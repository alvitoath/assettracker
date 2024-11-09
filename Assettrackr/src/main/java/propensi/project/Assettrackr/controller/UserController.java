package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.request.CreateUserRequest;
import propensi.project.Assettrackr.model.dto.request.LoginRequest;
import propensi.project.Assettrackr.model.dto.request.UserUpdateRequest;
import propensi.project.Assettrackr.model.dto.response.ListUserResponse;
import propensi.project.Assettrackr.model.dto.response.UserResponse;
import propensi.project.Assettrackr.service.user.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request){
        try {
            String password = service.createUser(request);
            return ResponseEntity.ok(password);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        try {
            String jwt = service.login(request);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ListUserResponse> getAll(){
        ListUserResponse response = service.getAllUser();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getDetail(@PathVariable("id") String id){
        try {
            UserResponse response= service.getDetailUser(id);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request){
        try {
            boolean result = service.updateUser(id, request);
            if (result) return ResponseEntity.ok("success");
            else return ResponseEntity.badRequest().body("Data is not valid");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        try {
            boolean success = service.deleteUser(id);
            if (success) return ResponseEntity.ok("Success");
            return ResponseEntity.badRequest().body("User not found");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/login-active")
    public ResponseEntity<UserResponse> getActiveUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse user = service.getDetailUserByUsername(username);
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}