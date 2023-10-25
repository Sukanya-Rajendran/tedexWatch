package com.ecomapp.ecomapp.config.OtpConfig;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DefaultAddressNotFoundException.class)
    public ModelAndView handleDefaultAddressNotFoundException(DefaultAddressNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.setViewName("error-page"); // Create an error page in your template
        return modelAndView;
    }

    public static class DefaultAddressNotFoundException extends RuntimeException {
        public DefaultAddressNotFoundException(String message) {
            super(message);
        }
    }


}
