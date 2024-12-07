package com.company.books.backend.service;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//indicamos que es un servicio
@Service
public class CategoriaServiceImpl implements ICategoriaService{

    private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);


    private final ICategoriaDao categoriaDao;

    //"inyectamos" la dependencia
    @Autowired
    public CategoriaServiceImpl(ICategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    @Override
    @Transactional(readOnly = true)//como es un GET solo leemos los datos
    public ResponseEntity <CategoriaResponseRest> buscarCategorias() {

        log.info("inicio del metodo buscarCategorias()");

        CategoriaResponseRest response = new CategoriaResponseRest();

        try{
            List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();

            response.getCategoriaResponse().setCategoria(categoria);

            response.setMetadata("Respuesta: ok", "200", "Respuesta exitosa");

        }catch(Exception e){
            log.error(e.getMessage());
            response.setMetadata("Respuesta: error", "500", e.getMessage());
            e.getStackTrace();

            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //devuelve el response y el statusHttp (el codigo de exito 200)
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)//tenemos que estar seguro de importar del paquete de spring framework para las anotaciones
    public ResponseEntity<CategoriaResponseRest> buscarCategoriasById(Long id) {

        log.info("Inicio del metodo buscarCategoriasById");

        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();

        try{

            Optional<Categoria> categoria =  categoriaDao.findById(id);

            //si devuelve información
            if(categoria.isPresent()){
                list.add(categoria.get());
                response.getCategoriaResponse().setCategoria(list);
                response.setMetadata("Respuesta: ok", "200", "Respuesta exitosa");
            } else {
                log.error("Error en consultar categoria by Id");
                response.setMetadata("Respuesta no Ok", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);//Error 404
            }

        }catch (Exception e){
            log.error(e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta: error", "500", e.getMessage());
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);//devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> insertCategoria(Categoria categoria) {

        log.info("Inicio del método insertCategoria");

        CategoriaResponseRest response = new CategoriaResponseRest();
        List <Categoria> list = new ArrayList<>();

        try{
            Categoria categoriaGuardada = categoriaDao.save(categoria);

            if( categoriaGuardada != null){
                list.add(categoriaGuardada);
                response.getCategoriaResponse().setCategoria(list);
                response.setMetadata("Respuesta: ok", "200", "Respuesta exitosa");
            }else{
                log.error("Error al insertar categoria");
                response.setMetadata("Respuesta no Ok", "-1", "Categoria no guardada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);//devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> updateCategoria(Categoria categoria, Long id) {

        log.info("Inicio del método updateCategoria");

        CategoriaResponseRest response = new CategoriaResponseRest();
        List <Categoria> list = new ArrayList<>();

        try{
            Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);

            if(categoriaBuscada.isPresent()){
                categoriaBuscada.get().setNombre(categoria.getNombre());
                categoriaBuscada.get().setDescripcion(categoria.getDescripcion());

                Categoria categoriaActualizada = categoriaDao.save(categoriaBuscada.get());

                if(categoriaActualizada != null){
                    list.add(categoriaActualizada);
                    response.getCategoriaResponse().setCategoria(list);
                    response.setMetadata("Respuesta: ok", "200", "Respuesta exitosa");
                }else {
                    log.error("Error al actualizar categoria");
                    response.setMetadata("Respuesta no Ok", "-1", "Categoria no actualizada");
                    return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.error("Categoria no encontrada");
                response.setMetadata("Respuesta no Ok", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> deleteCategoria(Long id) {

        log.info("inicio del método deleteCategoria");

        CategoriaResponseRest response = new CategoriaResponseRest();

        try{
            Optional<Categoria> categoriabuscada= categoriaDao.findById(id);

            if(! categoriabuscada.get().getLibros().isEmpty()){
                response.setMetadata("No se puede eliminar", "-1", "No se puede eliminar");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            categoriaDao.deleteById(id);
            response.setMetadata("Respuesta: ok", "200", "Respuesta exitosa");

        }catch (Exception e){
            log.error(e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
    }
}
