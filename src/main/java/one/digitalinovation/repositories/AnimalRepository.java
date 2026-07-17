package one.digitalinovation.repositories;

import one.digitalinovation.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findAnimalByNomeIgnoreCase(String nome);
}
