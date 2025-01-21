package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
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

   @Autowired
   private UserRepository userRepository;

    @DeleteMapping()
    public ResponseEntity<?> deleteUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
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
