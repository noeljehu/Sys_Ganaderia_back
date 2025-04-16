package pe.idat.SistemaGanaderiaHuaman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.service.ProveedorService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*") // Permite peticiones desde Angular u otro cliente
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // ✅ Registrar nuevo proveedor
    @PostMapping("/register")
    public ResponseEntity<Proveedor> registrarProveedor(@Valid @RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevo = proveedorService.save(proveedor);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error en consola
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Listar todos los proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.findAll();
        return ResponseEntity.ok(proveedores);
    }

    // ✅ Buscar proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.findById(id);
        return proveedor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Buscar proveedor por empresa
    @GetMapping("/buscar")
    public ResponseEntity<Proveedor> buscarPorEmpresa(@RequestParam String empresa) {
        Optional<Proveedor> proveedor = proveedorService.findByEmpresa(empresa);
        return proveedor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Eliminar proveedor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        if (proveedorService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody Proveedor proveedorActualizado) {
        Optional<Proveedor> existente = proveedorService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Proveedor proveedor = existente.get();
        proveedor.setRuc(proveedorActualizado.getRuc());
        proveedor.setEmpresa(proveedorActualizado.getEmpresa());
        proveedor.setRepresentante(proveedorActualizado.getRepresentante());
        proveedor.setTelefono(proveedorActualizado.getTelefono());
        proveedor.setCorreo(proveedorActualizado.getCorreo());
        proveedor.setEstado(proveedorActualizado.getEstado());

        Proveedor actualizado = proveedorService.save(proveedor);
        return ResponseEntity.ok(actualizado);
    }
}
