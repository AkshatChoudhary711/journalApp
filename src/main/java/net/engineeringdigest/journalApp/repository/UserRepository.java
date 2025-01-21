package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends MongoRepository<User, ObjectId> {
   //Spring JPA will generate the implementation at runtime by interpreting
    // the method name as two parts "findBy" -> Query and "Username" -> property name.
    User findByUsername(String username);
    void deleteByUsername(String username);

}
