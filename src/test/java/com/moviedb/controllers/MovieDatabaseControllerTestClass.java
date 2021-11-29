package com.moviedb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.dtos.MovieDTO;
import com.moviedb.services.MovieDatabaseService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieDatabaseController.class)
public class MovieDatabaseControllerTestClass {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private MovieDatabaseService movieDatabaseService;

    @Test
    void shouldExpectOKForAllMoviesReturned() throws Exception {
        mockMvc.perform(get("/api/v1/movies/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(movieDatabaseService, times(1)).getAllMovies();
    }

    @Test
    void shouldExpectNotFoundErrorWhenUrlIsWrong() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        verify(movieDatabaseService, times(0)).getAllMovies();
    }

    @Test
    void shouldReturn200ResponseOnGetByMovieID() throws Exception {
        when(movieDatabaseService.getMovieByID(1)).thenReturn(Optional.of(new MovieDTO(1, "test-movie", "test-actor", "test-director")));
        mockMvc.perform(get("/api/v1/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"test-movie\",\"actorName\":\"test-actor\",\"directorName\":\"test-director\"}"));

        verify(movieDatabaseService, times(1)).getMovieByID(1);
    }

    @Test
    void shouldReturn200ResponseOnSuccessfulCreationOfMovie() throws Exception {
        MovieDTO requestMovieDTO = new MovieDTO(null, "Doctor Strnge", "Benedict Cumberbacth", "Dominic Cooke");
        MovieDTO createdMovieDTO = new MovieDTO(1, "Doctor Strnge", "Benedict Cumberbacth", "Dominic Cooke");

        when(movieDatabaseService.saveMovie(any())).thenReturn(createdMovieDTO);

        mockMvc.perform(post("/api/v1/movies/")
                        .content(this.mapper.writeValueAsString(requestMovieDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.movieId", Matchers.is(createdMovieDTO.getMovieId())));
    }

    private List<MovieDTO> dataSetup() {
        return new ArrayList<MovieDTO>(Arrays.asList(
                new MovieDTO(null, "Doctor Strnge", "Benedict Cumberbacth", "Dominic Cooke"),
                new MovieDTO(null, "The Eichmann Show", "Martin Freeman", "Paul Andrew Williams"),
                new MovieDTO(null, "The Man from U.N.C.L.E", "Henry Cavill", "Guy Ritchie")));
    }

    private List<MovieDTO> createdData() {
        return new ArrayList<MovieDTO>(Arrays.asList(
                new MovieDTO(1, "Doctor Strnge", "Benedict Cumberbacth", "Dominic Cooke"),
                new MovieDTO(2, "The Eichmann Show", "Martin Freeman", "Paul Andrew Williams"),
                new MovieDTO(3, "The Man from U.N.C.L.E", "Henry Cavill", "Guy Ritchie")));
    }

    @Test
    void shouldReturn200ResponseOnSuccessfulBulkCreationOfMovies() throws Exception {
        List<MovieDTO> requestDTOs = dataSetup();
        List<MovieDTO> createdMovieDTOs = createdData();
        when(movieDatabaseService.createMovies(requestDTOs)).thenReturn(createdMovieDTOs);

        mockMvc.perform(post("/api/v1/movies/bulk/")
                        .content(this.mapper.writeValueAsString(requestDTOs))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].movieId", Matchers.is(createdMovieDTOs.get(0).getMovieId())));
    }


    @Test
    void shouldReturn202ResponseOnSuccessfulUpdateOfMovie() throws Exception {

        MovieDTO originalRecord = new MovieDTO(1, "Doctor Strnge", "Benedict Cumberbacth", "Dominic Cooke");
        MovieDTO updatedMovieDTO = new MovieDTO(1, "Doctor Strange", "Benedict Cumberbatch", "Dominic Cooke");

        when(movieDatabaseService.getMovieByID(1)).thenReturn(Optional.of(originalRecord));
        when(movieDatabaseService.updateMovie(originalRecord)).thenReturn(updatedMovieDTO);

        mockMvc.perform(put("/api/v1/movies/1")
                        .content(this.mapper.writeValueAsString(updatedMovieDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.movieName", Matchers.is("Doctor Strange")));
    }

    @Test
    public void shouldReturn200Response_when_DeleteMovie() throws Exception {
        MovieDTO recordToDelete = new MovieDTO(1, "Doctor Strnge", "Benedict Cumberbacth", "Dominic Cooke");

        when(movieDatabaseService.getMovieByID(1)).thenReturn(Optional.of(recordToDelete));

        mockMvc.perform(delete("/api/v1/movies/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn_NotFoundResponse_when_Movie_not_Found_DeleteMovie() throws Exception {
        MovieDTO recordToDelete = null;

        when(movieDatabaseService.getMovieByID(1)).thenReturn(Optional.ofNullable(recordToDelete));

        mockMvc.perform(delete("/api/v1/movies/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
