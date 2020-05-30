package what.to.eat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import what.to.eat.dtos.UserDto;
import what.to.eat.entities.Users;
import what.to.eat.exception.WebApplicationException;
import what.to.eat.services.UsersService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for all user related endpoints.
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Controller", description = "user operations controller")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    UsersService usersService;

    /**
     * Retrieve all existing users
     * @return array with all existing users
     */
    @Operation(summary = "Retrieve all users.", description = "Retrieve all existing users.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    public ResponseEntity<List<String>> getAllUsers() {

        LOGGER.info("Calling getAllUsers() endpoint .. ");

        List<Users> users = usersService.getAllUsers();
        List<String> usernames = new ArrayList<>();
        for (Users user : users) {
            usernames.add(user.getUsername());
        }

        return ResponseEntity.status(HttpStatus.OK).body(usernames);
    }

    /**
     * Retrieve specific user by username
     * @param username - the username of the searched user
     * @return user
     */
    @Operation(summary = "Retrieve an user.", description = "Retrieve specific user by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User with this username is not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "username", required = true) @PathVariable("username") String username) {
        LOGGER.info("Calling get specific user endpoint .. ");

        Users user = usersService.getUserByUsername(username);
        if (user == null) {
            throw new WebApplicationException("There is no such user.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersService.convertToDto(user));
    }
}
