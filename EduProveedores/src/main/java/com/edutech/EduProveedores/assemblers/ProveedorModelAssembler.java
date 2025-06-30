package com.edutech.EduProveedores.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.EduProveedores.controller.ProveedorControllerV2;
import com.edutech.EduProveedores.model.Proveedor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<Proveedor, EntityModel<Proveedor>> {

    @Override
    public EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
                linkTo(methodOn(ProveedorControllerV2.class).obtenerProveedorPorId(proveedor.getId())).withSelfRel(),
                linkTo(methodOn(ProveedorControllerV2.class).obtenerTodosLosProveedores()).withRel("proveedores"));
    }
}