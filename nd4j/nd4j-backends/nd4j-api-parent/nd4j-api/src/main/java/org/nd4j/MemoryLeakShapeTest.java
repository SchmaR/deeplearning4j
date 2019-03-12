package org.nd4j;

import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.shape.Shape;
import org.nd4j.linalg.factory.Nd4j;

public class MemoryLeakShapeTest {

  private static int mega = (int) Math.pow(1024, 2);


  public static void main(String[] args) throws InterruptedException {
    DataBuffer longBuffer = Nd4j.createBuffer(2);

    for (int i = 0; i < 5_000_000; i++) {
      Shape.shapeOf(longBuffer);
      if (i % 10000 == 0) {
         System.out.println(i);
         printMemoryStats();
      }
    }

    for (int i = 0; i < 5; i++) {
      System.gc();
      System.gc();
      Thread.sleep(1000);
      printMemoryStats();
    }

    System.out.println("DONE GC");
    long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mega;
    printMemoryStats();
    if (usedMemory > 600) {
      System.exit(1);
    } else {
      System.exit(0);
    }
  }

  private static void printMemoryStats() {
    long maxMemory = Runtime.getRuntime().maxMemory() / mega;
    System.out.println("Max memory:" + maxMemory);
    long totalMemory = Runtime.getRuntime().totalMemory() / mega;
    System.out.println("total memory:" + totalMemory);
    long freeMemory = Runtime.getRuntime().freeMemory() / mega;
    System.out.println("free memory:" + freeMemory);
    long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mega;
    System.out.println("used memory:" + usedMemory);
  }

}
