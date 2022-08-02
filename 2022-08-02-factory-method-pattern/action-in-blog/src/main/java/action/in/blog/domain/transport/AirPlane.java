package action.in.blog.domain.transport;

public class AirPlane extends Transport {

    @Override
    public void reserve() {
        System.out.println("do something to reserve airplane");
        reserved = true;
    }
}
