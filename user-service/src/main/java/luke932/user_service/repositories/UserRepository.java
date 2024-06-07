package luke932.user_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import luke932.user_service.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 Optional<User> findByEmail(String email);
	
    // Find all users by name (custom JPQL query)
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByName(@Param("name") String name);

    // Find all users with email like a given pattern (custom native SQL query)
    @Query(nativeQuery = true, value = "SELECT * FROM Utenti WHERE email LIKE ?1")
    List<User> findByEmailLike(String emailPattern);
}
