package action.in.blog.domain.transport;

public class ContainerShip extends Transport {

    @Override
    public void reserve() {
        System.out.println("do something to reserve container ship");
        reserved = true;
    }
}
