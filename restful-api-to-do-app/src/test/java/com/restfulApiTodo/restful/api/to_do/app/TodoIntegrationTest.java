package com.restfulApiTodo.restful.api.to_do.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateTodoItem() throws Exception {
        String jsonRequest = "{ \"title\": \"New Task\", \"description\": \"Description of the new task\", \"status\": false }";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.description").value("Description of the new task"))
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    public void testGetTodoItemById() throws Exception {
        mockMvc.perform(get("/api/todos/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Plan Weekend Getaway"))
                .andExpect(jsonPath("$.description").value("Book a hotel and plan activities for a weekend trip."))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void testUpdateTodoItem() throws Exception {
        String jsonRequest = "{ \"title\": \"Updated Task\", \"description\": \"Updated description\", \"status\": true }";

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void testDeleteTodoItem() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }

    //invalid input
    @Test
    public void testCreateTodoItemWithInvalidData() throws Exception {
        String invalidJsonRequest = "{ \"title\": \"\", \"description\": \"Description\", \"status\": false }";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }


}
