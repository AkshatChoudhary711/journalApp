package net.engineeringdigest.journalApp.controllers;


import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getAll(@PathVariable String username){
    List<JournalEntry> result = journalEntryService.getallJournals(username);
    if(result!=null && !result.isEmpty()){
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> add(@RequestBody JournalEntry myEntry,@PathVariable String username){
//        try{
            journalEntryService.saveEntry(myEntry,username);

            return new ResponseEntity<>(HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> journalEntryById(@PathVariable ObjectId id ){
        Optional<JournalEntry> result =  journalEntryService.getById(id);
        if(result.isPresent()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id, @PathVariable String username){

            journalEntryService.deleteById(id,username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry ){
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        if (old != null) {
            old.setTitle((newEntry.getTitle() == null || newEntry.getTitle().equals("")) ? old.getTitle() : newEntry.getTitle());
            old.setContent((newEntry.getContent() == null || newEntry.getContent().equals("")) ? old.getContent() : newEntry.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
