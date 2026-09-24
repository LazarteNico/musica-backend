package com.lazarte.musica_backend.config;

import com.lazarte.musica_backend.model.entity.*;
import com.lazarte.musica_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaybackRepository playbackRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Ya hay datos cargados
        }

        // 1. Usuarios
        User u1 = userRepository.save(User.builder().name("juanp").email("juan@mail.com").build());
        User u2 = userRepository.save(User.builder().name("mariag").email("maria@mail.com").build());

        // 2. Artistas
        Artist a1 = artistRepository.save(Artist.builder().name("Duki").mainGenre("Trap").nationality("Argentina").build());
        Artist a2 = artistRepository.save(Artist.builder().name("Bizarrap").mainGenre("Electronic").nationality("Argentina").build());

        // 3. Canciones
        Song s1 = songRepository.save(Song.builder().title("Goteo").durationSeconds(170).genre("Trap").artist(a1).build());
        Song s2 = songRepository.save(Song.builder().title("She Don't Give a FO").durationSeconds(210).genre("Trap").artist(a1).build());
        Song s3 = songRepository.save(Song.builder().title("BZRP Music Session #52").durationSeconds(200).genre("Electronic").artist(a2).build());

        // 4. Playlists
        Playlist p1 = Playlist.builder()
                .name("Favoritos Trap")
                .description("Lo mejor del trap")
                .user(u1)
                .songs(List.of(s1, s2))
                .build();
        playlistRepository.save(p1);

        // 5. Playbacks
        playbackRepository.save(Playback.builder().user(u1).song(s1).playedAt(LocalDateTime.now()).build());
        playbackRepository.save(Playback.builder().user(u2).song(s3).playedAt(LocalDateTime.now()).build());
    }
}