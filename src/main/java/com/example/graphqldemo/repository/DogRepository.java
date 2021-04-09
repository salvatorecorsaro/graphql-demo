package com.example.graphqldemo.repository;

import com.example.graphqldemo.entity.Dog;
import org.springframework.data.repository.CrudRepository;

public interface DogRepository extends CrudRepository<Dog, Long> {
}
