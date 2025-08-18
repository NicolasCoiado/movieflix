package com.movieflix.service;

import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import com.movieflix.exception.CategoryNotFoundException;
import com.movieflix.exception.StreamingNotFoundException;
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
    private final OpenAIService openAIService;

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

    public Optional<Movie> updateMovie(Long movieId, Movie updateMovie) {
        Optional<Movie> optMovie = repository.findById(movieId);
        if (optMovie.isPresent()) {

            List<Long> categoryIdsRequested = updateMovie.getCategories()
                    .stream()
                    .map(Category::getId)
                    .toList();

            List<Category> categoriesFound = this.findCategories(updateMovie.getCategories());
            List<Long> missingCategoryIds = categoryIdsRequested.stream()
                    .filter(id -> categoriesFound.stream().noneMatch(c -> c.getId().equals(id)))
                    .toList();

            if (!missingCategoryIds.isEmpty()) {
                throw new CategoryNotFoundException("Categories not found: " + missingCategoryIds);
            }

            List<Long> streamingIdsRequested = updateMovie.getStreamings()
                    .stream()
                    .map(Streaming::getId)
                    .toList();

            List<Streaming> streamingsFound = this.findStreamings(updateMovie.getStreamings());
            List<Long> missingStreamingIds = streamingIdsRequested.stream()
                    .filter(id -> streamingsFound.stream().noneMatch(s -> s.getId().equals(id)))
                    .toList();

            if (!missingStreamingIds.isEmpty()) {
                throw new StreamingNotFoundException("Streamings not found: " + missingStreamingIds);
            }

            Movie movie = optMovie.get();
            movie.setTitle(updateMovie.getTitle());
            movie.setDescription(updateMovie.getDescription());
            movie.setReleaseDate(updateMovie.getReleaseDate());
            movie.setRating(updateMovie.getRating());

            movie.getCategories().clear();
            movie.getCategories().addAll(categoriesFound);

            movie.getStreamings().clear();
            movie.getStreamings().addAll(streamingsFound);

            return Optional.of(repository.save(movie));
        }

        return Optional.empty();
    }

    public Optional<Movie> editMovie(Long movieId, Movie editMovie) {
        Optional<Movie> optMovie = repository.findById(movieId);
        if (optMovie.isPresent()) {

            Movie movie = optMovie.get();

            if (editMovie.getCategories() != null && !editMovie.getCategories().isEmpty()) {
                List<Long> categoryIdsRequested = editMovie.getCategories()
                        .stream()
                        .map(Category::getId)
                        .toList();

                List<Category> categoriesFound = this.findCategories(editMovie.getCategories());
                List<Long> missingCategoryIds = categoryIdsRequested.stream()
                        .filter(id -> categoriesFound.stream().noneMatch(c -> c.getId().equals(id)))
                        .toList();

                if (!missingCategoryIds.isEmpty()) {
                    throw new CategoryNotFoundException("Categories not found: " + missingCategoryIds);
                }

                movie.getCategories().clear();
                movie.getCategories().addAll(categoriesFound);
            }

            if (editMovie.getStreamings() != null && !editMovie.getStreamings().isEmpty()) {
                List<Long> streamingIdsRequested = editMovie.getStreamings()
                        .stream()
                        .map(Streaming::getId)
                        .toList();

                List<Streaming> streamingsFound = this.findStreamings(editMovie.getStreamings());
                List<Long> missingStreamingIds = streamingIdsRequested.stream()
                        .filter(id -> streamingsFound.stream().noneMatch(s -> s.getId().equals(id)))
                        .toList();

                if (!missingStreamingIds.isEmpty()) {
                    throw new StreamingNotFoundException("Streamings not found: " + missingStreamingIds);
                }

                movie.getStreamings().clear();
                movie.getStreamings().addAll(streamingsFound);
            }

            if (editMovie.getTitle() != null) {
                movie.setTitle(editMovie.getTitle());
            }
            if (editMovie.getDescription() != null) {
                movie.setDescription(editMovie.getDescription());
            }
            if (editMovie.getReleaseDate() != null) {
                movie.setReleaseDate(editMovie.getReleaseDate());
            }
            if (editMovie.getRating() != null) {
                movie.setRating(editMovie.getRating());
            }

            return Optional.of(repository.save(movie));
        }

        return Optional.empty();
    }


    public List<Movie> findByCategory(List<Long> categoriesIds) {
        List<Long> idsNotFound = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for (Long id : categoriesIds) {
            categoryService.findByCategoryId(id)
                    .ifPresentOrElse(
                            categories::add,
                            () -> idsNotFound.add(id)
                    );
        }

        if (!idsNotFound.isEmpty()) {
            throw new CategoryNotFoundException("Categories not found: " + idsNotFound);
        }

        return repository.findByCategoriesIn(categories);
    }

    public void delete(Long movieId){
        repository.deleteById(movieId);
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

    public String generateImg (Long id){
        Optional<Movie> optMovie = repository.findById(id);

        if (optMovie.isPresent()) {
            Movie movie = optMovie.get();

            String urlGenerated = openAIService.generate(movie);

            movie.setUrlImg(urlGenerated);

            repository.save(movie);

            return urlGenerated;
        }

        return null;
    }
}
