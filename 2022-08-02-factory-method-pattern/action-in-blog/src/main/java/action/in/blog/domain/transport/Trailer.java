package action.in.blog.domain.transport;

public class Trailer extends Transport {

    @Override
    public void reserve() {
        System.out.println("do something to reserve trailer");
        reserved = true;
    }
}
