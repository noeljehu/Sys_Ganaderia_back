package pe.idat.SistemaGanaderiaHuaman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.SistemaGanaderiaHuaman.model.Cliente;
import pe.idat.SistemaGanaderiaHuaman.service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteService.obtenerClientePorId(id);
        return clienteOpt.map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Buscar un cliente por número de documento
    @GetMapping("/buscar/{numeroDocumento}")
    public ResponseEntity<Cliente> obtenerClientePorNumeroDocumento(@PathVariable String numeroDocumento) {
        Optional<Cliente> clienteOpt = clienteService.obtenerClientePorNumeroDocumento(numeroDocumento);
        return clienteOpt.map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
        // Verificar si ya existe un cliente con el mismo número de documento
        Optional<Cliente> clienteExistente = clienteService.obtenerClientePorNumeroDocumento(cliente.getNumeroDocumento());
        if (clienteExistente.isPresent()) {
            return new ResponseEntity<>("El número de documento ya está registrado", HttpStatus.BAD_REQUEST);
        }

        // Crear cliente
        Cliente clienteGuardado = clienteService.crearCliente(cliente);
        return new ResponseEntity<>(clienteGuardado, HttpStatus.CREATED);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        // Verificar si el cliente existe
        Optional<Cliente> clienteExistente = clienteService.obtenerClientePorId(id);
        if (clienteExistente.isEmpty()) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }

        // Actualizar cliente
        cliente.setIdCliente(id);
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        Optional<Cliente> clienteExistente = clienteService.obtenerClientePorId(id);
        if (clienteExistente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clienteService.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
