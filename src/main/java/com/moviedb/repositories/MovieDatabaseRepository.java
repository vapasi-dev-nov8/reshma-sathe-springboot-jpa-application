package com.moviedb.repositories;

import com.moviedb.entities.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDatabaseRepository extends CrudRepository<MovieEntity, Integer> {
}
