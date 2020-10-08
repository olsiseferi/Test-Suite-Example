package al.thinktech.tutorial.testsuite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import al.thinktech.tutorial.testsuite.exception.EventException;
import al.thinktech.tutorial.testsuite.exception.dto.ErrorDto;

@ControllerAdvice
public class EventControllerExceptionMapper {
	@ExceptionHandler(EventException.class)
	ResponseEntity<ErrorDto> campaignHandler(EventException ex) {
		ErrorDto error = new ErrorDto(ex.getType().getCode(), ex.getType().getMessage());
		return new ResponseEntity<>(error, ex.getType().getStatus());
	}

}
