package com.example.idol.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.idol.entity.Artist;
import com.example.idol.repository.ArtistRepository;

import jakarta.transaction.Transactional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public void delete(Integer artistId) {
        artistRepository.deleteById(artistId);
    }

    // 新規登録時の保存
    public void save(Artist artist, MultipartFile file) throws IOException {
        String uploadDir = "static/images/"; 

        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename;

        int i = 1;
        while (Files.exists(Paths.get(uploadDir, filename))) {
            String name = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
            filename = name + "(" + i + ")" + ext;
            i++;
        }

        Files.write(Paths.get(uploadDir, filename), file.getBytes());

        artist.setArtistArtUrl("images/" + filename);

        artistRepository.save(artist);
    }

    public Artist findById(Integer artistId) {
        return artistRepository.findById(artistId).orElseGet(Artist::new);
    }

    // 更新処理（画像も含めて対応）
    @Transactional
    public Artist updateArtist(Artist update, MultipartFile file) throws IOException {
        Artist entity = artistRepository.findById(update.getArtistId())
                .orElseThrow(() -> new IllegalArgumentException("存在しないアーティスト"));

        entity.setArtistName(update.getArtistName());
        entity.setArtistHiraganaName(update.getArtistHiraganaName());

        // ファイルが選択されている場合だけ処理
        if (file != null && !file.isEmpty()) {
            String uploadDir = "static/images/"; 

            String originalFilename = file.getOriginalFilename();
            String filename = originalFilename;

            int i = 1;
            while (Files.exists(Paths.get(uploadDir, filename))) {
                String name = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
                String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
                filename = name + "(" + i + ")" + ext;
                i++;
            }

            Files.write(Paths.get(uploadDir, filename), file.getBytes());

            entity.setArtistArtUrl("images/" + filename);
        }

        return artistRepository.save(entity);
    }
}
