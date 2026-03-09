package com.packt.cardatabase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest          // Loads full Spring context including Security
@AutoConfigureMockMvc    // Injects a MockMvc instance automatically
class CarRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /login with valid credentials returns 200 OK")
    public void testAuthentication() throws Exception {
        this.mockMvc
                .perform(post("/login")
                        .content("{\"username\":\"admin\",\"password\":\"admin\"}")
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())          // Prints full request/response to console
                .andExpect(status().isOk());  // Asserts HTTP 200
    }
}

