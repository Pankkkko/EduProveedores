package com.edutech.EduProveedores.service;

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

    public Optional<Proveedor> buscarxId(int id) {
        return proveedorRepository.findById(id);
    }
    
    public Optional<Proveedor> actualizarProveedor(int id, Proveedor updatedProveedor) {
        return proveedorRepository.findById(id).map(proveedor -> {
            proveedor.setNombre(updatedProveedor.getNombre());
            proveedor.setCategoria(updatedProveedor.getCategoria());
            proveedor.setContacto(updatedProveedor.getContacto());
            return proveedorRepository.save(proveedor);
        });

    }

    public boolean eliminarProveedor(int id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}