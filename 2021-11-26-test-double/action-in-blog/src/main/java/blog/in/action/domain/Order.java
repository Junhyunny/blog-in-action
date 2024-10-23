package blog.in.action.domain;

import java.util.Objects;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final int price;

    public Order() {
        this(0);
    }

    public Order(int price) {
        this.id = null;
        this.price = price;
    }

    public Order(UUID id, int price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return price == order.price && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }
}
