package com.github.dimka9910.documents.service.Exceprions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found")
public class NotFoundException extends RuntimeException{
}
