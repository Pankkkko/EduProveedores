package com.edutech.EduProveedores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.EduProveedores.model.Proveedor;
import com.edutech.EduProveedores.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v0/proveedores")
@Tag(name = "Proveedores", description = "Operaciones CRUD relacionadas con los proveedores educativos")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    @Operation(summary = "Crear un proveedor", description = "Permite crear un nuevo proveedor educativo")
    public Proveedor crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.crearProveedor(proveedor);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Obtiene la lista de todos los proveedores educativos")
    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorService.listarProveedores();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Obtiene un proveedor específico por su ID")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        return proveedorService.buscarxId(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proveedor por ID", description = "Permite actualizar los datos de un proveedor mediante su ID")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor updatedProveedor) {
        try{
            Proveedor proveedorActualizado = proveedorService.actualizarProveedor(id, updatedProveedor);
            return ResponseEntity.ok(proveedorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proveedor por ID", description = "Elimina un proveedor a partir de un ID específico")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}