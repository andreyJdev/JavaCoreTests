import java.util.ArrayList;
import java.util.List;

public class MyStringBuilder {
    private char[] value;
    private int count;
    private final Originator originator = new Originator();
    private final Caretaker caretaker = new Caretaker();

    public MyStringBuilder() {
        value = new char[16];
        count = 0;
    }

    public MyStringBuilder(String str) {
        int length = str.length();
        int size = Math.max(str.length(), 16);
        value = new char[size];
        str.getChars(0, length, value, count);
        count += length;
    }

    public MyStringBuilder append(String str) {
        if (str == null)
            return this;

        caretaker.addMemento(originator.saveStateToMemento(this));

        int length = str.length();
        valExtension(count + length);
        str.getChars(0, length, value, count);
        count += length;
        return this;
    }

    public MyStringBuilder insert(int offset, String str) {
        if (offset < 0 || offset > count) {
            throw new IndexOutOfBoundsException();
        }
        if (str == null) {
            return this;
        }

        caretaker.addMemento(originator.saveStateToMemento(this));

        int length = str.length();
        valExtension(count + length);
        System.arraycopy(value, offset, value, offset + length, count - offset);
        str.getChars(0, length, value, offset);
        count += length;
        return this;
    }

    private void valExtension(int length) {
        if (length < value.length) {
            return;
        } else {
            char[] newValue = new char[value.length * 2];
            System.arraycopy(value, 0, newValue, 0, count);
            value = newValue;
        }
    }

    public void undo() {
        Originator.Memento memento = caretaker.getLastMemento();
        if (memento != null) {
            originator.getStateFromMemento(memento, this);
        } else {
            throw new NullPointerException("Caretaker is null");
        }
    }

    public String toString() {
        return new String(value, 0, count);
    }

    static class Originator {
        private char[] stateValue;
        private int stateCount;

        public Memento saveStateToMemento(MyStringBuilder thisStringBuilder) {
            stateValue = new char[thisStringBuilder.value.length];
            System.arraycopy(thisStringBuilder.value, 0, stateValue, 0, thisStringBuilder.count);
            stateCount = thisStringBuilder.count;
            return new Memento(stateValue, stateCount);
        }

        public void getStateFromMemento(Memento memento, MyStringBuilder thisStringBuilder) {
            thisStringBuilder.value = new char[memento.stateValue.length];
            System.arraycopy(memento.stateValue, 0, thisStringBuilder.value, 0, memento.stateCount);
            thisStringBuilder.count = memento.stateCount;
        }

        private static class Memento {
            private final char[] stateValue;
            private final int stateCount;

            public Memento(char[] stateValue, int stateCount) {
                this.stateValue = stateValue;
                this.stateCount = stateCount;
            }
        }
    }

    private static class Caretaker {
        private final List<Originator.Memento> mementoList = new ArrayList<>();

        private void addMemento(Originator.Memento memento) {
            mementoList.add(memento);
        }

        private Originator.Memento getLastMemento() {
            if (!mementoList.isEmpty()) {
                return mementoList.remove(mementoList.size() - 1);
            }
            return null;
        }
    }
}
