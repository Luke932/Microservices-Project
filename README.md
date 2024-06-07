🚀 Microservices Project 🚀

Questo progetto è parte di un'architettura a microservizi che include i servizi User Service e Order Service. I due servizi comunicano tra loro tramite la tecnologia Apache Kafka.
User Service 🧑‍💼

Il servizio User Service gestisce le informazioni sugli utenti nel sistema. Fornisce funzionalità per la gestione degli utenti, inclusa la creazione, la modifica, la cancellazione e il recupero delle informazioni sugli utenti.
Order Service 🛒

Il servizio Order Service gestisce gli ordini all'interno del sistema. Fornisce funzionalità per la gestione degli ordini, inclusa la creazione, la modifica, la cancellazione e il recupero delle informazioni sugli ordini. Quando viene creato un nuovo utente tramite il servizio User Service, l'Order Service riceve un messaggio da Kafka contenente le informazioni sull'utente e le salva in un file JSON. Quando viene creato un nuovo ordine, l'Order Service recupera la lista degli utenti dal file JSON e permette all'utente di selezionare uno dalla lista tramite ID.
Comunicazione tramite Apache Kafka 💌

User Service e Order Service comunicano tra loro tramite Apache Kafka, un sistema di messaggistica distribuita. Ogni volta che viene creato un nuovo utente tramite l'User Service, viene inviato un messaggio al topic di Kafka. L'Order Service ascolta il topic e aggiunge il nuovo utente alla lista JSON.
Funzionalità principali di User Service 📝

    Creazione utente: Possibilità di creare un nuovo utente specificando il nome e l'email.
    Aggiornamento utente: Consentire agli utenti di aggiornare le proprie informazioni.
    Cancellazione utente: Possibilità di eliminare un utente esistente dal sistema.
    Recupero informazioni utente: Fornire dettagli sugli utenti, inclusi nome, email e altri dettagli pertinenti.

Funzionalità principali di Order Service 📦

    Creazione ordine: Possibilità di creare un nuovo ordine specificando gli articoli ordinati, le informazioni sulla spedizione, il metodo di pagamento e lo stato dell'ordine.
    Aggiornamento ordine: Consentire agli utenti di aggiornare le informazioni sugli ordini esistenti.
    Cancellazione ordine: Possibilità di eliminare un ordine esistente dal sistema.
    Recupero informazioni ordine: Fornire dettagli sugli ordini, inclusi articoli ordinati, informazioni sulla spedizione, metodo di pagamento e stato dell'ordine.

Dipendenze 📦

Il progetto dipende dalle seguenti tecnologie e framework:

    Spring Boot
    Spring Data JPA
    Apache Kafka
    PostgreSQL Database (per lo sviluppo)

Configurazione ⚙️
Configurazione del Database

Il servizio utilizza un database PostgreSQL per l'archiviazione dei dati. La configurazione del database è definita nel file application.properties.

properties

# Impostazioni del database PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driverClassName=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

Configurazione di Apache Kafka

La configurazione di Apache Kafka è definita nel file application.properties.

properties

# Configurazione di Apache Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=user-service-group
spring.kafka.consumer.auto-offset-reset=earliest

Utilizzo 🛠️

Dopo aver avviato i servizi, puoi utilizzare le seguenti API per interagire con gli utenti e gli ordini:
User Service
Creazione utente

http

POST /api/users
Content-Type: application/json

{
    "name": "Mario Rossi",
    "email": "mario.rossi@example.com"
}

Recupero di tutti gli utenti

http

GET /api/users

Recupero di un utente per ID

http

GET /api/users/{id}

Aggiornamento utente

http

PUT /api/users/{id}
Content-Type: application/json

{
    "name": "Mario Bianchi",
    "email": "mario.bianchi@example.com"
}

Eliminazione utente

http

DELETE /api/users/{id}

Order Service
Creazione ordine

http

POST /api/orders
Content-Type: application/json

{
    "articoli_ordinati": "Prodotto A, Prodotto B",
    "info_spedizione": "Indirizzo di spedizione",
    "metodo_pagamento": "Carta di credito",
    "status": "In attesa",
    "userId": 123
}

Recupero di tutti gli ordini

http

GET /api/orders

Recupero di un ordine per ID

http

GET /api/orders/{id}

Aggiornamento ordine

http

PUT /api/orders/{id}
Content-Type: application/json

{
    "status": "Consegnato"
}

ChatGPT può c
