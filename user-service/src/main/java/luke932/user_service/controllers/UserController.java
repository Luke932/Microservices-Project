package luke932.user_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import luke932.user_service.entities.User;
import luke932.user_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService usSv;
	
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
	   User createdUser = usSv.createUser(user);
	   return ResponseEntity.ok(createdUser);
	}
	 
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = usSv.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user = usSv.getUserById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
    @PutMapping("/{id}") // Aggiorna un utente
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = usSv.updateUser(id, user);
        
        if(existingUser == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}") // Deletes a user
    public ResponseEntity deleteUser(@PathVariable Long id) {
        usSv.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findByName/{name}") // Finds users by name (custom query)
    public ResponseEntity<List<User>> findByName(@PathVariable String name) {
        List<User> users = usSv.findByName(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/findByEmailLike/{emailPattern}") // Finds users by email pattern (custom query)
    public ResponseEntity<List<User>> findByEmailLike(@PathVariable String emailPattern) {
        List<User> users = usSv.findByEmailLike(emailPattern);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
