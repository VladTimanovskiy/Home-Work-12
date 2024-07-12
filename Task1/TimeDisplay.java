package module12.Task1;
public class TimeDisplay {

    public static void main(String[] args) {
        // Створення першого потоку для виведення часу кожну секунду
        Thread timeThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                long currentTime = System.currentTimeMillis();
                long elapsedTime = (currentTime - startTime) / 1000;
                System.out.println("Час, що минув від запуску програми: " + elapsedTime + " секунд");
            }
        });

        // Створення другого потоку для виведення повідомлення кожні 5 секунд
        Thread messageThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                System.out.println("Минуло 5 секунд");
            }
        });

        // Запуск обох потоків
        timeThread.start();
        messageThread.start();
    }
}
