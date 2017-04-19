package daggerok.monitor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//tag::monitor[]
@Slf4j
@Aspect
@Component
public class MessageLogAspect {

  @SneakyThrows
  @Around(value = "execution(* daggerok..*(..))")
  public Object trace(ProceedingJoinPoint joinPoint) {

    val res = joinPoint.proceed();

    log.info("trace: {}\ntarget: {}\nargs  : {}\nresult: {}",
        joinPoint,
        joinPoint.getSignature().toShortString(),
        joinPoint.getArgs(),
        res);

    return res;
  }
}
//end::monitor[]
