package what.to.eat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Tag(name = "Hello Controller", description = "test controller")
public class HelloController {

	@Operation(summary = "Retrieve Hello!", description = "Greeting from the application!", tags = { "test" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = ResponseEntity.class))) })
	@GetMapping(value = "/test", produces = "application/txt")
	public ResponseEntity<String> printHello() {
		return new ResponseEntity<>("Hello to what to eat!", HttpStatus.OK);
	}

}