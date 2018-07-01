package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductFactoryDemo {
	private final AtomicInteger productCount = new AtomicInteger();

	public AtomicInteger getProductcount() {
		return productCount;
	}

	class Producer implements Runnable {
		BlockingQueue<RawMaterial> queue = new LinkedBlockingDeque<>();
		ConveyorBelt belt;
		int numOfMachines, numOfBolts;

		public Producer(BlockingQueue<RawMaterial> blockngQueue, ConveyorBelt belt, int numOfMachines, int numOfBolts) {
			this.queue = blockngQueue;
			this.belt = belt;
			this.numOfBolts = numOfBolts;
			this.numOfMachines = numOfMachines;
		}

		@Override
		public void run() {
			queue.addAll(belt.getRawMaterial(numOfBolts, "Bolt"));
			queue.addAll(belt.getRawMaterial(numOfMachines, "Machine"));
			synchronized (queue) {
				queue.notify();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			synchronized (queue) {
				queue.notifyAll();
			}
		}

	}

	class Consumer implements Runnable {
		BlockingQueue<RawMaterial> queue = new LinkedBlockingDeque<>();

		public Consumer(BlockingQueue<RawMaterial> blockngQueue) {
			this.queue = blockngQueue;
		}

		@Override
		public void run() {
			int boltCount = 0;
			int machineCount = 0;
			while (true) {
				synchronized (queue) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
					}
				}
				while (!queue.isEmpty()) {
					RawMaterial material = queue.poll();
					if (material instanceof Bolt) {
						boltCount++;
					} else if (material instanceof Machine) {
						machineCount++;
					}
				}
				if (queue.isEmpty() && boltCount != 0 && machineCount != 0) {
					productCount.set(boltCount / machineCount);
				}
			}
		}
	}

	static class ConveyorBelt {

		RawMaterial getRawMaterial(String type) {
			if ("Bolt".equalsIgnoreCase(type)) {
				return new Bolt();
			} else if ("Machine".equalsIgnoreCase(type)) {
				return new Machine();
			}
			return null;
		}
		
		List<RawMaterial> getRawMaterial(int num, String type) {
			List<RawMaterial> materialList = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				RawMaterial material = getRawMaterial(type);
				materialList.add(material);
			}
			return Collections.synchronizedList(materialList);
		}
	}

	static class RawMaterial {
	}

	static class Bolt extends RawMaterial {
		@Override
		public String toString() {
			return "Bolt";
		}
	}

	static class Machine extends RawMaterial {
		@Override
		public String toString() {
			return "Machine";
		}
	}
}