public class BlockingQueue {

    private final Node first;
    private int size = 0;
    private final int initialCapacity;

    public BlockingQueue(int initialCapacity) {
        this.first = new Node(null, null);
        this.initialCapacity = initialCapacity;
    }

    public int getSize () {
        return this.size;
    }

    public synchronized void enqueue(Integer item) {
        if (size >= initialCapacity) {
            try {
            wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Node current = first;

        while (current.next != null) {
            current = current.next;
        }

        current.next = new Node(item, null);
        size++;
        notify();
    }

    public synchronized Integer dequeue() {
        if (first.next == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Node current = first;
        while (current.next.getNext() != null) {
            current = current.next;
        }

        Node target = current.next;
        current.next = null;
        notify();
        return target.value;
    }

    public String toString() {
        Node current = first;
        StringBuilder sb = new StringBuilder();

        while (current.next != null) {
            current = current.next;
            sb.append(current.value).append(" ");
        }

        return sb.toString();
    }

    private static class Node {
        Integer value;
        Node next;

        public Node(Integer value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
