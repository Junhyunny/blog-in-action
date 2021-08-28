package blog.in.action.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class BlogAop {

    @Around("execution(* blog.in.action.service.BlogService.*(..))")
    public Object aroundServiceFoo(ProceedingJoinPoint pjp) throws Throwable {
        log.info("==========\taround service before foo");
        Object result = pjp.proceed();
        log.info("==========\taround service after foo");
        return result;
    }

    @Around("execution(* blog.in.action.controller.BlogController.*(..))")
    public Object aroundControllerFoo(ProceedingJoinPoint pjp) throws Throwable {
        log.info("==========\taround controller before foo");
        Object result = pjp.proceed();
        log.info("==========\taround controller after foo");
        return result;
    }
}
