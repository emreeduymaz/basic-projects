package online.auction.system.controller;


import online.auction.system.model.Users;
import online.auction.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> findAll(){
        return  ResponseEntity.ok(service.getAll());
    }

    @GetMapping("userInfo/{requestedId}")
    public ResponseEntity<Optional<Users>> findById(@PathVariable("requestedId") Long requestedId) {
        try {
            return ResponseEntity.ok(service.getById(requestedId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Users> addUser(@RequestBody Users newUser){
        Users addedUser=service.addUser(newUser);
        if(addedUser!=null){
            return new ResponseEntity<>(addedUser,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<Users> updateUser(@RequestBody Users user) {
        try {
            return ResponseEntity.ok(service.updateUser(user));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            service.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
