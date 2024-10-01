import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static <T> Map<T, Integer>toMap(T[] array) {
        Map<T, Integer> map = new HashMap<>();
        T[] arrayCpy = Arrays.copyOf(array, array.length);
        for (T i : arrayCpy) {
            map.merge(i, 1, Integer::sum);
        }
        return map;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, -1, 8, 9, 10, 1, 1, 2, 3, 5, 5, 6, 7, 10, -1};
        Map<Integer, Integer> map = toMap(array);
        System.out.println(map);
    }
}