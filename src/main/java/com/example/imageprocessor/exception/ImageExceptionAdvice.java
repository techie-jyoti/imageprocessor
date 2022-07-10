package com.example.imageprocessor.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ImageExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(ImageExceptionAdvice.class);
	
	@ResponseBody
	@ExceptionHandler(InvalidImageTypeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String InvalidImageTypeExceptionHandler(InvalidImageTypeException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(ImageDownloadException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String ImageDownloadExceptionHandler(ImageDownloadException ex) {
		logger.error("Image download Exception "+ex.getMessage());
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(ImageUploadException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String ImageUploadException(ImageUploadException ex) {
		logger.error("Image upload Exception "+ex.getMessage());
		return ex.getMessage();
	}
	
	
	
}
