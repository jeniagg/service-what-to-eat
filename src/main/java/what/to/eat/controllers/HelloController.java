package what.to.eat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping(value = "/test", produces = "application/json")
	public ResponseEntity<String> printHello() {
		return new ResponseEntity<>("Hello to what to eat!", HttpStatus.OK);
	}

}