package com.example.palindromenumber;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PalindromeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheckEndpointShouldReturnOk() throws Exception {
        mockMvc.perform(get("/actuator/health"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status", is("UP")));
    }

    @Test
    void shouldReturnTrueForPalindromeNumber() throws Exception {
        mockMvc.perform(get("/palindrome/121"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.number", is(121)))
               .andExpect(jsonPath("$.isPalindrome", is(true)));
    }

    @Test
    void shouldReturnFalseForNonPalindromeNumber() throws Exception {
        mockMvc.perform(get("/palindrome/123"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.number", is(123)))
               .andExpect(jsonPath("$.isPalindrome", is(false)));
    }

    @Test
    void shouldReturnTrueForSingleDigitNumber() throws Exception {
        mockMvc.perform(get("/palindrome/7"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.isPalindrome", is(true)));
    }

    @Test
    void shouldReturnFalseForNegativeNumber() throws Exception {
        mockMvc.perform(get("/palindrome/-121"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.isPalindrome", is(false)));
    }

    @Test
    void shouldReturnBadRequestForInvalidNumber() throws Exception {
        mockMvc.perform(get("/palindrome/not-a-number"))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.error", is("Bad Request")))
               .andExpect(jsonPath("$.message", is("'number' should be a valid 'long' but received 'not-a-number'")));
    }
}
