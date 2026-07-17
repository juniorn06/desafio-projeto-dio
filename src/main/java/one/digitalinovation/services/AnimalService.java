package one.digitalinovation.services;

import one.digitalinovation.entities.Animal;
import one.digitalinovation.entities.Cliente;
import one.digitalinovation.exceptions.ResourceNotFoundException;
import one.digitalinovation.repositories.AnimalRepository;
import one.digitalinovation.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Animal> findAll(){
        return animalRepository.findAll();
    }

    public Animal findById(Long id){
        return animalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));

    }

    public Animal findByNome(String nome){
        return animalRepository.findAnimalByNomeIgnoreCase(nome).orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));
    }

    public Animal insertAnimal(Animal animal){
        if (animal.getDono() != null && animal.getDono().getId() != null){
            Cliente dono = clienteRepository.findById(animal.getDono().getId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
            animal.setDono(dono);
            dono.getAnimais().add(animal);
        }
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Animal animal, Long id){
        Animal animalAtualizado = animalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));

        animalAtualizado.setNome(animal.getNome());
        animalAtualizado.setRaca(animal.getRaca());
        animalAtualizado.setDono(animal.getDono());

        return animalRepository.save(animalAtualizado);
    }

    public void deleteAnimal(Long id){
        animalRepository.deleteById(id);
    }
}
