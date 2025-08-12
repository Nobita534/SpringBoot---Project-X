package vn.hoidanit.todo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.todo.entity.Todo;
import vn.hoidanit.todo.repository.TodoRepository;

@Service
public class TodoService {

	private TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	public Todo handleCreatetodo(Todo todo) {
		Todo createdTodo = this.todoRepository.save(todo);
		return createdTodo;
	}

	public void handleGettodos() {
//		List<Todo> todos = this.todoRepository.findAll();
//		todos.forEach(System.out::println);
		Optional<Todo> todoOptional = this.todoRepository.findByUsername("Trần Hồng Phúc");
		if (todoOptional.isPresent()) {
			System.out.println(todoOptional.get().toString());
		}
	}

	public void handleUpdatetodos() {
		Optional<Todo> todoOptional = this.todoRepository.findById(1L);
		if (todoOptional.isPresent()) {
			Todo currentTodo = todoOptional.get();

			currentTodo.setComplete(false);
			currentTodo.setUsername("Phúc Chần");
			this.todoRepository.save(currentTodo);
		}
	}

	public void handleDeletetodos() {
		this.todoRepository.deleteById(1L);
	}

}
