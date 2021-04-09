package com.example.graphqldemo.mutator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphqldemo.entity.Dog;
import com.example.graphqldemo.exception.BreedNotFoundException;
import com.example.graphqldemo.exception.DogNotFoundException;
import com.example.graphqldemo.repository.DogRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    private DogRepository dogRepository;

    public Mutation(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Dog newDog(String name, String breed, String origin){
        Dog dog = new Dog(name,breed,origin);
        dogRepository.save(dog);
        return dog;
    }

    public boolean deleteDogBreed(String breed){
        boolean deleted = false;
        Iterable<Dog> allDogs = dogRepository.findAll();
        for (Dog d : allDogs) {
            if (d.getBreed().equals(breed)){
                dogRepository.delete(d);
                deleted = true;
            }
        }
        if (!deleted){
            throw new BreedNotFoundException("Breed Not Found", breed);
        }
        return deleted;
    }

    public Dog updateDogName(String newName, Long id){
        Optional<Dog> optionalDog = dogRepository.findById(id);

        if (optionalDog.isPresent()){
            Dog dog = optionalDog.get();
            dog.setName(newName);
            dogRepository.save(dog);
            return dog;
        } else {
            throw new DogNotFoundException("Dog Not Found", id);
        }
    }
}
