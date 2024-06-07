package luke932.user_service.service;

import java.util.List;

import luke932.user_service.entities.User;

//user_service project

//UserService (Service Interface)
public interface UserService {

	 User createUser(User user);
	
	 List<User> getAllUsers();
	
	 User getUserById(Long id);
	
	 User updateUser(Long id, User user);
	
	 void deleteUser(Long id);
	
	 List<User> findByName(String name);
	
	 List<User> findByEmailLike(String emailPattern);
}

