import java.util.Random;

public class Main {

    public static Integer randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(min, max);
    }

    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue(16);
        // без thread2 блокируется при добавлении 17-го элемента
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 17; i++)
                queue.enqueue(randInt(-99, 100));

            System.out.println(queue.getSize());
            System.out.println(queue);
        });
        // без thread3 блокируется при извлечении 18-го элемента
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 18; i++)
                System.out.println(queue.dequeue());
        });
        // завершает программу
        Thread thread3 = new Thread(() -> {
            queue.enqueue(randInt(-99, 100));
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}