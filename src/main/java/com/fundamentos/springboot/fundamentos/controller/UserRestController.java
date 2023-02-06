package com.fundamentos.springboot.fundamentos.controller;

import com.fundamentos.springboot.fundamentos.cause.CreateUser;
import com.fundamentos.springboot.fundamentos.cause.DeleteUser;
import com.fundamentos.springboot.fundamentos.cause.GetUser;
import com.fundamentos.springboot.fundamentos.cause.UpdateUser;
import com.fundamentos.springboot.fundamentos.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    //create, get, delete, update
    private GetUser getUser;
    private CreateUser createUser;
    private DeleteUser deleteUser;
    private UpdateUser updateUser;

    public UserRestController(GetUser getUser, CreateUser createUser, DeleteUser deleteUser,UpdateUser updateUser) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;

    }
    @GetMapping("/")
    //@ResponseBody
    List<User> get(){
        List<User> users = getUser.getAll();
        users.forEach(u -> System.out.println(u));
        return users;
    }
    @PostMapping("/")
    ResponseEntity<User> newUser(@RequestBody User newUser){
        return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@PathVariable Long id){
        deleteUser.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.update(newUser, id ), HttpStatus.OK);

    }
}
