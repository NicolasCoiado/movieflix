package com.movieflix.service;

import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.*;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private final ImageModel imageModel;

    @Value("${path.images}")
    private String pathImages;

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

        byte[] decodedImageBytes = Base64.getDecoder().decode(b64);

        try {
            Path outImage = Files.createTempFile(
                    Paths.get(pathImages),
                    "img_",
                    ".png"
            );

            Files.write(outImage, decodedImageBytes);

            return outImage.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}