package com.edutech.EduProveedores.service;

import com.edutech.EduProveedores.model.Proveedor;
import com.edutech.EduProveedores.repository.ProveedorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ProveedorServiceTest {
    
    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGuardarProveedor() {
        Proveedor proveedor = new Proveedor(null, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Proveedor proveedorGuardado = new Proveedor(1L, "MateSimples", "Matematicas", "msimples@contacto.cl");
        when(proveedorRepository.save(proveedor)).thenReturn(proveedorGuardado);

        Proveedor resultado = proveedorService.crearProveedor(proveedor);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    public void testListarProveedores() {
        Proveedor proveedor1 = new Proveedor(1L, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Proveedor proveedor2 = new Proveedor(2L, "CienciasFascinantes", "Ciencias", "cfascinantes@contacto.cl");
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor1, proveedor2));

        List<Proveedor> proveedores = proveedorService.listarProveedores();
        assertThat(proveedores).hasSize(2).contains(proveedor1, proveedor2);
        verify(proveedorRepository).findAll();
    }

    @Test
    public void testEliminarProveedor(){
        Long idProveedor = 1L;
        doNothing().when(proveedorRepository).deleteById(idProveedor);
        proveedorService.eliminarProveedor(idProveedor);
        verify(proveedorRepository).deleteById(idProveedor);

    }

    @Test
    public void testObtenerProveedorPorId() {
        Long idProveedor = 1L;
        Proveedor proveedorSimulado = new Proveedor(idProveedor, "MateSimples", "Matematicas", "msimples@contacto.cl");
        when(proveedorRepository.findById(idProveedor))
            .thenReturn(Optional.of(proveedorSimulado));
            
        Optional<Proveedor> resultado = proveedorService.buscarxId(idProveedor);

        assertThat(resultado)
            .isPresent()
            .hasValueSatisfying(proveedor -> {
                assertThat(proveedor.getId()).isEqualTo(idProveedor);
                assertThat(proveedor.getNombre()).isEqualTo("MateSimples");
                assertThat(proveedor.getCategoria()).isEqualTo("Matematicas");
                assertThat(proveedor.getContacto()).isEqualTo("msimples@contacto.cl");
            });

        verify(proveedorRepository).findById(idProveedor);
    }

    @Test
    public void testActualizarProveedor() {
        Long idProveedor = 1L;
        Proveedor proveedorExistente = new Proveedor(idProveedor, "MateSimples", "Matematicas", "msimples@contacto.cl");
        Proveedor proveedorActualizado = new Proveedor(idProveedor, "MateSimples", "Matematicas", "msimples@contacto.cl");

        when(proveedorRepository.findById(idProveedor)).thenReturn(Optional.of(proveedorExistente));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorActualizado);
        Proveedor resultado = proveedorService.actualizarProveedor(idProveedor, proveedorActualizado);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(idProveedor);
        assertThat(resultado.getNombre()).isEqualTo("MateSimples");
        assertThat(resultado.getCategoria()).isEqualTo("Matematicas");
        assertThat(resultado.getContacto()).isEqualTo("msimples@contacto.cl");
        verify(proveedorRepository).findById(idProveedor);
        verify(proveedorRepository).save(proveedorActualizado);
    }

}
