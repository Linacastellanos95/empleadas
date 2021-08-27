package ar.com.ada.api.empleadas.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleadas.entities.*;
import ar.com.ada.api.empleadas.entities.Empleada.EstadoEmpleadaEnum;
import ar.com.ada.api.empleadas.models.request.InfoEmpleadaNueva;
import ar.com.ada.api.empleadas.repos.EmpleadaRepository;


@Service
public class EmpleadaService {

    @Autowired
    private EmpleadaRepository repo;

    @Autowired
    CategoriaService categoriaService;

    public void crearEmpleada (Empleada empleada){
        repo.save(empleada);
    }


    public List<Empleada> traerEmpleadas(){
        return repo.findAll();
    }

    public Empleada buscarEmpleada(Integer empleadaId){
        Optional<Empleada> resultado = repo.findById(empleadaId);

        if(resultado.isPresent())
            return resultado.get();

        return null;
    }


        
    public Empleada crearEmpleada(InfoEmpleadaNueva empleadaInfo){
        Empleada empleada = new Empleada(empleadaInfo.nombre, empleadaInfo.edad, empleadaInfo.sueldo, new Date());
 
        Categoria categoria = categoriaService.buscarCategoria(empleadaInfo.categoriaId);
        empleada.setCategoria(categoria);
        empleada.setEstado(EstadoEmpleadaEnum.ACTIVO);

        crearEmpleada(empleada);
        return empleada;
    }
    //DELETE LOGICO, se mantiene en la BD pero con estatus.
    public void bajaEmpleadaporId(Integer id) {
        Empleada empleada = this.buscarEmpleada(id);
        empleada.setEstado(EstadoEmpleadaEnum.BAJA);
        empleada.setFechaBaja(new Date());

        repo.save(empleada);
    }

    public List<Empleada> traerEmpleadasPorCategoria(Integer catId) {
       Categoria categoria = categoriaService.buscarCategoria(catId);
        return categoria.getEmpleadas();
    }


    public void guardar(Empleada empleada) {
        repo.save(empleada);
    }

    
}
