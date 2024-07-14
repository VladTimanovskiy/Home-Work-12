package module12.Task2;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FizzBuzz {
    private final int n;
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final AtomicInteger currentNumber = new AtomicInteger(1);
    private final CountDownLatch latch;

    public FizzBuzz(int n) {
        this.n = n;
        this.latch = new CountDownLatch(3);
    }

    public void fizz() throws InterruptedException {
        while (true) {
            int num = currentNumber.get();
            if (num > n) {
                break;
            }
            if (num % 3 == 0 && num % 5 != 0) {
                queue.put("fizz");
                currentNumber.incrementAndGet();
            }
        }
        latch.countDown();
    }

    public void buzz() throws InterruptedException {
        while (true) {
            int num = currentNumber.get();
            if (num > n) {
                break;
            }
            if (num % 5 == 0 && num % 3 != 0) {
                queue.put("buzz");
                currentNumber.incrementAndGet();
            }
        }
        latch.countDown();
    }

    public void fizzbuzz() throws InterruptedException {
        while (true) {
            int num = currentNumber.get();
            if (num > n) {
                break;
            }
            if (num % 3 == 0 && num % 5 == 0) {
                queue.put("fizzbuzz");
                currentNumber.incrementAndGet();
            }
        }
        latch.countDown();
    }

    public void number() throws InterruptedException {
        while (true) {
            int num = currentNumber.get();
            if (num > n) {
                break;
            }
            if (num % 3 != 0 && num % 5 != 0) {
                queue.put(String.valueOf(num));
                currentNumber.incrementAndGet();
            }
        }
    }

    public void print() throws InterruptedException {
        latch.await();
        for (int i = 1; i <= n; i++) {
            System.out.print(queue.take() + (i < n ? ", " : ""));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();

        fizzBuzz.print();
    }
}
