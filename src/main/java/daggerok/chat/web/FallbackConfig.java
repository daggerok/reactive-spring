package daggerok.chat.web;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Slf4j
@Configuration
public class FallbackConfig implements ErrorViewResolver {

  @Override
  public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

    val formatted = model
        .entrySet()
        .stream()
        .map(e -> format("%s:\"%s\"", e.getKey(), e.getValue()))
        .collect(joining(","));

    log.info("{{}}", formatted);

    return new ModelAndView("/index.html");
  }
}
