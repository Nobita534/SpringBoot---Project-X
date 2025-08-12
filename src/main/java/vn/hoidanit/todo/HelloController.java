package vn.hoidanit.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.hoidanit.todo.entity.Todo;
import vn.hoidanit.todo.entity.User;

@RestController
public class HelloController {

	@Autowired
	private ObjectMapper om;

	@GetMapping("/")
	public ResponseEntity<String> index() throws Exception {
//		json => object: frontend send data to backend
		String json = """
				{
					"name": "Trần Hồng Phúc",
					"email": "phuccupid2005@gmail.com"
				}
				""";
		User test = om.readValue(json, User.class);

		User linh = new User(null, "Mai Nguyễn Gia Linh", "linh@gmail.com");
		String linhJson = om.writeValueAsString(linh);

		return ResponseEntity.ok().body(linhJson);
	}

	@GetMapping("/phucchan")
	public ResponseEntity<Todo> phucchan() {
		Todo test = new Todo("Trần Hồng Phúc", true);
		return ResponseEntity.ok().body(test);
	}
}
