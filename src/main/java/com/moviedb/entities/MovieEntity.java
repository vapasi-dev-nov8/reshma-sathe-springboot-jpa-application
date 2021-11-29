package com.moviedb.entities;

import com.moviedb.dtos.MovieDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    private String movieName;
    private String actorName;
    private String directorName;


    public MovieEntity() {
        super();
    }


    public MovieEntity(Integer id, String movieName, String actorName, String directorName) {
        this.movieId = id;
        this.movieName = movieName;
        this.actorName = actorName;
        this.directorName = directorName;
    }

    public static MovieEntity entityFrom(MovieDTO movieDto) {
        return new MovieEntity(null,movieDto.getMovieName(), movieDto.getActorName(), movieDto.getDirectorName());
    }

    public static MovieEntity updateEntityFrom(MovieDTO movieDto) {
        return new MovieEntity(movieDto.getMovieId(), movieDto.getMovieName(), movieDto.getActorName(), movieDto.getDirectorName());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(movieName, that.movieName) && Objects.equals(actorName, that.actorName) && Objects.equals(directorName, that.directorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieName, actorName, directorName);
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", actorName='" + actorName + '\'' +
                ", directorName='" + directorName + '\'' +
                '}';
    }
}
