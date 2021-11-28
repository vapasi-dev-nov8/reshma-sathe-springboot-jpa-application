package com.moviedb.services;

import com.moviedb.entities.MovieEntity;
import com.moviedb.repositories.MovieDatabaseRepository;
import com.moviedb.dtos.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<MovieDTO> getMovie(Integer id) {
        Optional<MovieEntity> movieEntity = moviesDBRepository.findById(id);
        return movieEntity.map(MovieDTO::dtoFrom);
    }

    public List<MovieDTO> saveMovies(List<MovieDTO> movies) {
        List<MovieDTO> createdMovieList = new ArrayList<>();
        for (MovieDTO movieDTO: movies) {
            MovieEntity movieEntity = MovieEntity.entityFrom(movieDTO);
            MovieEntity savedMovieEntity = moviesDBRepository.save(movieEntity);
            createdMovieList.add(MovieDTO.dtoFrom(savedMovieEntity));
        }
        return createdMovieList;
    }
}
