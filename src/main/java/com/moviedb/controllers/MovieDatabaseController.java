package com.moviedb.controllers;

import com.moviedb.dtos.MovieDTO;
import com.moviedb.services.MovieDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieDatabaseController {

        MovieDatabaseService moviesService;

        @Autowired
        public MovieDatabaseController(MovieDatabaseService moviesService) {
            this.moviesService = moviesService;
        }

        @GetMapping("/")
        public ResponseEntity<List<MovieDTO>> getMovies() {
            List<MovieDTO> allMovies = moviesService.getAllMovies();
            return ResponseEntity.ok().body(allMovies);
        }

        @PostMapping("/")
        public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movie) {
            MovieDTO savedMovie = moviesService.saveMovie(movie);
            return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
        }

    @PostMapping("/bulk/")
    public ResponseEntity<List<MovieDTO>> createMovies(@RequestBody List<MovieDTO> movies) {
        List<MovieDTO> savedMovieList = moviesService.saveMovies(movies);
        return new ResponseEntity<>(savedMovieList, HttpStatus.CREATED);
    }



        @GetMapping("/{id}")
        public ResponseEntity<MovieDTO> getMovieWithId(@PathVariable String id) {
            Optional<MovieDTO> movie = moviesService.getMovie(Integer.valueOf(id));
            if (movie.isPresent()) {
                return ResponseEntity.ok().body(movie.get());
            }
            return ResponseEntity.notFound().build();
        }
    }

