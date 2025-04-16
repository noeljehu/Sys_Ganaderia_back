package pe.idat.SistemaGanaderiaHuaman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.repository.ProveedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Listar todos los proveedores
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    // Buscar proveedor por ID
    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }

    // Buscar proveedor por nombre de empresa
    public Optional<Proveedor> findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }

    // Guardar o actualizar proveedor
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Eliminar proveedor por ID
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }
}
