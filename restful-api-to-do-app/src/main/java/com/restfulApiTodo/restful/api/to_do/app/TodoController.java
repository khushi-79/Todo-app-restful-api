package com.restfulApiTodo.restful.api.to_do.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoItem> getAllTodos(){
        return todoService.getAllTodos();
    }


    @PostMapping
    public TodoItem createTodoItem(@RequestBody TodoItem todoItem){
        return todoService.createTodoItem(todoItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Long id){
        return todoService.getTodoItemById(id)
                .map(todoItem -> ResponseEntity.ok(todoItem))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@RequestBody TodoItem updatedTodo, @PathVariable Long id){
        return ResponseEntity.ok(todoService.updateTodoItem(updatedTodo,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id){
        todoService.deleteTodoItem(id);
        return ResponseEntity.noContent().build();
    }

    //pagination
    @GetMapping("/paginated")
    public Page<TodoItem> getTodosPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        return todoService.getTodosWithPagination(page, size, sortBy);
    }
}
