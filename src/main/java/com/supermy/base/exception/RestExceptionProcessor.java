package com.supermy.base.exception;


import com.supermy.base.exception.helper.ErrorInfo;
import com.supermy.base.exception.helper.FieldErrorDTO;
import com.supermy.base.exception.helper.ErrorFormInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestExceptionProcessor {




    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> getSQLError(Exception exception)
    {
		System.out.println("debug: getSQLError");

		String errmsg = findException(exception.getCause());
		System.out.println("debug:"+exception.fillInStackTrace().getMessage());

		exception.printStackTrace();

        HttpHeaders headers = new HttpHeaders();
        headers.set("HeaderKey","HeaderDetails");


//		Throwable[] suppressed = exception.getSuppressed();
//		for (Throwable se:
//				suppressed) {
//			System.out.println("debug:"+se.getMessage());
//
//		}


		return new ResponseEntity<String>(errmsg==null?exception.getCause().getMessage():errmsg,headers,HttpStatus.ACCEPTED);
    }

	private String findException(Throwable cause){

		if(cause==null) {
			return null;
		}
		System.out.println("===="+cause);
		if(cause.toString().indexOf("jdbc")>=1) {
			return cause.getMessage();
		}
		System.out.println("*****"+cause.getCause());
		return findException(cause.getCause());
	}

    //@ExceptionHandler(Exception.class)
    public @ResponseBody
	ErrorInfo handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
		System.out.println("debug: handleEmployeeNotFoundException");

		ex.printStackTrace();

        ErrorInfo response = new ErrorInfo(request.getRequestURL().toString(),ex.getMessage());

        return response;
    }


    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIOException(NullPointerException ex) {
		System.out.println("debug: handleIOException");

		ex.printStackTrace();
        return ClassUtils.getShortName(ex.getClass()) + ex.getMessage();
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleUnexpectedServerError(HttpServletRequest request,RuntimeException ex) {
		System.out.println("debug: handleUnexpectedServerError");
		String errmsg = findException(ex.getCause());
		System.out.println("debug: handleUnexpectedServerError   ---"+errmsg);

		ex.printStackTrace();

        ErrorInfo response = new ErrorInfo(request.getRequestURL().toString(),errmsg==null?ex.getCause().getMessage():errmsg);

        return response;
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public String exceptionIO(Exception e) {
		System.out.println("debug: exceptionIO");

		e.printStackTrace();

        //..
        return "error";
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public String exceptionSQL(Exception e) {
		System.out.println("debug: exceptionSQL");

		e.printStackTrace();

        //..
        return "error";
    }


    /////////////////

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(PersonNotFoundException.class)
	@ResponseStatus(value= HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo personNotFound(HttpServletRequest req, PersonNotFoundException ex) {
		System.out.println("debug: personNotFound");

		ex.printStackTrace();
		
		String errorMessage = localizeErrorMessage("error.no.smartphone.id");
		
		errorMessage += ex.getPersonId();
		String errorURL = req.getRequestURL().toString();
		
		return new ErrorInfo(errorURL, errorMessage);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value= HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorFormInfo handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
		System.out.println("debug: handleMethodArgumentNotValid");

		ex.printStackTrace();
		String errorMessage = localizeErrorMessage("error.bad.arguments");
		String errorURL = req.getRequestURL().toString();
		
		ErrorFormInfo errorInfo = new ErrorFormInfo(errorURL, errorMessage);
		
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		errorInfo.getFieldErrors().addAll(populateFieldErrors(fieldErrors));
		
		return errorInfo;
	}
	
	/**
	 * Method populates {@link java.util.List} of {@link FieldErrorDTO} objects. Each list item contains
	 * localized error message and name of a form field which caused the exception.
	 * Use the {@link #localizeErrorMessage(String) localizeErrorMessage} method.
	 *
	 * @param fieldErrorList - {@link java.util.List} of {@link org.springframework.validation.FieldError} items
	 * @return {@link java.util.List} of {@link FieldErrorDTO} items
	 */
	public List<FieldErrorDTO> populateFieldErrors(List<FieldError> fieldErrorList) {
		System.out.println("debug: populateFieldErrors");

		List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();
		StringBuilder errorMessage = new StringBuilder("");

		for (FieldError fieldError : fieldErrorList) {

			errorMessage.append(fieldError.getCode()).append(".");
			errorMessage.append(fieldError.getObjectName()).append(".");
			errorMessage.append(fieldError.getField());

			String localizedErrorMsg = localizeErrorMessage(errorMessage.toString());

			fieldErrors.add(new FieldErrorDTO(fieldError.getField(), localizedErrorMsg));
			errorMessage.delete(0, errorMessage.capacity());
		}
		return fieldErrors;
	}

	/**
	 * Method retrieves appropriate localized error message from the {@link org.springframework.context.MessageSource}.
	 * 
	 * @param errorCode - key of the error message
	 * @return {@link String} localized error message 
	 */
	public String localizeErrorMessage(String errorCode) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage(errorCode, null, locale);
		return errorMessage;
	}



}
