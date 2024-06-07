package luke932.user_service.impService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import luke932.user_service.entities.User;
import luke932.user_service.exceptions.EmailAlreadyExistsException;
import luke932.user_service.exceptions.GlobalExceptionHandler;
import luke932.user_service.exceptions.UserNotFoundException;
import luke932.user_service.exceptions.UserServiceException;
import luke932.user_service.repositories.UserRepository;
import luke932.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_CREATED = "user_created";
    private static final String TOPIC_UPDATED = "user_updated";
    private static final String TOPIC_DELETED = "user_deleted";

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(@Valid User user) {
        try {
            // Controlla se l'email è già presente nel database
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                throw new EmailAlreadyExistsException();
            }

            // Salva l'utente nel repository (database)
            User newUser = userRepository.save(user);

            // Assicurati che l'ID sia impostato correttamente prima di inviare il messaggio Kafka
            if (newUser.getId() != null) {
                // Converti l'oggetto User in una stringa JSON
                String userJson = objectMapper.writeValueAsString(newUser);

                // Invia il messaggio JSON al topic Kafka
                kafkaTemplate.send(TOPIC_CREATED, userJson);
            } else {
                System.err.println("L'ID dell'utente è null dopo il salvataggio.");
            }

            return newUser;
        } catch (EmailAlreadyExistsException e) {
            log.error("Email già esistente: {}", e.getMessage());
            throw e; // Lanciare nuovamente l'eccezione per consentire al gestore globale delle eccezioni di gestirla
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    @Transactional
    public User updateUser(Long id, @Valid User user) {
        try {
            // Trova l'utente nel database
            User updatedUser = userRepository.findById(id).orElse(null);
            if (updatedUser == null) {
                throw new UserNotFoundException("Utente non trovato");
            }

            // Controlla se l'email è già presente nel database (escludendo l'utente corrente)
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new EmailAlreadyExistsException();
            }

            // Aggiorna i dati dell'utente con quelli forniti
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());

            // Salva l'utente aggiornato nel repository (database)
            updatedUser = userRepository.save(updatedUser);

            // Assicurati che l'ID sia impostato correttamente prima di inviare il messaggio Kafka
            if (updatedUser.getId() != null) {
                // Converti l'oggetto User in una stringa JSON
                String userJson = objectMapper.writeValueAsString(updatedUser);

                // Invia il messaggio JSON al topic Kafka solo se l'email è valida
                if (isValidEmail(updatedUser.getEmail())) {
                    kafkaTemplate.send(TOPIC_UPDATED, userJson);
                } else {
                    log.error("Email non valida per l'utente con ID {}: {}", id, updatedUser.getEmail());
                    // Gestire il caso in cui l'email non sia valida
                }
            } else {
                log.error("L'ID dell'utente è null dopo l'aggiornamento.");
            }

            return updatedUser;
        } catch (UserNotFoundException e) {
            log.error("Utente non trovato con ID {}: {}", id, e.getMessage());
            throw e; // Lanciare nuovamente l'eccezione per consentire al gestore globale delle eccezioni di gestirla
        } catch (EmailAlreadyExistsException e) {
            log.error("Email già esistente: {}", e.getMessage());
            throw e; // Lanciare nuovamente l'eccezione per consentire al gestore globale delle eccezioni di gestirla
        } catch (Exception e) {
            log.error("Errore durante l'aggiornamento dell'utente con ID {}: {}", id, e.getMessage());
            throw new UserServiceException("Errore durante l'aggiornamento dell'utente", e);
        }
    }

    private boolean isValidEmail(String email) {
        // Espressione regolare per validare l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Verifica se l'email corrisponde alla regex
        return email.matches(emailRegex);
    }





    @Override
    @Transactional
    public void deleteUser(Long id) {
        // Trova l'utente da eliminare
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato con ID: " + id));

        // Elimina l'utente dal repository
        userRepository.delete(userToDelete);

        // Invia il messaggio di eliminazione dell'utente a Kafka
        try {
            // Converti l'oggetto User eliminato in una stringa JSON
            String deletedUserJson = objectMapper.writeValueAsString(userToDelete);

            // Invia il messaggio JSON al topic Kafka
            kafkaTemplate.send(TOPIC_DELETED, deletedUserJson);

            // Stampa un messaggio di conferma
            log.info("Utente eliminato con successo: {}", userToDelete);
        } catch (JsonProcessingException e) {
            log.error("Errore durante la conversione dell'utente eliminato in JSON: {}", e.getMessage());
            // Gestire il caso in cui si verifichi un errore durante la conversione in JSON
        }
    }


	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public List<User> findByEmailLike(String emailPattern) {
		return userRepository.findByEmailLike(emailPattern);
	}
}
