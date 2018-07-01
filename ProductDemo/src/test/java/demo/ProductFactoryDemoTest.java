package demo;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.Before;
import org.junit.Test;

import demo.ProductFactoryDemo.Bolt;
import demo.ProductFactoryDemo.ConveyorBelt;
import demo.ProductFactoryDemo.Machine;
import demo.ProductFactoryDemo.RawMaterial;

public class ProductFactoryDemoTest {
	ProductFactoryDemo demo;

	@Before
	public void setUp() {
		demo = new ProductFactoryDemo();
	}

	@Test
	public void testNoProductIfNoRawMaterial() throws InterruptedException {
		ConveyorBelt belt = new ConveyorBelt();
		BlockingQueue<RawMaterial> blockngQueue = new LinkedBlockingDeque<>();
		Thread producer = new Thread(demo.new Producer(blockngQueue, belt, 0, 0));
		Thread consumers[] = new Thread[2];
		for (int i = 0; i < consumers.length; i++) {
			consumers[i] = new Thread(demo.new Consumer(blockngQueue), ("Consumer " + i));
			consumers[i].start();
		}
		producer.start();
		Thread.sleep(10000);
		assertEquals(demo.getProductcount().get(), 0);
	}
	
	@Test
	public void testProductCount_WithMachine2_And_Bold5() throws InterruptedException {
		ConveyorBelt belt = new ConveyorBelt();
		BlockingQueue<RawMaterial> blockngQueue = new LinkedBlockingDeque<>();
		Thread producer = new Thread(demo.new Producer(blockngQueue, belt, 2, 5));
		Thread cons[] = new Thread[2];
		for (int i = 0; i < cons.length; i++) {
			cons[i] = new Thread(demo.new Consumer(blockngQueue), ("Consumer " + i));
			cons[i].start();
		}
		producer.start();
		Thread.sleep(10000);
		assertEquals(demo.getProductcount().get(), 2);
	}

	@Test
	public void testProductCount_WithMachine2_And_Bold6() throws InterruptedException {
		ConveyorBelt belt = new ConveyorBelt();
		BlockingQueue<RawMaterial> blockngQueue = new LinkedBlockingDeque<>();
		Thread producer = new Thread(demo.new Producer(blockngQueue, belt, 2, 6));
		Thread cons[] = new Thread[2];
		for (int i = 0; i < cons.length; i++) {
			cons[i] = new Thread(demo.new Consumer(blockngQueue), ("Consumer " + i));
			cons[i].start();
		}
		producer.start();
		Thread.sleep(10000);
		assertEquals(demo.getProductcount().get(), 3);
	}

	@Test
	public void testProductCount_WithMachine3_And_Bold8() throws InterruptedException {
		ConveyorBelt belt = new ConveyorBelt();
		BlockingQueue<RawMaterial> blockngQueue = new LinkedBlockingDeque<>();
		Thread producer = new Thread(demo.new Producer(blockngQueue, belt, 2, 8));
		Thread cons[] = new Thread[2];
		for (int i = 0; i < cons.length; i++) {
			cons[i] = new Thread(demo.new Consumer(blockngQueue), ("Consumer " + i));
			cons[i].start();
		}
		producer.start();
		Thread.sleep(10000);
		assertEquals(demo.getProductcount().get(), 4);
	}

	@Test
	public void testProductCount_WithMachine3_And_Bold9() throws InterruptedException {
		ConveyorBelt belt = new ConveyorBelt();
		BlockingQueue<RawMaterial> blockngQueue = new LinkedBlockingDeque<>();
		Thread producer = new Thread(demo.new Producer(blockngQueue, belt, 2, 9));
		Thread cons[] = new Thread[2];
		for (int i = 0; i < cons.length; i++) {
			cons[i] = new Thread(demo.new Consumer(blockngQueue), ("Consumer " + i));
			cons[i].start();
		}
		producer.start();
		Thread.sleep(10000);
		assertEquals(demo.getProductcount().get(), 4);
	}

	@Test
	public void testBoltToString() {
		Bolt bolt = new Bolt();
		assertEquals(bolt.toString(), "Bolt");
	}

	@Test
	public void testMachineToString() {
		Machine machine = new Machine();
		assertEquals(machine.toString(), "Machine");
	}
	
	@Test
	public void testGetInvalidMaterial() {
		ConveyorBelt belt = new ConveyorBelt();
		assertEquals(belt.getRawMaterial(null), null);
	}
	

}
