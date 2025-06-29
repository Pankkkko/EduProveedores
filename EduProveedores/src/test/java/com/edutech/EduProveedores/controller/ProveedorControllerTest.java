package com.edutech.EduProveedores.controller;

import com.edutech.EduProveedores.service.ProveedorService;
import com.edutech.EduProveedores.model.Proveedor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProveedorController.class)
class ProveedorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProveedorService proveedorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerProveedores() throws Exception {
        Proveedor proveedor1 = new Proveedor(1L, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Proveedor proveedor2 = new Proveedor(2L, "CienciasFascinantes", "Ciencias", "cfascinantes@contacto.cl");
    
        Mockito.when(proveedorService.listarProveedores()).thenReturn(Arrays.asList(proveedor1, proveedor2));
        mockMvc.perform(get("/api/v0/proveedores"))
                //.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("MateSimples"))
                .andExpect(jsonPath("$[1].categoria").value("Ciencias"));
    }


    @Test
    void testCrearProveedor() throws Exception{
        Proveedor nuevo = new Proveedor(null, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Proveedor guardado = new Proveedor(1L, "MateSimples", "Matematicas", "msimples@contacto.cl");
    
        Mockito.when(proveedorService.crearProveedor(any(Proveedor.class)))
                .thenReturn(guardado);

        mockMvc.perform(post("/api/v0/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("MateSimples"));
    
    }

    @Test
    void testObtenerXidNoExistente() throws Exception{
        Mockito.when(proveedorService.buscarxId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v0/proveedores/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarProveedor() throws Exception {
        Proveedor proveedorExistente = new Proveedor(1L, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Proveedor proveedorActualizado = new Proveedor(1L, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Mockito.when(proveedorService.buscarxId(1L)).thenReturn(Optional.of(proveedorExistente));
        Mockito.when(proveedorService.actualizarProveedor(eq(1L), any(Proveedor.class)))
                .thenReturn(proveedorActualizado);
        mockMvc.perform(put("/api/v0/proveedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedorActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("MateSimples"));
        
                Mockito.verify(proveedorService).actualizarProveedor(eq(1L), any(Proveedor.class));   
    }

    @Test
    void testEliminarProveedor() throws Exception {
        Long idProveedor = 1L;
        Mockito.doNothing().when(proveedorService).eliminarProveedor(idProveedor);
        
        mockMvc.perform(delete("/api/v0/proveedores/{id}", idProveedor))
                .andExpect(status().isNoContent()); // error 204 No Content

        Mockito.verify(proveedorService).eliminarProveedor(idProveedor);
    }

    @Test
    void testActualizarProveedorNoExistente() throws Exception {
        Long idProveedor = 999L;
        Proveedor proveedorActualizado = new Proveedor(idProveedor, "MateSimples", "Matematicas", "msimples@contacto.cl");
        // lanza excepcion cuando no encuentra el prov
        Mockito.when(proveedorService.actualizarProveedor(Mockito.eq(idProveedor), Mockito.any(Proveedor.class)))
                .thenThrow(new RuntimeException("Proveedor no encontrado"));

        mockMvc.perform(put("/api/v0/proveedores/{id}", idProveedor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedorActualizado)))
                .andExpect(status().isNotFound());
    }
}