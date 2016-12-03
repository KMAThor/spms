package nc.ukma.thor.spms.controller;

import java.sql.SQLException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(value = { SQLException.class, DataAccessException.class })
	public String databaseError(Model model, Exception e) {
		//Logger should be used instead
		e.printStackTrace();
		return "redirect:/databaseError/";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String illegalArgumentException(Model model, Exception e) {
		//Logger should be used instead
		e.printStackTrace();
		return "redirect:/403/";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String accessDeniedError(Model model, Exception e) {
		//Logger should be used instead
		e.printStackTrace();
		return "redirect:/403/";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleNoHandlerFoundException(Model model, NoHandlerFoundException e) {
		//Logger should be used instead
		e.printStackTrace();
		return "redirect:/404/";
	}
	
	@ExceptionHandler(Exception.class)
	public String defaultErrorHandler(Model model, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the spring framework handle it
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;
		
		//Logger should be used instead
		e.printStackTrace();
		return "redirect:/500/";
	}
	
}