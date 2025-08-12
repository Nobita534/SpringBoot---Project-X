package vn.hoidanit.todo.Service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.repository.UserRepository;
import vn.hoidanit.todo.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

//	fake data with Mock
	@Mock
	private UserRepository userRepository;

//	InjectMock: to link to the Mock variable 
	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void createUser_shouldReturnUser_whenEmailValid() {
//		Arrange: Chuẩn bị data
		User inputUser = new User(null, "Trần Hồng Phúc", "phuccupid2005@gmail.com"); // Input data
		User outputUser = new User(1L, "Trần Hồng Phúc", "phuccupid2005@gmail.com"); // Output data which you want
																						// database to save
		when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(false);
		when(this.userRepository.save(any())).thenReturn(outputUser);

//		Act: Hành động
		User result = this.userService.createUser(inputUser);
//		Assert: So sánh
		assertEquals(1L, result.getId());
	}

	@Test
	public void createUser_shouldReturnException_whenEmailValid() {
//		Arrange: chuẩn bị data
		User inputUser = new User(null, "Trần Hồng Phúc", "phuccupid2005@gmail.com"); // Input data
		when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(true);
//		Act: hành động
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			this.userService.createUser(inputUser);
		});

//		Assert: so sánh 
		assertEquals("Email already exists", ex.getMessage());
	}

	@Test
	public void getAllUsers() {
//		Arrange: chuẩn bị data
		List<User> input = new ArrayList<>();
		input.add(new User(1L, "Trần Hồng Phúc", "a"));
		input.add(new User(2L, "Mai Nguyễn Gia Linh", "a"));

		when(this.userRepository.findAll()).thenReturn(input);

//		Act: hành động
		List<User> result = this.userService.getAllUsers();
//		Assert: so sánh 
		assertEquals(input.size(), result.size());
		assertEquals(input.get(0).getName(), result.get(0).getName());

	}

	@Test
	public void getUserById_shouldReturnUser() {
//		Arrange: chuẩn bị data
		Long id = 1L;
		Optional<User> outputUser = Optional.of(new User(1L, "Trần Hồng Phúc", "phuccupid2005@gmail.com"));

		when(this.userRepository.findById(id)).thenReturn(outputUser);
//		Act: hành động
		Optional<User> result = this.userService.getUserById(id);

//		Assert: so sánh 
		assertEquals(true, result.isPresent());
	}

	@Test
	public void deleteUser_shouldReturnVoid() {
//		Arrange: chuẩn bị data
		Long id = 1L;

		when(this.userRepository.existsById(id)).thenReturn(true);
//		Act: hành động
		this.userService.deleteUser(id);
//		Assert: so sánh 
		verify(this.userRepository).deleteById(id);
	}

	@Test
	public void deleteUser_shouldReturnException() {
//		Arrange: chuẩn bị data
		Long id = 1L;
		String check = "User not found";
		when(this.userRepository.existsById(id)).thenReturn(false);
//		Act: hành động
		Exception ex = assertThrows(NoSuchElementException.class, () -> {
			this.userService.deleteUser(id);
		});

//		Assert: so sánh 
		assertEquals(check, ex.getMessage());
	}

	@Test
	public void updateUser_shouldReturnUser_whenValid() {
//		Arrange: chuẩn bị data
		Long id = 1L;
		User inputUser = new User(1L, "Trần Hồng Phúc", "phuccupid2005@gmail.com");
		User outputUser = new User(1L, "Phúc Chần", "10a1tranhongphuc2021@gmail.com");

		when(this.userRepository.findById(id)).thenReturn(Optional.of(inputUser));
		when(this.userRepository.save(any())).thenReturn(outputUser);
//		Act: hành động
		User res = this.userService.updateUser(id, inputUser);
//		Assert: so sánh 
		assertEquals(outputUser.getName(), res.getName());
	}
}
