package com.movieflix.service;

import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import com.movieflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public Movie save (Movie movie){
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreamings(movie.getStreamings()));
        return repository.save(movie);
    }

    public List<Movie> findAll (){
        return repository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return repository.findById(id);
    }

    public List<Category> findCategories (List<Category> categories){
        List<Category> categoriesFound = new ArrayList<>();
        for (Category category : categories){
            categoryService.findByCategoryId(category.getId()).ifPresent(categoriesFound::add);
        }

        return categoriesFound;
    }

    public List<Streaming> findStreamings (List<Streaming> streamings){
        List<Streaming> streamingFound = new ArrayList<>();
        for (Streaming streaming : streamings){
            streamingService.findById(streaming.getId()).ifPresent(streamingFound::add);
        }

        return streamingFound;
    }
}
