package com.example.palindrome;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PalindromeController {

  /**
   * Checks if a given number is a palindrome.
   *
   * @param number The number to check.
   * @return A response entity containing the original number and whether it is a palindrome.
   */
  @GetMapping("/isPalindrome/{number}")
  public ResponseEntity<Map<String, Object>> isPalindrome(@PathVariable long number) {
    boolean isPalindromeResult = checkPalindrome(number);
    Map<String, Object> response = Map.of(
        "number", number,
        "isPalindrome", isPalindromeResult
    );
    return ResponseEntity.ok(response);
  }

  private boolean checkPalindrome(long number) {
    if (number < 0) {
      return false;
    }

    long originalNumber = number;
    long reversedNumber = 0;

    while (number != 0) {
      long remainder = number % 10;
      reversedNumber = reversedNumber * 10 + remainder;
      number /= 10;
    }

    return originalNumber == reversedNumber;
  }
}
