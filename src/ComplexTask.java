class ComplexTask {
    private final int taskId;
    private final long sleepDuration;

    public ComplexTask(int taskId, long sleepDuration) {
        this.taskId = taskId;
        this.sleepDuration = sleepDuration;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " выполняет задачу " + taskId + ".");
        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " завершил задачу " + taskId + ".");
    }
}