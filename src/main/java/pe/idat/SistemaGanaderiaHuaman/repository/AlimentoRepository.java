package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.SistemaGanaderiaHuaman.model.Alimento;

import java.util.Optional;

public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {
    Optional<Alimento> findTopByOrderByIdDesc();

    Optional<Alimento> findByNombre(String nombre);
    // Puedes agregar consultas personalizadas si las necesitas
}
