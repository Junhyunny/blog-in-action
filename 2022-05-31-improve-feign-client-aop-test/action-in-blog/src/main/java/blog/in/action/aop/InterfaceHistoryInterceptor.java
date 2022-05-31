package blog.in.action.aop;

import blog.in.action.annotation.InterfaceMeta;
import blog.in.action.repository.InterfaceHistory;
import blog.in.action.repository.InterfaceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class InterfaceHistoryInterceptor {

    private final InterfaceHistoryRepository repository;

    private String[] getPath(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetMapping) {
                return ((GetMapping) annotation).path();
            } else if (annotation instanceof PostMapping) {
                return ((PostMapping) annotation).path();
            } else if (annotation instanceof PutMapping) {
                return ((PutMapping) annotation).path();
            } else if (annotation instanceof DeleteMapping) {
                return ((DeleteMapping) annotation).path();
            } else if (annotation instanceof RequestMapping) {
                return ((RequestMapping) annotation).path();
            }
        }
        return null;
    }

    @Around("@within(org.springframework.cloud.openfeign.FeignClient) && @annotation(blog.in.action.annotation.InterfaceMeta)")
    public Object aroundCallFeignClient(ProceedingJoinPoint pjp) throws Throwable {
        Timestamp requestTime = Timestamp.valueOf(LocalDateTime.now());

        Object result = pjp.proceed();

        try {
            Timestamp responseTime = Timestamp.valueOf(LocalDateTime.now());
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            InterfaceMeta interfaceMeta = method.getAnnotation(InterfaceMeta.class);
            Annotation[] annotations = method.getDeclaredAnnotations();

            InterfaceHistory interfaceHistory = InterfaceHistory
                    .builder()
                    .serviceId(interfaceMeta.serviceId())
                    .explainText(interfaceMeta.explainText())
                    .path(getPath(annotations))
                    .response(String.valueOf(result))
                    .requestTime(requestTime)
                    .responseTime(responseTime)
                    .build();

            repository.save(interfaceHistory);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
