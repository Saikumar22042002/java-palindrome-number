package com.example.palindromenumber;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PalindromeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenPalindrome_thenReturnTrue() throws Exception {
        mockMvc.perform(get("/palindrome/121"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(true));
    }

    @Test
    void whenNotPalindrome_thenReturnFalse() throws Exception {
        mockMvc.perform(get("/palindrome/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(false));
    }

    @Test
    void whenSingleDigit_thenReturnTrue() throws Exception {
        mockMvc.perform(get("/palindrome/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(true));
    }

    @Test
    void whenNegativeNumber_thenReturnFalse() throws Exception {
        mockMvc.perform(get("/palindrome/-121"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(false));
    }

    @Test
    void whenZero_thenReturnTrue() throws Exception {
        mockMvc.perform(get("/palindrome/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPalindrome").value(true));
    }

    @Test
    void whenInvalidInput_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/palindrome/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("'abc' is not a valid number."));
    }

    @Test
    void healthCheckShouldReturnOk() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
