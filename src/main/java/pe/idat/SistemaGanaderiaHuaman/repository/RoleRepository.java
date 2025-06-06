package pe.idat.SistemaGanaderiaHuaman.repository;

import pe.idat.SistemaGanaderiaHuaman.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNombre(String nombre);
}