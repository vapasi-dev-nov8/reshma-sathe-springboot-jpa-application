package com.moviedb.dtos;

import com.moviedb.entities.MovieEntity;

public class MovieDTO {

    private Integer movieId;
    private String movieName;
    private String actorName;
    private String directorName;

    public MovieDTO() {
        super();
    }

    public MovieDTO(String movieName, String actorName, String directorName) {
        this.movieId = null;
        this.movieName = movieName;
        this.actorName = actorName;
        this.directorName = directorName;
    }

    public MovieDTO(Integer id, String movieName, String actorName, String directorName) {
        this.movieId = id;
        this.movieName = movieName;
        this.actorName = actorName;
        this.directorName = directorName;
    }


    public static MovieDTO dtoFrom(MovieEntity movieEntity) {
        return new MovieDTO(
                movieEntity.getMovieId(),
                movieEntity.getMovieName(),
                movieEntity.getActorName(),
                movieEntity.getDirectorName());
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getActorName() {
        return actorName;
    }

    public String getDirectorName() {
        return directorName;
    }
}



