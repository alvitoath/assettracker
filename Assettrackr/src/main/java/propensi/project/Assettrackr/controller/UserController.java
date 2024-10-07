package propensi.project.Assettrackr.controller;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.*;
import propensi.project.Assettrackr.service.user.UserService;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService service;


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
    public ResponseEntity<UserResponse> getDetail(@PathVariable("id") Integer id){
        try {
            UserResponse response= service.getDetailUser(id);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}