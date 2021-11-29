package com.moviedb.repositories;

import com.moviedb.entities.MovieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieDatabaseRepository extends CrudRepository<MovieEntity, Integer> {
    Optional<List<MovieEntity>> findByActorName(String actor);

    Optional<List<MovieEntity>> findByDirectorName(String directorName);


}
