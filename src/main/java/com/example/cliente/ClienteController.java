
package com.example.cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:8081")


@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteRepository ClienteRepository;

    @GetMapping("/clientes")
    public ResponseEntity<Object> getClientees() {
        List<Cliente> Clientes = ClienteRepository.findAll();
        if (!Clientes.isEmpty()) {
            return new ResponseEntity<>(Clientes, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existem clientes cadastrados.");
        }
    }
   
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Object> getClienteById(@PathVariable Long id) {

        
        Optional<Cliente> ClienteOptional = ClienteRepository.findById(id);
        if (ClienteOptional.isPresent()) {
            return new ResponseEntity<>(ClienteOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado com o id: " + id);
        }
    }
    @PostMapping("/clientes")
    public Cliente createCliente(@Valid @RequestBody Cliente Cliente) {
        return ClienteRepository.save(Cliente);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable Long id, @RequestBody Cliente updatedCliente) {
        Optional<Cliente> ClienteOptional = ClienteRepository.findById(id);
    
        if (ClienteOptional.isPresent()) {
            Cliente c = ClienteOptional.get();
            c.setNome(updatedCliente.getNome()); 
            c.setEmail(updatedCliente.getEmail()); 
            ClienteRepository.save(c);
            return ResponseEntity.ok(c);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado com o id: " + id);
        }
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Object>  deleteCliente(@PathVariable Long id) {
        if (ClienteRepository.existsById(id)) {
            ClienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente excluído com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado com o ID: " + id);
        }
    }



}