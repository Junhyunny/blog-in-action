package action.in.blog.handler;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class PostInvocationHandler implements InvocationHandler {

    private final Object target;

    public PostInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getPosts")) {
            var start = System.nanoTime();
            var result = method.invoke(target, args);
            log.info("getPosts method takes {} ns", System.nanoTime() - start);
            return result;
        }
        throw new RuntimeException(
                String.format("%s is not supported method", method.getName())
        );
    }
}
