package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    public List<JournalEntry> getallJournals(String username){
        User user =  userService.getByUsername(username);
        return (user != null)?user.getJournals():null;
    }

    @Transactional
    public void saveEntry(JournalEntry myEntry, String username){
        myEntry.setDate(LocalDate.now());
        JournalEntry entry = journalEntryRepository.save(myEntry);
        User user = userService.getByUsername(username);
        user.getJournals().add(entry);
        userService.saveOldEntry(user);
    }

    public void saveEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
    }



    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String username){
        boolean removed = false;
        try {
            User user =  userService.getByUsername(username);
            removed = user.getJournals().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveOldEntry(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry.",e);
        }
        return removed;

    }





}



