package action.in.blog.domain.transport;

public class Truck extends Transport {

    @Override
    public void reserve() {
        System.out.println("do something to reserve truck");
        reserved = true;
    }
}
