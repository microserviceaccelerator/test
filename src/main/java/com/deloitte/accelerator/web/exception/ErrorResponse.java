package com.deloitte.accelerator.web.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Builder
@Getter
@RequiredArgsConstructor
public class ErrorResponse implements Serializable {
    private final String message;
}
