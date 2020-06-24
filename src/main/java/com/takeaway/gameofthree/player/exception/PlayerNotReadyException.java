package com.takeaway.gameofthree.player.exception;

import lombok.Getter;

/**
 * {@link PlayerNotReadyException} has to be thrown by service layer whenever Player cannot be found
 *
 */

@Getter
public class PlayerNotReadyException extends RuntimeException {

	private static final long serialVersionUID = -4782406348983885747L;

    private final String player;

    /**
     * Instantiates a new instance of {@link PlayerNotReadyException}.
     *
     * @param player type of the resource which cannot be found
     * @param resourceId   an identifier of the resource which cannot be found
     */
    public PlayerNotReadyException(String player) {
        this.player = player;
    }

    /**
     * Instantiates a new instance of {@link PlayerNotReadyException}.
     *
     * @param cause        an original cause of the error
     * @param player type of the resource which cannot be found
     * @param resourceId   an identifier of the resource which cannot be found
     */
    public PlayerNotReadyException(Throwable cause, String player) {
        super(cause);
        this.player = player;
    }

    /**
     * Instantiates a new instance of {@link PlayerNotReadyException}.
     *
     * @param message      custom message
     * @param cause        an original cause of the error
     * @param player type of the resource which cannot be found
     * @param resourceId   an identifier of the resource which cannot be found
     */
    public PlayerNotReadyException(String message, Throwable cause, String player) {
        super(message, cause);
        this.player = player;
    }
}
