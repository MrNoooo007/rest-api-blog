package com.congdat.springbootrestapi.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {

    public ErrorDetails(Date timestamp, String message, String details) {
    }
}
