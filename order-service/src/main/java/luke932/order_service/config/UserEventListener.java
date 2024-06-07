package luke932.order_service.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

	private List<Map<String, Object>> userList = new ArrayList<>();


    private final String userListFilePath = "userList.json";

    @Autowired
    private ObjectMapper objectMapper;

    public UserEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadUsersFromJson();
    }

    @KafkaListener(topics = "user_created", groupId = "order_service")
    public void handleUserCreated(String message) {
        try {
            // Deserializza il messaggio JSON in un oggetto Map
            Map<String, Object> userMap = objectMapper.readValue(message, new TypeReference<>() {});

            // Aggiungi i dati dell'utente alla lista
            userList.add(userMap);

            // Salva la lista aggiornata in un file JSON
            saveUserListToFile();

            // Stampa la lista degli utenti aggiornata
            System.out.println("Lista degli utenti aggiornata: " + userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "user_updated", groupId = "order_service")
    public void handleUserUpdated(String message) {
        try {
            // Deserializza il messaggio JSON in un oggetto Map
            Map<String, Object> updatedUser = objectMapper.readValue(message, new TypeReference<>() {});

            // Trova e aggiorna l'utente nella lista
            for (Map<String, Object> user : userList) {
                if (user.get("id").equals(updatedUser.get("id"))) {
                    user.putAll(updatedUser);
                    break;
                }
            }

            // Salva la lista aggiornata nel file JSON
            saveUserListToFile();

            // Stampa la lista degli utenti aggiornata
            System.out.println("Lista degli utenti aggiornata dopo l'aggiornamento: " + userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @KafkaListener(topics = "user_deleted", groupId = "order_service")
    public void handleUserDeleted(String message) {
        try {
            // Deserializza il messaggio JSON in un oggetto Map
            Map<String, Object> deletedUser = objectMapper.readValue(message, new TypeReference<>() {});

            // Trova e rimuovi l'utente dalla lista
            userList.removeIf(user -> user.get("id").equals(deletedUser.get("id")));

            // Salva la lista aggiornata nel file JSON
            saveUserListToFile();

            // Stampa la lista degli utenti aggiornata
            System.out.println("Lista degli utenti aggiornata dopo l'eliminazione: " + userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Map<String, Object>> getUserList() {
        return userList;
    }

    private void saveUserListToFile() throws IOException {
        // Scrivi la lista degli utenti nel file JSON
        objectMapper.writeValue(new File(userListFilePath), userList);
        
        // Ottieni il percorso assoluto del file
        String absolutePath = new File(userListFilePath).getAbsolutePath();
        
        // Stampa un messaggio di conferma con il percorso del file
        System.out.println("Lista degli utenti salvata correttamente nel file JSON: " + absolutePath);
    }

    private void loadUsersFromJson() {
        try {
            File file = new File(userListFilePath);
            if (file.exists()) {
                // Carica la lista degli utenti dal file JSON
                userList = objectMapper.readValue(file, new TypeReference<>() {});
                System.out.println("Lista degli utenti caricata correttamente da file JSON.");
            } else {
                System.out.println("File JSON degli utenti non trovato. Creerà un nuovo file durante il salvataggio.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
