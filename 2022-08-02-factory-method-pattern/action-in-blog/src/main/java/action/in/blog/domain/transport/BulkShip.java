package action.in.blog.domain.transport;

public class BulkShip extends Transport {

    @Override
    public void reserve() {
        System.out.println("do something to reserve bulk ship");
        reserved = true;
    }
}
