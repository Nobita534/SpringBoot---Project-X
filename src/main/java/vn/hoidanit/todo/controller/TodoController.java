package vn.hoidanit.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.todo.entity.Todo;
import vn.hoidanit.todo.service.TodoService;

@RestController
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	@GetMapping("/create-todos")
	public String createTodos() {
		Todo myTodo = new Todo("Trần Hồng Phúc", true);
		Todo newTodo = this.todoService.handleCreatetodo(myTodo);
		return "create todo with id: " + newTodo.getId();
	}

	@GetMapping("/todos")
	public String getTodos() {
		this.todoService.handleGettodos();
		return "get todos: ";
	}

	@GetMapping("/update-todos")
	public String updateTodos() {
		this.todoService.handleUpdatetodos();
		return "update a todos";
	}

	@GetMapping("/delete-todo")
	public String deleteTodos() {
		this.todoService.handleDeletetodos();
		return "delete a todos";
	}
}
