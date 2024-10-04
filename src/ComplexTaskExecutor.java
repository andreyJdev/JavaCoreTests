import java.util.concurrent.*;

class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final long sleepDuration;

    public ComplexTaskExecutor(int numberOfTasks, long sleepDuration) {
        this.numberOfTasks = numberOfTasks;
        this.sleepDuration = sleepDuration;
    }

    public void executeTasks() {
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () ->
                System.out.println("Все задачи завершены. Обработка результатов...")
        );

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        try {
            for (int i = 1; i <= numberOfTasks; i++) {
                final int taskId = i;
                executorService.submit(() -> {
                    ComplexTask task = new ComplexTask(taskId, sleepDuration);
                    task.execute();
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(16, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}