package com.senla.intership.boot.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final Integer statusCode;
    private final Date timestamp;
    private final String message;
    private final String description;
}