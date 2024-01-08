package com.vednovak.urlshortener.handlers;

import com.vednovak.urlshortener.account.exceptions.AccountRegisterException;
import com.vednovak.urlshortener.account.models.AccountResponse;
import com.vednovak.urlshortener.redirect.exceptions.RedirectNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(AccountRegisterException.class)
    public ResponseEntity<AccountResponse> handleAccountRegisterException(final AccountRegisterException ex) {
        AccountResponse accountResponse = ex.getAccountResponse();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accountResponse);
    }

    @ExceptionHandler(RedirectNullException.class)
    public ResponseEntity<String> handleRedirectException(final RedirectNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}