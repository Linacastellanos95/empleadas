package ar.com.ada.api.empleadas.controllers;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleadas.entities.*;
import ar.com.ada.api.empleadas.models.request.InfoEmpleadaNueva;
import ar.com.ada.api.empleadas.models.request.SueldoNuevoEmpleada;
import ar.com.ada.api.empleadas.models.response.GenericResponse;
import ar.com.ada.api.empleadas.services.CategoriaService;
import ar.com.ada.api.empleadas.services.EmpleadaService;

@RestController
public class EmpleadaController {

    @Autowired
    private EmpleadaService service;

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleada>> traerEmpleadas() {
        return ResponseEntity.ok(service.traerEmpleadas());
    }

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleada(@RequestBody InfoEmpleadaNueva empleadaInfo) {

    
        Empleada empleada = new Empleada();
        service.crearEmpleada(empleadaInfo);
        GenericResponse respuesta = new GenericResponse();
    
        respuesta.isOk = true;
        respuesta.id = empleada.getEmpleadaId();
        respuesta.message = "La empleada fue creada con éxito";
           return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleada> getEmpleadaPorId(@PathVariable Integer id) {
        Empleada empleada = service.buscarEmpleada(id);
        if (empleada == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleada);

    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleada(@PathVariable Integer id) {

        service.bajaEmpleadaporId(id);

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.message = "La empleada fue dada de baja con éxito";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/empleados/categorias/{catId}")
    public ResponseEntity<List<Empleada>> obtenerEmpleadasPorCategoria(@PathVariable Integer catId){
        List<Empleada> empleadas = service.traerEmpleadasPorCategoria(catId);
        return ResponseEntity.ok(empleadas);
    }
    
    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> modificarSueldo(@PathVariable Integer id, @RequestBody SueldoNuevoEmpleada sueldoNuevoInfo){
        
        //1) Buscar una empleada
        Empleada empleada = service.buscarEmpleada(id);
        //2) Setear su nuevo sueldo
        empleada.setSueldo(sueldoNuevoInfo.sueldoNuevo);
        //3) Guardar en la base de datos
        service.guardar(empleada);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "Sueldo actualizado";

        return ResponseEntity.ok(respuesta);
        
    }



}
