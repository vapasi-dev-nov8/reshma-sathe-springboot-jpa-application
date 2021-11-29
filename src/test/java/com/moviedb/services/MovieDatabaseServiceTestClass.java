package com.moviedb.services;

import com.moviedb.dtos.MovieDTO;
import com.moviedb.entities.MovieEntity;
import com.moviedb.repositories.MovieDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieDatabaseServiceTestClass {

        MovieDatabaseRepository moviesDatabaseRepository;
        MovieDatabaseService moviesDatabaseService;

        @BeforeEach
        void setUp() {
            moviesDatabaseRepository = mock(MovieDatabaseRepository.class);
            moviesDatabaseService = new MovieDatabaseService(moviesDatabaseRepository);
        }

        @Test
        public void getAllMoviesAddedToList() {
            List<MovieEntity> allMovies = new ArrayList<>();
            allMovies.add(new MovieEntity(1, "pirates of caribbean", "Johny Depp", "Gore Verbinsky"));
            allMovies.add(new MovieEntity(2, "The Fight club", "Brad pitt", "David Fincher"));
            allMovies.add(new MovieEntity(3, "Interstellar", "Mathew McConaughey", "Christopher Nolan"));
            allMovies.add(new MovieEntity(4, "pulp fiction", "Samuel L Johnson", "Quintine torentino"));
            when(moviesDatabaseRepository.findAll()).thenReturn(allMovies);

            List<MovieDTO> actualMovies = moviesDatabaseService.getAllMovies();

            List<MovieDTO> expectedMovies = Arrays.asList(
                    new MovieDTO(1, "pirates of caribbean", "Johny Depp", "Gore Verbinsky"),
                    new MovieDTO(2, "The Fight club", "Brad pitt", "David Fincher"),
                    new MovieDTO(3, "Interstellar", "Mathew McConaughey", "Christopher Nolan"),
                    new MovieDTO(4, "pulp fiction", "Samuel L Johnson", "Quintine torentino"));

            verify(moviesDatabaseRepository, times(1)).findAll();
            assertEquals(expectedMovies, actualMovies);
        }

        @Test
        public void getEmptyMoviesWhenNoMoviesAddedToList() {
            List<MovieEntity> allMovies = new ArrayList<>();
            when(moviesDatabaseRepository.findAll()).thenReturn(allMovies);

            moviesDatabaseService.getAllMovies();

            verify(moviesDatabaseRepository, times(1)).findAll();
            assertEquals(allMovies.size(), 0);
        }

        @Test
        void shouldGetMovieById() {
            MovieEntity movieEntity = new MovieEntity(6, "test-movie", "test-actor", "test-director");
            when(moviesDatabaseRepository.findById(6)).thenReturn(Optional.of(movieEntity));

            Optional<MovieDTO> actualMovie = moviesDatabaseService.getMovieByID(6);
            MovieDTO expectedMovie = new MovieDTO(6, "test-movie", "test-actor", "test-director");

            assertEquals(expectedMovie, actualMovie.get());
            verify(moviesDatabaseRepository, times(1)).findById(6);
        }


        @Test
        void shouldCreateMovie() {

            MovieDTO movieDto = new MovieDTO("test-movie", "test-actor", "test-director");
            MovieEntity savedMovieEntity = new MovieEntity(6, "test-movie", "test-actor", "test-director");
            when(moviesDatabaseRepository.save(any())).thenReturn(savedMovieEntity);

            moviesDatabaseRepository.save(savedMovieEntity);

            ArgumentCaptor<MovieEntity> movieEntityArgumentCaptor = ArgumentCaptor.forClass(MovieEntity.class);
            verify(moviesDatabaseRepository).save(movieEntityArgumentCaptor.capture());
            MovieEntity expectedMovie = new MovieEntity(6, "test-movie", "test-actor", "test-director");
            MovieEntity actualMovie = movieEntityArgumentCaptor.getValue();
            assertEquals(expectedMovie, actualMovie);
        }

    @Test
    void shouldUpdateMovie() {

        MovieDTO movieDto = new MovieDTO(1,"abc","def","xyz");
        MovieEntity updatedMovieEntity = new MovieEntity(1, "sss", "lll", "ddd");
        when(moviesDatabaseRepository.save(any())).thenReturn(updatedMovieEntity);

        moviesDatabaseRepository.save(updatedMovieEntity);

        ArgumentCaptor<MovieEntity> movieEntityArgumentCaptor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(moviesDatabaseRepository).save(movieEntityArgumentCaptor.capture());
        MovieEntity expectedMovie = new MovieEntity(1, "sss", "lll", "ddd");
        MovieEntity actualMovie = movieEntityArgumentCaptor.getValue();
        assertEquals(expectedMovie, actualMovie);
    }


    @Test
     public void getAllMoviesByActorName() {
        List<MovieEntity> allMovies = new ArrayList<>();
        allMovies.add(new MovieEntity(1, "The Hobbit", "Martin Freeman", "Peter Jackson"));
        allMovies.add(new MovieEntity(2, "The Hobbit Desolation of Smaug", "Martin Freeman", "Peter Jackson"));
        allMovies.add(new MovieEntity(3, "The Hobbit Battle of the Five Armies", "Martin Freeman", "Peter Jackson"));
        allMovies.add(new MovieEntity(4, "The Eichmann Show", "Martin Freeman", "Paul Andrew Williams"));
        String actor = "Martin Freeman";
        when(moviesDatabaseRepository.findByActorName(actor)).thenReturn(Optional.of(allMovies));

        Optional<List<MovieDTO>> actualMovies = moviesDatabaseService.getMovieByActorName(actor);

        List<MovieDTO> expectedMovies = Arrays.asList(
                new MovieDTO(1, "The Hobbit", "Martin Freeman", "Peter Jackson"),
                new MovieDTO(2, "The Hobbit Desolation of Smaug", "Martin Freeman", "Peter Jackson"),
                new MovieDTO(3, "The Hobbit Battle of the Five Armies", "Martin Freeman", "Peter Jackson"),
                new MovieDTO(4, "The Eichmann Show", "Martin Freeman", "Paul Andrew Williams"));

        verify(moviesDatabaseRepository, times(1)).findByActorName(actor);
        assertEquals(expectedMovies, actualMovies.get());
    }


    @Test
    public void getAllMoviesByDirectorName() {
        List<MovieEntity> allMovies = new ArrayList<>();
        allMovies.add(new MovieEntity(1, "The Hobbit", "Martin Freeman", "Peter Jackson"));
        allMovies.add(new MovieEntity(2, "The Hobbit Desolation of Smaug", "Martin Freeman", "Peter Jackson"));
        allMovies.add(new MovieEntity(3, "The Hobbit Battle of the Five Armies", "Martin Freeman", "Peter Jackson"));
        allMovies.add(new MovieEntity(4, "The Eichmann Show", "Martin Freeman", "Paul Andrew Williams"));
        String actor = "Martin Freeman";
        when(moviesDatabaseRepository.findByActorName(actor)).thenReturn(Optional.of(allMovies));

        Optional<List<MovieDTO>> actualMovies = moviesDatabaseService.getMovieByActorName(actor);

        List<MovieDTO> expectedMovies = Arrays.asList(
                new MovieDTO(1, "The Hobbit", "Martin Freeman", "Peter Jackson"),
                new MovieDTO(2, "The Hobbit Desolation of Smaug", "Martin Freeman", "Peter Jackson"),
                new MovieDTO(3, "The Hobbit Battle of the Five Armies", "Martin Freeman", "Peter Jackson"),
                new MovieDTO(4, "The Eichmann Show", "Martin Freeman", "Paul Andrew Williams"));

        verify(moviesDatabaseRepository, times(1)).findByActorName(actor);
        assertEquals(expectedMovies, actualMovies.get());
    }

    @Test
    void shouldDeleteMovie() {
        MovieDTO movieDto = new MovieDTO(1,"abc","def","xyz");
        assertDoesNotThrow(() ->moviesDatabaseRepository.deleteById(movieDto.getMovieId()));
    }

}
