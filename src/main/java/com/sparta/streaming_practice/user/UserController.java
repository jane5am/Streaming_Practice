package com.sparta.streaming_practice.user;

import com.sparta.streaming_practice.domain.User;
import com.sparta.streaming_practice.dto.ResponseMessage;
import com.sparta.streaming_practice.dto.user.CreateUserRequestDto;
import com.sparta.streaming_practice.dto.user.PutUserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseMessage> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ResponseMessage response = ResponseMessage.builder()
                .data(users)
                .statusCode(200)
                .resultMessage("Success")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            ResponseMessage response = ResponseMessage.builder()
                    .data(user.get())
                    .statusCode(200)
                    .resultMessage("Success")
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ResponseMessage response = ResponseMessage.builder()
                    .statusCode(404)
                    .resultMessage("User not found")
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        User user = new User();
        user.setEmail(createUserRequestDto.getEmail());
        user.setPassword(createUserRequestDto.getPassword());
        User createdUser = userService.createUser(user);
        ResponseMessage response = ResponseMessage.builder()
                .data(createdUser)
                .statusCode(201)
                .resultMessage("User created successfully")
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping()
    public ResponseEntity<ResponseMessage> updateUser(@RequestBody PutUserRequestDto userDetails) {
        try {
            User updatedUser = userService.updateUser(userDetails);
            ResponseMessage response = ResponseMessage.builder()
                    .data(updatedUser)
                    .statusCode(200)
                    .resultMessage("User updated successfully")
                    .build();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ResponseMessage response = ResponseMessage.builder()
                    .statusCode(404)
                    .resultMessage("User not found")
                    .detailMessage(e.getMessage())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            ResponseMessage response = ResponseMessage.builder()
                    .statusCode(204)
                    .resultMessage("User deleted successfully")
                    .build();
            return ResponseEntity.status(204).body(response);
        } catch (RuntimeException e) {
            ResponseMessage response = ResponseMessage.builder()
                    .statusCode(404)
                    .resultMessage("User not found")
                    .detailMessage(e.getMessage())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }
}