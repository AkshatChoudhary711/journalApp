package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getall(){
        return userRepository.findAll();
    }

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean deleteByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user!=null){
            userRepository.deleteById(user.getId());
            return true;
        }return false;
    }



}
