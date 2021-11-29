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
        List<MovieDTO> savedMovieList = moviesService.createMovies(movies);
        return new ResponseEntity<>(savedMovieList, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieWithId(@PathVariable String id) {
        Optional<MovieDTO> movie = moviesService.getMovieByID(Integer.valueOf(id));
        if (movie.isPresent()) {
            return ResponseEntity.ok().body(movie.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byActor")
    public ResponseEntity<List<MovieDTO>> getMovieWithActorName(@RequestParam String name) {
        Optional<List<MovieDTO>> moviesByActor = moviesService.getMovieByActorName(name);
        if (moviesByActor.isPresent()) {
            return ResponseEntity.ok().body(moviesByActor.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byDirector")
    public ResponseEntity<List<MovieDTO>> getMovieWithDirectorName(@RequestParam String name) {
        Optional<List<MovieDTO>> moviesByDirector = moviesService.getMovieByDirectorName(name);
        if (moviesByDirector.isPresent()) {
            return ResponseEntity.ok().body(moviesByDirector.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMovie(@RequestBody MovieDTO requestMovieDto, @PathVariable Integer id) {

        Optional<MovieDTO> movieDTOOptional = moviesService.getMovieByID(id);

        if (!movieDTOOptional.isPresent())
            return ResponseEntity.notFound().build();

        MovieDTO updatedMovieDTO = movieDTOOptional.get();

        if (requestMovieDto.getActorName() !=null && !requestMovieDto.getActorName().isEmpty())
            updatedMovieDTO.setActorName(requestMovieDto.getActorName());

        if (requestMovieDto.getDirectorName()!= null && !requestMovieDto.getDirectorName().isEmpty()) {
            updatedMovieDTO.setDirectorName(requestMovieDto.getDirectorName());
        }

        if (requestMovieDto.getMovieName() !=null && !requestMovieDto.getMovieName().isEmpty()) {
            updatedMovieDTO.setMovieName(requestMovieDto.getMovieName());
        }
        MovieDTO savedMovie = moviesService.updateMovie(updatedMovieDTO);

        return new ResponseEntity<>(savedMovie, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMovie(@PathVariable Integer id) {
        Optional<MovieDTO> movieDTOOptional = moviesService.getMovieByID(id);

        if (!movieDTOOptional.isPresent())
            return ResponseEntity.notFound().build();

        moviesService.deleteMovie(movieDTOOptional.get().getMovieId());
        return ResponseEntity.ok().build();
    }

}

