package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberSequence {
    private static final Logger logger = LoggerFactory.getLogger(NumberSequence.class);

    private final int min = 1;
    private final int max = 10;
    private final int[] outputNumbers = new int[]{min, min};
    private final Direction[] direction = new Direction[]{Direction.INC, Direction.INC};

    private int nextThreadId = 0;


    private synchronized void action(int currentThreadId) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (this.nextThreadId != currentThreadId) {
                    this.wait();
                }

                logger.info("Thread_" + (currentThreadId + 1) + ": " + outputNumbers[currentThreadId]);

                setNextOutputNumbers(currentThreadId);
                setNextThreadId(currentThreadId);
                sleep(300);
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void setNextOutputNumbers(int id) {
        if (this.direction[id] == Direction.INC) {
            if (this.outputNumbers[id] == this.max) {
                this.direction[id] = Direction.DEC;
                this.outputNumbers[id]--;
            } else {
                this.outputNumbers[id]++;
            }
        } else {
            assert this.direction[id] == Direction.DEC;
            if (this.outputNumbers[id] == this.min) {
                this.direction[id] = Direction.INC;
                this.outputNumbers[id]++;
            } else {
                this.outputNumbers[id]--;
            }
        }
    }

    private void setNextThreadId(int currentThreadId) {
        if (currentThreadId == 0) {
            this.nextThreadId = 1;
        } else {
            this.nextThreadId = 0;
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        NumberSequence numberSequence = new NumberSequence();
        new Thread(() -> numberSequence.action(0)).start();
        new Thread(() -> numberSequence.action(1)).start();
    }
}
