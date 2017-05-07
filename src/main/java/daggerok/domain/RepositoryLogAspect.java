package daggerok.domain;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RepositoryLogAspect {

  @SneakyThrows
  @Around(value = "execution(* *..*Repository.*(..))")
  public Object trace(ProceedingJoinPoint joinPoint) {

    log.info("trace {}", joinPoint);
    return joinPoint.proceed();
  }
}
