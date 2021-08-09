package ar.com.ada.api.empleadas.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import ar.com.ada.api.empleadas.entities.*;
import ar.com.ada.api.empleadas.models.response.GenericResponse;
import ar.com.ada.api.empleadas.services.CategoriaService;


@RestController
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    //POST / categorias
    @PostMapping("/categorias") // Ningun web Method devuelve void
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){ // ? Me permite devolver cualquier tipo de cosas
        
        GenericResponse respuesta = new GenericResponse();
        service.crearCategoria(categoria);
        respuesta.isOk = true;
        respuesta.id = categoria.getCategoriaId();
        respuesta.message = "La categoria fue creada con éxito";

        return ResponseEntity.ok(respuesta);

    }

    //GET / categorias
    @GetMapping("/categorias") //Hacer el mapping 
    public ResponseEntity<List<Categoria>> traerCategorias(){ //Return Response Entity 
        return ResponseEntity.ok(service.traerCategorias()); //Return entity con el valor esperado
           
    }
    
}
