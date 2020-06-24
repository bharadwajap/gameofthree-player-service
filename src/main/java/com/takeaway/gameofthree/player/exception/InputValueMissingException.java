package com.takeaway.gameofthree.player.exception;

import lombok.Getter;

/**
 * {@link InputValueMissingException} has to be thrown by service layer whenever resource cannot be found
 *
 */

@Getter
public class InputValueMissingException extends RuntimeException {

	private static final long serialVersionUID = -4782406348983885747L;

    /**
     * Instantiates a new instance of {@link InputValueMissingException}.
     *
     * @param resourceType type of the resource which cannot be found
     * @param resourceId   an identifier of the resource which cannot be found
     */
    public InputValueMissingException() {
    }

    /**
     * Instantiates a new instance of {@link InputValueMissingException}.
     *
     * @param cause        an original cause of the error
     */
    public InputValueMissingException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new instance of {@link InputValueMissingException}.
     *
     * @param message      custom message
     * @param cause        an original cause of the error
     * @param resourceType type of the resource which cannot be found
     * @param resourceId   an identifier of the resource which cannot be found
     */
    public InputValueMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
