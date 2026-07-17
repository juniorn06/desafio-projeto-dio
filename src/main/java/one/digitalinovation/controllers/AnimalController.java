package one.digitalinovation.controllers;

import one.digitalinovation.entities.Animal;
import one.digitalinovation.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/{id}")
    public ResponseEntity<Animal> findById(@PathVariable Long id){
        return ResponseEntity.ok(animalService.findById(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Animal> findByNome(@PathVariable String nome){
        return ResponseEntity.ok(animalService.findByNome(nome));
    }

    @GetMapping
    public ResponseEntity<List<Animal>> findAll(){
        return ResponseEntity.ok(animalService.findAll());
    }

    @PostMapping
    public ResponseEntity<Animal> insertAnimal(@RequestBody Animal animal){
        Animal novoAnimal = animalService.insertAnimal(animal);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(novoAnimal.getId()).toUri();

        return ResponseEntity.created(uri).body(novoAnimal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@RequestBody Animal animal, @PathVariable Long id){
        Animal animalAtualizado = animalService.updateAnimal(animal, id);
        return ResponseEntity.ok(animalAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(Long id){
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
