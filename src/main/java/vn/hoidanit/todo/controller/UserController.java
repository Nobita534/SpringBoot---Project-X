package vn.hoidanit.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.todo.entity.ApiResponse;
import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
		User created = userService.createUser(user);

//		2 cách viết: 
//		Cách 1: dùng var để bắt Spring đoán kiểu dữ liệu
		var result = new ApiResponse<>(HttpStatus.CREATED, "createUser", created, null);
//		Cách 2: gán thẳng kiểu dữ liệu
//		ApiResponse<User> result = new ApiResponse<User>(HttpStatus.CREATED, "createUser", created, null);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "getAllUsers", userService.getAllUsers(), null));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
		return userService.getUserById(id).map(user -> {
			var response = new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			ApiResponse<User> result = new ApiResponse<>(HttpStatus.NOT_FOUND, "Không tìm thấy ID với user: " + id,
					null, "USER NOT FOUND");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		});
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userService.updateUser(id, user);
		var result = new ApiResponse<>(HttpStatus.OK, "updateUser", updated, null);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		ApiResponse<User> result = new ApiResponse<>(HttpStatus.NO_CONTENT, "deleteUser", null, null);
		return ResponseEntity.ok(result);
	}
}
