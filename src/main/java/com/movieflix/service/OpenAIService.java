package com.movieflix.service;

import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.*;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private final ImageModel imageModel;
    private final GoogleDriveService googleDriveService;

    public String generate(Movie movie) {
        String categories = movie.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.joining(", "));

        String prompt = "Create a movie poster taking into account the following information:\n" +
                "The poster should only contain the movie title and a background image based on the following properties:\n" +
                "Title: " + movie.getTitle() + "\n" +
                "Description: " + movie.getDescription() + "\n" +
                "Categories: " + categories;

        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .N(1)
                .model("gpt-image-1")
                .width(1024)
                .height(1024)
                .quality("medium")
                .build();

        ImageResponse response = imageModel.call(new ImagePrompt(prompt, options));

        String b64 = response.getResult().getOutput().getB64Json();

        // Converte Base64 para bytes
        byte[] imageBytes = Base64.getDecoder().decode(b64);

        try {
            // cria arquivo temporário
            String fileName = UUID.randomUUID().toString() + ".png";
            java.io.File tempFile = java.io.File.createTempFile("poster-", ".png");
            try (OutputStream os = new FileOutputStream(tempFile)) {
                os.write(imageBytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "URL";
    }
}