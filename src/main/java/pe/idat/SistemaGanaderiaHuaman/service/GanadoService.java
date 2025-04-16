package pe.idat.SistemaGanaderiaHuaman.service;

import pe.idat.SistemaGanaderiaHuaman.repository.CorralRepository;
import pe.idat.SistemaGanaderiaHuaman.repository.EstabloRepository;
import pe.idat.SistemaGanaderiaHuaman.repository.GanadoRepository;
import pe.idat.SistemaGanaderiaHuaman.model.Corral;
import pe.idat.SistemaGanaderiaHuaman.model.Establo;
import pe.idat.SistemaGanaderiaHuaman.model.Ganado;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
=======
>>>>>>> 7a495c2e883807a19748493f4cb95d60c23de761
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class GanadoService {
    private final GanadoRepository ganadoRepository;
    private final EstabloRepository establoRepository;
    private final CorralRepository corralRepository;
    private static final Logger logger = Logger.getLogger(GanadoService.class.getName());

    public GanadoService(GanadoRepository ganadoRepository, EstabloRepository establoRepository, CorralRepository corralRepository) {
        this.ganadoRepository = ganadoRepository;
        this.establoRepository = establoRepository;
        this.corralRepository = corralRepository;
    }

    // ✅ Obtener ganado por corral
    public List<Ganado> getGanadoByCorral(Long corralId) {
        return ganadoRepository.findByCorralId(corralId);
    }

    // ✅ Obtener todos los ganados
    public List<Ganado> getGanados() {
        return ganadoRepository.findAll();
    }

    // ✅ Obtener ganado por código único
    public Optional<Ganado> getGanadoByCodigo(String codigoUnico) {
        return ganadoRepository.findByCodigoUnico(codigoUnico);
    }

    // ✅ Crear ganado (asociado a un corral)
    @Transactional
    public Ganado createGanado(Ganado ganado) {
        if (ganadoRepository.findByCodigoUnico(ganado.getCodigoUnico()).isPresent()) {
            throw new IllegalArgumentException("El código único ya está en uso.");
        }

        // Obtener el ID del corral desde el objeto Ganado
        Long corralId = ganado.getCorral().getId();
        Optional<Corral> corral = corralRepository.findById(corralId);

        if (corral.isEmpty()) {
            throw new IllegalArgumentException("El corral seleccionado no existe.");
        }

        // Validar que el corral pertenece a un establo existente
        Establo establo = corral.get().getEstablo();
        if (establo == null) {
            throw new IllegalArgumentException("El corral no está asociado a un establo válido.");
        }

        // Asignar el corral al ganado y guardarlo
        ganado.setCorral(corral.get());
        return ganadoRepository.save(ganado);
    }

    // ✅ Actualizar datos de un ganado
    public Optional<Ganado> updateGanado(Long ganadoId, Ganado newGanado) {
        return ganadoRepository.findById(ganadoId).map(ganado -> {
            ganado.setTiempo(newGanado.getTiempo());
            ganado.setRaza(newGanado.getRaza());
            ganado.setPeso(newGanado.getPeso());
            return ganadoRepository.save(ganado);
        });
    }

    // ✅ Obtener corral por ID
    public Optional<Corral> getCorralById(Long corralId) {
        return corralRepository.findById(corralId);
    }

    // ✅ Obtener ganado cerca del límite de 90 días
    public List<Ganado> getGanadoOrdenadoPorTiempo() {
        return ganadoRepository.findAll()
                .stream()
                .peek(ganado -> {
                    if (ganado.getCorral() != null) {
                        ganado.getCorral().getNombre(); // 🔥 Forzamos la carga
                    }
                })
                .sorted((g1, g2) -> Integer.compare(g2.getTiempo(), g1.getTiempo())) // Orden descendente
                .toList();
    }



    // ✅ Eliminar un ganado
    public boolean deleteGanado(Long ganadoId) {
        if (ganadoRepository.existsById(ganadoId)) {
            ganadoRepository.deleteById(ganadoId);
            return true;
        }
        return false;
    }

    // ✅ Método automático para actualizar el tiempo del ganado
<<<<<<< HEAD
    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // Todos los días a medianoche
    public void actualizarTiempoGanado() {
        LocalDate hoy = LocalDate.now();
        List<Ganado> ganadoList = ganadoRepository.findAll();

        ganadoList.forEach(ganado -> {
            // Convertir java.util.Date a java.time.LocalDate
            LocalDate fechaRegistro = ganado.getFechaRegistro().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            long dias = ChronoUnit.DAYS.between(fechaRegistro, hoy);
            ganado.setTiempo((int) dias);

            if (dias >= 85 && dias < 90) {
                logger.warning(String.format("⚠ ALERTA: El ganado con código %s está en %d días y se acerca a 90.",
                        ganado.getCodigoUnico(), dias));
            }
        });

        ganadoRepository.saveAll(ganadoList);
    }


=======
    @Scheduled(cron = "0 0 0 * * *") // Se ejecuta a medianoche
    public void actualizarTiempoGanado() {
        List<Ganado> ganadoList = ganadoRepository.findAll();

        for (Ganado ganado : ganadoList) {
            if (ganado.getTiempo() < 90) {
                ganado.setTiempo(ganado.getTiempo() + 1);

                if (ganado.getTiempo() >= 85) {
                    logger.warning("⚠ ALERTA: El ganado con código " + ganado.getCodigoUnico() +
                            " está en " + ganado.getTiempo() + " días y se acerca a 90.");
                }
            }
        }

        ganadoRepository.saveAll(ganadoList);
    }
>>>>>>> 7a495c2e883807a19748493f4cb95d60c23de761
}