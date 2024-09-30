import java.util.ArrayList;
import java.util.List;

public class MyStringBuilder {
    // дефолтный размер
    private static final int DEFAULT_SIZE = 16;

    // параметры строки
    private char[] symbolsArray;
    private int count;

    // смотритель
    private final Caretaker caretaker = new Caretaker();

    // пустая строка
    public MyStringBuilder() {
        this.symbolsArray = new char[DEFAULT_SIZE];
        this.count = 0;
    }

    // инициализация со строкой по умолчанию
    public MyStringBuilder(String source) {
        int size = DEFAULT_SIZE;
        if (source == null || source.isEmpty()) {
            this.symbolsArray = new char[size];
            this.count = 0;
        } else if (source.length() > DEFAULT_SIZE) {
            size = (int) Math.ceil(source.length() / (double) DEFAULT_SIZE) * DEFAULT_SIZE;
            this.symbolsArray = new char[size];
            this.count = source.length();
            System.arraycopy(source.toCharArray(), 0, this.symbolsArray, 0, this.count);
        } else {
            this.symbolsArray = new char[size];
            this.count = source.length();
        }
    }

    // добавление строки в конец
    public void append(String str) {
        if (str == null)
            return;

        // снимок
        caretaker.addMemento(createMemento());

        int length = str.length();
        valExtension(this.count + length);
        str.getChars(0, length, this.symbolsArray, this.count);
        this.count += length;
    }

    // добавление строки в середину
    public void insert(int offset, String str) {
        if (offset < 0 || offset > this.count) {
            throw new IndexOutOfBoundsException();
        }
        if (str == null) {
            return;
        }

        // снимок
        caretaker.addMemento(createMemento());

        int length = str.length();
        valExtension(this.count + length);
        System.arraycopy(this.symbolsArray, offset, this.symbolsArray, offset + length, this.count - offset);
        str.getChars(0, length, this.symbolsArray, offset);
        count += length;
    }

    // расширение массива в 2 раза
    private void valExtension(int length) {
        boolean needExtension = true;

        // пока нужно расширение
        while (needExtension)
            if (length < this.symbolsArray.length) {
                needExtension = false;
            } else {
                char[] newValue = new char[this.symbolsArray.length * 2];
                System.arraycopy(this.symbolsArray, 0, newValue, 0, count);
                this.symbolsArray = newValue;
            }
    }

    public void undo() {
        Memento memento = caretaker.getLastMemento();
        getStateFromMemento(memento);
        caretaker.removeMemento();
    }

    // от 0 символа до последнего не null
    public String toString() {
        return new String(this.symbolsArray, 0, this.count);
    }

    // создаем снимок текущего состояния объекта
    private Memento createMemento() {
        return new Memento(this.symbolsArray, this.count);
    }

    // восстановить состояние из снимка
    private void getStateFromMemento(Memento memento) {
        this.symbolsArray = new char[memento.stateCount];
        System.arraycopy(memento.stateSymbols, 0, this.symbolsArray, 0, memento.stateCount);
        this.count = memento.stateCount;
    }

    // класс снимок
    private static class Memento {
        private final char[] stateSymbols;
        private final int stateCount;

        private Memento(char[] stateSymbols, int stateCount) {
            this.stateSymbols = stateSymbols;
            this.stateCount = stateCount;
        }
    }

    // смотритель
    private static class Caretaker {
        private final List<Memento> mementoList = new ArrayList<>();

        // добавляем снимок
        private void addMemento(Memento memento) {
            mementoList.add(memento);
        }

        // получаем последний снимок
        private Memento getLastMemento() {
            if (mementoList.isEmpty()) {
                throw new NullPointerException("No memento was added to the caretaker");
            } else {
                return mementoList.get(mementoList.size() - 1);
            }
        }

        // удаляем последний снимок
        private void removeMemento() {
            mementoList.remove(mementoList.size() - 1);
        }
    }
}