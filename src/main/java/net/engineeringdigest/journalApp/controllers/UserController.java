package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> userById(@PathVariable String username ){
        User result =  userService.getByUsername(username);
        if(result!=null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username){
        if(userService.deleteByUsername(username)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public ResponseEntity<?> updateEntry(@RequestBody User newUser){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

        User old = userService.getByUsername(authentication.getName());


            old.setUsername(newUser.getUsername());
            old.setPassword(newUser.getPassword());
            userService.saveEntry(old);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
