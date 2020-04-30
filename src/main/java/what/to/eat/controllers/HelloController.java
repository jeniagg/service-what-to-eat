package what.to.eat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	@RequestMapping(method = RequestMethod.GET, value = "/test", produces = "application/json")
	public ResponseEntity<String> printHello() {
		return new ResponseEntity<String>("Hello to what to eat!", HttpStatus.OK);
	}

}