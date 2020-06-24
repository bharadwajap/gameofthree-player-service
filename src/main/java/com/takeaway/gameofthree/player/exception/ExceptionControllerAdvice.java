package com.takeaway.gameofthree.player.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Allows to handle all expected and unexpected errors occurred while processing the request.
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    private static final String APPLICATION_PROBLEM_JSON = "application/problem+json";

    /**
     * Handles a case when requested resource cannot be found
     *
     * @param e any exception of type {@link Exception}
     * @return {@link ResponseEntity} containing standard body in case of errors
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public HttpEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                     final HttpServletRequest request) {
    	String message = String.format("Resource %s of type %s cannot be found", e.getResourceId(), e.getResourceType());
        LOGGER.error(message);

        ProblemDetail problem = new ProblemDetail("Resource not found", message);
        problem.setStatus(HttpStatus.NOT_FOUND.value());
        problem.setInstance(request.getRequestURI());

        return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles a case when opponent player is not available
     *
     * @param e any exception of type {@link Exception}
     * @return {@link ResponseEntity} containing standard body in case of errors
     */
    @ExceptionHandler(PlayerNotReadyException.class)
    public HttpEntity<ProblemDetail> handlePlayerNotReadyException(PlayerNotReadyException e,
                                                                     final HttpServletRequest request) {
    	String message = "Opponent Player {} is not available to play the game, please try again later!";
        LOGGER.error(message, e.getPlayer());

        ProblemDetail problem = new ProblemDetail("Opponent not found", String.format(message.replace("{}", "%s"), e.getPlayer()));
        problem.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        problem.setInstance(request.getRequestURI());

        return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    /**
     * Handles a case when requested resource cannot be found
     *
     * @param e any exception of type {@link Exception}
     * @return {@link ResponseEntity} containing standard body in case of errors
     */
    @ExceptionHandler(InputValueMissingException.class)
    public HttpEntity<ProblemDetail> handleInputValueMissingException(InputValueMissingException e,
                                                                     final HttpServletRequest request) {
    	String message = "Input Value is missing! When Type of Input is set as manual, Input value is mandatory!";
        LOGGER.error(message);

        ProblemDetail problem = new ProblemDetail(HttpStatus.BAD_REQUEST.toString(), message);
        problem.setStatus(HttpStatus.BAD_REQUEST.value());
        problem.setInstance(request.getRequestURI());

        return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles all unexpected situations
     *
     * @param e any exception of type {@link Exception}
     * @return {@link ResponseEntity} containing standard body in case of errors
     */
    @ExceptionHandler(Exception.class)
    public HttpEntity<ProblemDetail> handleException(Exception e,
                                                     final HttpServletRequest request) {

        LOGGER.error("An unexpected error occurred", e);

        ProblemDetail problem = new ProblemDetail("Internal Error", e.getMessage());
        problem.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        problem.setInstance(request.getRequestURI());

        return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpHeaders overrideContentType() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", APPLICATION_PROBLEM_JSON);
        return httpHeaders;
    }

}