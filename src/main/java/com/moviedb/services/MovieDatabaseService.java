package com.moviedb.services;

import com.moviedb.entities.MovieEntity;
import com.moviedb.repositories.MovieDatabaseRepository;
import com.moviedb.dtos.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieDatabaseService {
    MovieDatabaseRepository moviesDBRepository;

    @Autowired
    public MovieDatabaseService(MovieDatabaseRepository moviesRepository) {
        this.moviesDBRepository = moviesRepository;
    }

    public List<MovieDTO> getAllMovies() {
        List<MovieDTO> moviesDTOList = new ArrayList<>();

        for (MovieEntity movieEntity : moviesDBRepository.findAll()) {
            moviesDTOList.add(MovieDTO.dtoFrom(movieEntity));
        }
        return moviesDTOList;
    }

    public MovieDTO saveMovie(MovieDTO movieDto) {
        MovieEntity movieEntity = MovieEntity.entityFrom(movieDto);
        MovieEntity savedMovieEntity = moviesDBRepository.save(movieEntity);
        return MovieDTO.dtoFrom(savedMovieEntity);
    }

    public MovieDTO updateMovie(MovieDTO movieDto) {
        MovieEntity movieEntity = MovieEntity.updateEntityFrom(movieDto);
        MovieEntity savedMovieEntity = moviesDBRepository.save(movieEntity);
        return MovieDTO.dtoFrom(savedMovieEntity);
    }
    public Optional<MovieDTO> getMovieByID(Integer id) {
        Optional<MovieEntity> movieEntity = moviesDBRepository.findById(id);
        return movieEntity.map(MovieDTO::dtoFrom);
    }

    public List<MovieDTO> createMovies(List<MovieDTO> movies) {
        List<MovieDTO> createdMovieList = new ArrayList<>();
        for (MovieDTO movieDTO : movies) {
            MovieEntity movieEntity = MovieEntity.entityFrom(movieDTO);
            MovieEntity savedMovieEntity = moviesDBRepository.save(movieEntity);
            createdMovieList.add(MovieDTO.dtoFrom(savedMovieEntity));
        }
        return createdMovieList;
    }

    public Optional<List<MovieDTO>> getMovieByActorName(String actor) {
        Optional<List<MovieEntity>> movieEntityList = moviesDBRepository.findByActorName(actor);
        return Optional.of(movieEntityList.get().stream().map(MovieDTO::dtoFrom).collect(Collectors.toList()));
    }


    public Optional<List<MovieDTO>> getMovieByDirectorName(String director) {
        Optional<List<MovieEntity>> movieEntityList = moviesDBRepository.findByDirectorName(director);
        return Optional.of(movieEntityList.get().stream().map(MovieDTO::dtoFrom).collect(Collectors.toList()));
    }

    public void deleteMovie(Integer movieID) {
        moviesDBRepository.deleteById(movieID);
    }
}

