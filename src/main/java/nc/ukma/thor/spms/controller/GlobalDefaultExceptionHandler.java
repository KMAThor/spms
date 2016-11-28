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

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";
	public static final String DEFAULT_ERROR_DESCRIPTION = "It looks like somebody has just lost his job =) \n"
			+ "Be sure that our team has already received notification about this error. \n"
			+ "As soon as we solve this problem, you will get email notification. \n"
			+ "Sorry for the inconvenience.";

	@ExceptionHandler(value = { SQLException.class, DataAccessException.class })
	public String databaseError(Model model, Exception e) {
		e.printStackTrace();
		
		model.addAttribute("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", "Database error, please try again later.");
		return DEFAULT_ERROR_VIEW;
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public String accessDeniedError(Model model, Exception e) {
		e.printStackTrace();

		model.addAttribute("httpStatus", HttpStatus.FORBIDDEN.value());
		model.addAttribute("message", "Access denied");
		return DEFAULT_ERROR_VIEW;
	}
	
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(Model model, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;
		e.printStackTrace();
		
		model.addAttribute("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", "INTERNAL SERVER ERROR");
		model.addAttribute("descrioption", DEFAULT_ERROR_DESCRIPTION);
		return DEFAULT_ERROR_VIEW;
	}
}