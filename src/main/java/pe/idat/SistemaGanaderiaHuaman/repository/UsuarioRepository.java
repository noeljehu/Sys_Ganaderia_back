package pe.idat.SistemaGanaderiaHuaman.repository;

import pe.idat.SistemaGanaderiaHuaman.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    // Método modificado para devolver una lista en lugar de una página
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}
