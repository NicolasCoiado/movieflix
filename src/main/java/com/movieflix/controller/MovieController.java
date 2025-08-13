package com.movieflix.controller;

import com.movieflix.controller.documentation.MovieControllerDoc;
import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.entity.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movieflix/movies")
@RequiredArgsConstructor
public class MovieController implements MovieControllerDoc {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> save (@Valid @RequestBody MovieRequest request){
        Movie savedMovie = movieService.save(MovieMapper.toMovie(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieMapper.toMovieResponse(savedMovie));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll (){
        return ResponseEntity.ok(movieService.findAll().stream().map(MovieMapper::toMovieResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById (@PathVariable Long id){
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update (@PathVariable Long id, @Valid @RequestBody MovieRequest request){
        return movieService.updateMovie(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/") //TODO: FIX NOT FOUND CATEGORY TO RETURN THIS ERROR TO USER
    public ResponseEntity<List<MovieResponse>> findByCategory (@RequestParam  List<Long> categoriesIds){

        List<MovieResponse> movies = movieService.findByCategory(categoriesIds)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList();
        return ResponseEntity.ok(movies);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        Optional<Movie> optMovie = movieService.findById(id);
        if (optMovie.isPresent()){
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
