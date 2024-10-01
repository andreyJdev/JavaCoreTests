import java.util.Arrays;

public class Main {

    public static <T> T[] filter(T[] array, Filter<T> filter) {
        T[] arrayCopy = Arrays.copyOf(array, array.length);
        for (int i = 0; i < arrayCopy.length; i++) {
            arrayCopy[i] = filter.apply(arrayCopy[i]);
        }
        return arrayCopy;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] arrayCopy = filter(array, new FilterImpl<>());
        for (Integer obj : arrayCopy) {
            System.out.println(obj);
        }
    }
}