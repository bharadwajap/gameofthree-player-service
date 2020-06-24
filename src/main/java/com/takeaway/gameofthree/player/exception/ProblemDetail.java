package com.takeaway.gameofthree.player.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Resource class for exceptions
 *
 * @author Bharadwaj Adepu
 * @since 1.0-SNAPSHOT
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {

    private final String title;

    private final String detail;

    private String instance;

    private Integer status;

}
