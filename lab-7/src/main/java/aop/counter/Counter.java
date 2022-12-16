package aop.counter;

import java.util.Random;

public class Counter {

    private int counter = 0;

    public int count() {
        randSleep();
        return counter;
    }

    public void click() {
        randSleep();
        counter++;
    }

    private void randSleep() {
        int rand = new Random().nextInt(2000);
        try {
            Thread.sleep(rand);
        } catch (InterruptedException ignored) {
        }
    }
}
