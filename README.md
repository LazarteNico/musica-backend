# Red Social de Música API - TP1

API RESTful desarrollada con Spring Boot de una Red Social de Música.

# Descripción del Dominio y Entidades

5 entidades principales:

1. Usuario (`User`): Representa a los usuarios registrados en la plataforma.
   Atributos: `id` (Long),
              `name` (String), 
              `email` (String),
              `country` (String).
2. Artista (`Artist`): Representa a los músicos o bandas registradas.
   Atributos: `id` (Long), 
              `name` (String), 
              `mainGenre` (String),
              `monthlyListeners` (Integer).
3. Canción (`Song`): Pistas musicales publicadas por los artistas.
   Atributos: `id` (Long),
              `title` (String),
              `durationSeconds` (Integer),
              `artistId` (Long),
              `genre` (String).
4. Playlist (`Playlist`): Listas de reproducción personalizadas creadas por los usuarios.
   Atributos: `id` (Long),
              `name` (String),
              `userId` (Long),
              `songIds` (List<Long>).
5. Reproducción (`Playback`): Registro histórico de canciones reproducidas por los usuarios.
   Atributos: `id` (Long),
              `userId` (Long),
              `songId` (Long),
              `playedAt` (LocalDateTime).


# Instrucciones de Ejecución

Desde la terminal en la raíz del proyecto, ejecutar:

bash
# Con Maven instalado globalmente
mvn clean spring-boot:run

# O mediante Maven Wrapper (Linux / macOS)
./mvnw clean spring-boot:run

# O mediante Maven Wrapper (Windows CMD / PowerShell)
mvnw.cmd clean spring-boot:run