package com.edutech.EduProveedores.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.EduProveedores.model.Proveedor; 
import com.edutech.EduProveedores.repository.ProveedorRepository;

@Service
public class ProveedorService{
    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Optional<Proveedor> buscarxId(Long id) {
        return proveedorRepository.findById(id);
    }

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }
    
    public Proveedor actualizarProveedor(Long id, Proveedor proveedor) {
        Proveedor existente = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
        existente.setNombre(proveedor.getNombre());
        existente.setCategoria(proveedor.getCategoria());
        existente.setContacto(proveedor.getContacto());
        return proveedorRepository.save(existente);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }
}