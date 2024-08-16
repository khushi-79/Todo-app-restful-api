package com.restfulApiTodo.restful.api.to_do.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoItem> getAllTodos(){
        return todoRepository.findAll();
    }

    public TodoItem createTodoItem(TodoItem todoItem){
        return todoRepository.save(todoItem);
    }

    public Optional<TodoItem> getTodoItemById(Long id){
        return todoRepository.findById(id);
    }

    public TodoItem updateTodoItem(TodoItem updatedTodo, Long id){
        return todoRepository.findById(id)
                .map(todo ->{
                    todo.setTitle(updatedTodo.getTitle());
                    todo.setDescription(updatedTodo.getDescription());
                    todo.setStatus(updatedTodo.getStatus());
                    return todoRepository.save(todo);
                })
                .orElseThrow(()->new RuntimeException("Todo not found"));
    }

    public void deleteTodoItem(Long id){
        todoRepository.deleteById(id);
    }

    //add pagination and sorting
    public Page<TodoItem> getTodosWithPagination(int page, int size, String sortBy){
        return todoRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy)));
    }
}
