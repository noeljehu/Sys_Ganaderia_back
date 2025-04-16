package pe.idat.SistemaGanaderiaHuaman.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "codigo_cliente")
    private String  CodigoAlimento;

    private String nombre;
    private String apellido;

    @Column(name = "tipo_documento", nullable = false)


    @Column(name = "numero_documento", unique = true, nullable = false)
    private String numeroDocumento;

    private String telefono;
    private String correo;
    private String direccion;

    private LocalDateTime fechaRegistro;

}
