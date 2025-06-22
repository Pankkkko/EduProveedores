package com.edutech.EduProveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.EduProveedores.model.Proveedor;

public interface ProveedorRepository extends JpaRepository <Proveedor, Long> {
    

}

