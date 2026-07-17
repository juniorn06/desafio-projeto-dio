package one.digitalinovation.services;

import one.digitalinovation.entities.Cliente;
import one.digitalinovation.exceptions.ResourceNotFoundException;
import one.digitalinovation.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id){
        return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
    }

    public Cliente findByNome(String nome){
        return clienteRepository.findClienteByNomeIgnoreCase(nome).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
    }

    public Cliente insertCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Cliente cliente, Long id){
        Cliente clienteAtualizado = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado!"));

        clienteAtualizado.setNome(cliente.getNome());
        clienteAtualizado.setTelefone(cliente.getTelefone());
        clienteAtualizado.setEndereco(cliente.getEndereco());
        clienteAtualizado.setCidade(cliente.getCidade());
        clienteAtualizado.setCep(cliente.getCep());

        return clienteRepository.save(clienteAtualizado);
    }

    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }
}
