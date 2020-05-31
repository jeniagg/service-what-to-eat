package what.to.eat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import what.to.eat.dtos.BasicUserDto;
import what.to.eat.dtos.UpdateUserDto;
import what.to.eat.dtos.UserDto;
import what.to.eat.entities.Users;
import what.to.eat.exception.WebApplicationException;
import what.to.eat.services.UsersService;

import java.security.NoSuchAlgorithmException;
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
    public ResponseEntity<BasicUserDto> getUser(
            @Parameter(description = "username", required = true) @PathVariable("username") String username) {
        LOGGER.info("Calling get specific user endpoint .. ");

        Users user = usersService.getUserByUsername(username);
        if (user == null) {
            throw new WebApplicationException("There is no such user.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersService.convertToBasicDto(user));
    }

    /**
     * Create new user with the given body
     * @param userDto - the body of the user to be saved
     * @return the new user
     * @throws WebApplicationException
     */
    @Operation(summary = "Create an user.", description = "Create new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Mandatory value is missing from the body or" +
                    " not allowed value is passed for some of the properties", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "User with this username already exists",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "415", description = "Missing body", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        LOGGER.info("Calling create specific user endpoint .. ");

        if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword()) ||
                StringUtils.isBlank(userDto.getEmail())) {
            throw new WebApplicationException("There are missing required properties.", HttpStatus.BAD_REQUEST);
        }

        if (!usersService.isUsernameUnique(userDto.getUsername())) {
            throw new WebApplicationException("User with this username already exists.", HttpStatus.CONFLICT);
        }

        if (!usersService.isEmailValid(userDto.getEmail())) {
            throw new WebApplicationException("This is not a valid email", HttpStatus.BAD_REQUEST);
        }

        if (!usersService.isPasswordValid(userDto.getPassword(), userDto.getRepeatPassword())) {
            throw new WebApplicationException("This is not a valid password", HttpStatus.BAD_REQUEST);
        }

        Users user = usersService.convertToEntity(userDto);
        try {
            usersService.saveUser(user);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage(), ex.getStackTrace());
            throw new WebApplicationException("Error occurs while saving the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("The new user was successfully created");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Update user with the given body
     * @param username - the username of the user who will be updated
     * @param userDto - the body of the user to be updated with
     * @return the updated user
     * @throws WebApplicationException
     */
    @Operation(summary = "Update an user.", description = "Update existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Mandatory value is missing from the body or" +
                    " not allowed value is passed for some of the properties", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "User with this username do not exists",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "415", description = "Missing body", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
    })
    @PutMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "username", required = true) @PathVariable("username") String username,
            @RequestBody UpdateUserDto userDto) throws NoSuchAlgorithmException {

        LOGGER.info("Calling update specific user endpoint .. ");

        if (usersService.getUserByUsername(username) == null) {
            throw new WebApplicationException("There is no such user.", HttpStatus.NOT_FOUND);
        }

        if (StringUtils.isBlank(userDto.getOldPassword()) || StringUtils.isBlank(userDto.getPassword()) ||
                StringUtils.isBlank(userDto.getRepeatPassword()) || StringUtils.isBlank(userDto.getEmail())) {
            throw new WebApplicationException("There are missing required properties.", HttpStatus.BAD_REQUEST);
        }

        if (!usersService.areCredentialsValid(username, userDto.getOldPassword())) {
            throw new WebApplicationException("Invalid combination from username and password", HttpStatus.BAD_REQUEST);
        }

        if (!usersService.isEmailValid(userDto.getEmail())) {
            throw new WebApplicationException("This is not a valid email", HttpStatus.BAD_REQUEST);
        }

        if (!usersService.isPasswordValid(userDto.getPassword(), userDto.getRepeatPassword())) {
            throw new WebApplicationException("This is not a valid password", HttpStatus.BAD_REQUEST);
        }

        try {
            usersService.updateUser(username, userDto.getPassword(), userDto.getEmail());
        } catch (NoSuchAlgorithmException ex){
            LOGGER.error(ex.getMessage(), ex.getStackTrace());
            throw new WebApplicationException("Error occurs while updating the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("The user was successfully updated");

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
