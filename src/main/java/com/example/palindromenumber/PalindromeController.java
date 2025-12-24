package com.example.palindromenumber;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class PalindromeController {

    @GetMapping("/palindrome/{number}")
    public ResponseEntity<Map<String, Object>> checkPalindrome(@PathVariable long number) {
        boolean isPalindrome = isPalindrome(number);
        Map<String, Object> response = Map.of(
            "number", number,
            "isPalindrome", isPalindrome
        );
        return ResponseEntity.ok(response);
    }

    private boolean isPalindrome(long number) {
        if (number < 0) {
            return false;
        }

        long original = number;
        long reversed = 0;
        while (number != 0) {
            long digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }
        return original == reversed;
    }
}

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = String.format("'%s' is not a valid number.", ex.getValue());
        Map<String, String> errorResponse = Collections.singletonMap("error", error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
