package action.in.blog;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ActionInBlogApplication {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("10 seconds later this application will start. make baseline."); // 1
        Thread.sleep(10000);

        var bufferList = new ArrayList<ByteBuffer>();

        for (int index = 0; index < 15; index++) { // 2
            Thread.sleep(1000);
            var byteBuffer = ByteBuffer.allocateDirect(1024);
            bufferList.add(byteBuffer);
            System.out.printf("%s seconds. expected size = %sKB\n", index + 1, bufferList.size());
        }

        System.out.println("10 seconds later this application will stop."); // 3
        Thread.sleep(10000);
    }
}
