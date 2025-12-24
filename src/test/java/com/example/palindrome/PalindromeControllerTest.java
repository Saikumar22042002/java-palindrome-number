package com.example.palindrome;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PalindromeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void whenPalindromeNumber_thenReturnsTrue() throws Exception {
    mockMvc.perform(get("/api/isPalindrome/121"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(121))
        .andExpect(jsonPath("$.isPalindrome").value(true));
  }

  @Test
  void whenNonPalindromeNumber_thenReturnsFalse() throws Exception {
    mockMvc.perform(get("/api/isPalindrome/123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(123))
        .andExpect(jsonPath("$.isPalindrome").value(false));
  }

  @Test
  void whenSingleDigitNumber_thenReturnsTrue() throws Exception {
    mockMvc.perform(get("/api/isPalindrome/7"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(7))
        .andExpect(jsonPath("$.isPalindrome").value(true));
  }

  @Test
  void whenZero_thenReturnsTrue() throws Exception {
    mockMvc.perform(get("/api/isPalindrome/0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(0))
        .andExpect(jsonPath("$.isPalindrome").value(true));
  }

  @Test
  void whenNegativeNumber_thenReturnsFalse() throws Exception {
    mockMvc.perform(get("/api/isPalindrome/-121"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(-121))
        .andExpect(jsonPath("$.isPalindrome").value(false));
  }

  @Test
  void healthCheckEndpoint_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/actuator/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("UP"));
  }
}
