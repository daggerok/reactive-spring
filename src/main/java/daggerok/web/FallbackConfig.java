package daggerok.web;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class FallbackConfig {

  @Bean
  ErrorProperties errorProperties() {
    return new ErrorProperties();
  }

  @Slf4j
  @Controller
  static class CustomErrorController extends BasicErrorController {

    public CustomErrorController(final ErrorAttributes errorAttributes, final ErrorProperties errorProperties) {
      super(errorAttributes, errorProperties);
    }

    @Override
    @SneakyThrows
    public ModelAndView errorHtml(final HttpServletRequest request, final HttpServletResponse response) {
      response.sendRedirect("/");
      return null; // useless after redirect
    }
  }
}
