package pe.idat.SistemaGanaderiaHuaman.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "alimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimento")
    private int id;

    @Column(name = "codigo_alimento", unique = true)
    private String CodigoAlimento;

    private String nombre;

    private String tipo;

    private BigDecimal cantidad;

    private String unidadMedida; // Ej: "kg" o "tonelada"

    private BigDecimal precioPorUnidad;

    private BigDecimal precioTotal;

    private LocalDate fechaIngreso;

    private String observaciones;

    private String proveedor;

}