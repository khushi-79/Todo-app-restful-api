package com.restfulApiTodo.restful.api.to_do.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void testGetAllTodos() throws Exception{
        List<TodoItem> todos = Arrays.asList(
                new TodoItem(1L, "complete project", "restapi springboot", false, null, null),
                new TodoItem(2L, "take rest", "long weekend", false, null, null));

        Mockito.when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("complete project"))
                .andExpect(jsonPath("$[1].status").value(false));
    }

    @Test
    public void testCreateTodoItem() throws Exception {
        TodoItem newTodo = new TodoItem(null, "Task 3", "New Task", false, null, null);
        TodoItem savedTodo = new TodoItem(3L, "Task 3", "New Task", false, null, null);

        Mockito.when(todoService.createTodoItem(Mockito.any(TodoItem.class))).thenReturn(savedTodo);

        mockMvc.perform(post("/api/todos")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Task 3"));
    }

    @Test
    public void testGetTodoItemById() throws Exception {
        TodoItem todo = new TodoItem(4L, "Task 4", "Description 4", false, null, null);

        Mockito.when(todoService.getTodoItemById(4L)).thenReturn(java.util.Optional.of(todo));

        mockMvc.perform(get("/api/todos/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task 4"));
    }

    @Test
    public void testUpdateTodoItem() throws Exception {
        TodoItem updatedTodo = new TodoItem(1L, "complete project", "restapi springboot", true, null, null);

        Mockito.when(todoService.updateTodoItem(Mockito.any(TodoItem.class), Mockito.eq(1L))).thenReturn(updatedTodo);

        mockMvc.perform(put("/api/todos/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatedTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void testDeleteTodoItem() throws Exception {
        mockMvc.perform(delete("/api/todos/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetTodoItemById_NotFound() throws Exception {
        Mockito.when(todoService.getTodoItemById(999L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/todos/999"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testCreateTodoItem_Invalid() throws Exception {
//        TodoItem newTodo = new TodoItem(null, null, "Description", false, null, null);  // Title is missing
//
//        mockMvc.perform(post("/api/todos")
//                        .contentType("application/json")
//                        .content(new ObjectMapper().writeValueAsString(newTodo)))
//                .andExpect(status().isBadRequest());
//    }
}
