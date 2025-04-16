package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.SistemaGanaderiaHuaman.model.Cliente;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado para buscar un cliente por su número de documento
    Optional<Cliente> findByNumeroDocumento(String numeroDocumento);

    boolean existsByCodigoCliente(String codigoCliente);
}
