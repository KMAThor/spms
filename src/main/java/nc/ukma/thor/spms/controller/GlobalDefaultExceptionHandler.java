package nc.ukma.thor.spms.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "Error";

	  @ExceptionHandler(value = { SQLException.class, DataAccessException.class})
	  public ModelAndView databaseError(HttpServletRequest req, Exception e) {
		e.printStackTrace();
		ModelAndView mav = new ModelAndView();
		mav.addObject("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
		mav.addObject("message", "Database error, please try again later.");
		mav.setViewName(DEFAULT_ERROR_VIEW);
	    return mav;
	  }
	/*  org.springframework.security.authentication.InternalAuthenticationServiceException*/
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it 
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;
		e.printStackTrace();
		ModelAndView mav = new ModelAndView();
		mav.addObject("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
		mav.addObject("message", "INTERNAL SERVER ERROR");
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}