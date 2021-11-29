package com.moviedb.dtos;

import com.moviedb.entities.MovieEntity;

import java.util.Objects;

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

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(movieId, movieDTO.movieId) && Objects.equals(movieName, movieDTO.movieName) && Objects.equals(actorName, movieDTO.actorName) && Objects.equals(directorName, movieDTO.directorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieName, actorName, directorName);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", actorName='" + actorName + '\'' +
                ", directorName='" + directorName + '\'' +
                '}';
    }
}



