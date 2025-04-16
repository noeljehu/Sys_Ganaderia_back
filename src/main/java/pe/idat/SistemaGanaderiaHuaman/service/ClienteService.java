package pe.idat.SistemaGanaderiaHuaman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.SistemaGanaderiaHuaman.model.Cliente;
import pe.idat.SistemaGanaderiaHuaman.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todos los clientes
    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }

    // Obtener un cliente por ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Buscar un cliente por número de documento
    public Optional<Cliente> obtenerClientePorNumeroDocumento(String numeroDocumento) {
        return clienteRepository.findByNumeroDocumento(numeroDocumento);
    }

    // Crear un nuevo cliente
    public Cliente crearCliente(Cliente cliente) {
        // Generar el Código Cliente automáticamente
        cliente.setCodigoCliente(generarCodigoCliente());

        // Guardamos el cliente en la base de datos
        return clienteRepository.save(cliente);
    }

    // Actualizar un cliente existente
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            cliente.setIdCliente(id);
            // Actualizar el cliente en la base de datos
            return clienteRepository.save(cliente);
        }
        return null;  // Si no se encuentra el cliente
    }

    // Eliminar un cliente por ID
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    // Método para generar el Código Cliente de manera automática y única
    private String generarCodigoCliente() {
        String prefijo = "CLI-";
        String codigoAleatorio;
        boolean codigoExistente;

        do {
            // Generamos un número aleatorio de 4 dígitos
            codigoAleatorio = String.format("%04d", (int) (Math.random() * 10000));

            // Concatenamos el prefijo con el número aleatorio
            String codigoCliente = prefijo + codigoAleatorio;

            // Verificamos si el código ya existe en la base de datos
            codigoExistente = clienteRepository.existsByCodigoCliente(codigoCliente);

        } while (codigoExistente); // Continuamos generando un nuevo código hasta que sea único

        return prefijo + codigoAleatorio;
    }
}
