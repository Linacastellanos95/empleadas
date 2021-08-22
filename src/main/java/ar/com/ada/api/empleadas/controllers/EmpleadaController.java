package ar.com.ada.api.empleadas.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleadas.entities.*;
import ar.com.ada.api.empleadas.entities.Empleada.EstadoEmpleadaEnum;
import ar.com.ada.api.empleadas.models.request.InfoEmpleadaNueva;
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

        GenericResponse respuesta = new GenericResponse();

        Empleada empleada = new Empleada();
        empleada.setNombre(empleadaInfo.nombre);
        empleada.setEdad(empleadaInfo.edad);
        empleada.setSueldo(empleadaInfo.sueldo);
        empleada.setFechaAlta(new Date());

        Categoria categoria = categoriaService.buscarCategoria(empleadaInfo.categoriaId);
        empleada.setCategoria(categoria);
        empleada.setEstado(EstadoEmpleadaEnum.ACTIVO);

        service.crearEmpleada(empleada);
        respuesta.isOk = true;
        respuesta.id = empleada.getEmpleadaId();
        respuesta.message = "La empleada fue creada con éxito";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleada> getEmpleadaPorId(@PathVariable Integer id) {
        Empleada empleada = service.buscarEmpleada(id);

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
    
    


}
