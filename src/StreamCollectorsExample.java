import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        Map<String, List<Order>> map;

        map = orders.stream().sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.groupingBy(Order::getProduct));

        map.forEach((productName, orderList) -> System.out.println(
                productName + " " + orderList.size() + " Items: Price - " +
                        orderList.stream().mapToDouble(Order::getCost).sum()
        ));
    }
}