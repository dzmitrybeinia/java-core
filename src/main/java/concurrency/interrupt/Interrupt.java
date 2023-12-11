package concurrency.interrupt;

public class Interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Print());
        thread.start();
        Thread.sleep(10000);
        thread.interrupt();
        thread.join();
    }
}

class Print implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("print smth");
            } catch (InterruptedException ex) {
                System.out.println("oops");
                return;
            }
        }
    }
}
