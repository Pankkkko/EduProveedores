package com.edutech.EduProveedores.controller;

import com.edutech.EduProveedores.model.Proveedor;
import com.edutech.EduProveedores.service.ProveedorService;
import com.edutech.EduProveedores.assemblers.ProveedorModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/proveedores")
public class ProveedorControllerV2 {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorModelAssembler assembler;

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Proveedor>> crearProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.crearProveedor(proveedor);
        return ResponseEntity
                .created(linkTo(methodOn(ProveedorControllerV2.class).obtenerProveedorPorId(nuevoProveedor.getId())).toUri())
                .body(assembler.toModel(nuevoProveedor));
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Proveedor>> obtenerTodosLosProveedores() {
        List<EntityModel<Proveedor>> proveedores = proveedorService.listarProveedores().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(proveedores,
                linkTo(methodOn(ProveedorControllerV2.class).obtenerTodosLosProveedores()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.buscarxId(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return assembler.toModel(proveedor);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Proveedor>> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor updatedProveedor) {
        Proveedor proveedorActualizado = proveedorService.actualizarProveedor(id, updatedProveedor);
        return ResponseEntity.ok(assembler.toModel(proveedorActualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}