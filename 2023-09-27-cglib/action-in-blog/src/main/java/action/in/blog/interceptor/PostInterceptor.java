package action.in.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class PostInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("CGLib interceptor works");
        if (method.getName().equals("getPosts")) {
            var start = System.nanoTime();
            var result = proxy.invokeSuper(obj, args);
            log.info("getPosts method takes {} ns", System.nanoTime() - start);
            return result;
        }
        return proxy.invokeSuper(obj, args);
    }
}
