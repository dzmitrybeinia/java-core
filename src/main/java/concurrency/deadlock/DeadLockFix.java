package concurrency.deadlock;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockFix {
    public static void main(String[] args) throws InterruptedException {
        Queue<String> in = new ArrayDeque<>(Arrays.asList("foo", "bar", "baz"));
        Queue<String> out = new ArrayDeque<>(Arrays.asList("foo", "bar", "baz"));
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println("thread1" + i);
                transfer(in, out);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println("thread2" + i);
                transfer(out, in);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static final Lock lock = new ReentrantLock();

    private static void transfer(Queue<String> in, Queue<String> out) {
        if (lock.tryLock()) {
            try {
                String res = in.poll();
                if (res != null) {
                    out.add(res);
                }
            } finally {
                lock.unlock();
            }
        } else {
            // Handle the case where the lock is not acquired
        }
    }
}