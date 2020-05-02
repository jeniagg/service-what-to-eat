package what.to.eat.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@ApiOperation(value = "Retrieve Hello greeting ")
	@ApiResponse(code = 200, message = "Suceess|OK")
	@GetMapping(value = "/test")
	public ResponseEntity<String> printHello() {
		return new ResponseEntity<>("Hello to what to eat!", HttpStatus.OK);
	}

}