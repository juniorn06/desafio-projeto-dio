package one.digitalinovation.controllers;

import one.digitalinovation.entities.Cliente;
import one.digitalinovation.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Cliente> findByNome(@PathVariable String nome){
        return ResponseEntity.ok(clienteService.findByNome(nome));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    @PostMapping
    public ResponseEntity<Cliente> insertCliente(@RequestBody Cliente cliente){
        Cliente novoCliente = clienteService.insertCliente(cliente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(novoCliente.getId()).toUri();

        return ResponseEntity.created(uri).body(novoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteAtualizado = clienteService.updateCliente(cliente, id);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
         clienteService.deleteCliente(id);
         return ResponseEntity.noContent().build();
    }
}
