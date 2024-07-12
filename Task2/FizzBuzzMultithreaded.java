package module12.Task2;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzzMultithreaded {

    private int n;
    private int currentNumber = 1;
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    // Метод для потоків A, B і C
    public synchronized void checkAndAddToQueue() {
        while (currentNumber <= n) {
            if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                queue.add("fizzbuzz");
            } else if (currentNumber % 3 == 0) {
                queue.add("fizz");
            } else if (currentNumber % 5 == 0) {
                queue.add("buzz");
            } else {
                queue.add(String.valueOf(currentNumber));
            }
            currentNumber++;
            notifyAll();
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Метод для потоку D
    public void number() {
        while (true) {
            String value;
            try {
                value = queue.take();
                System.out.println(value);
                if (value.equals(String.valueOf(n))) {
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        Thread threadA = new Thread(() -> fizzBuzz.checkAndAddToQueue());
        Thread threadB = new Thread(() -> fizzBuzz.checkAndAddToQueue());
        Thread threadC = new Thread(() -> fizzBuzz.checkAndAddToQueue());
        Thread threadD = new Thread(() -> fizzBuzz.number());

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
