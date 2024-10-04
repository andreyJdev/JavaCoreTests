public class TestComplexTaskExecutor {

    public static void main(String[] args) {
        ComplexTaskExecutor taskExecutor = new ComplexTaskExecutor(5, 1000); // Количество задач и длительность сна

        Runnable testRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + " начал тест.");

            taskExecutor.executeTasks();

            System.out.println(Thread.currentThread().getName() + " завершил тест.");
        };

        Thread thread1 = new Thread(testRunnable, "TestThread-1");
        Thread thread2 = new Thread(testRunnable, "TestThread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
