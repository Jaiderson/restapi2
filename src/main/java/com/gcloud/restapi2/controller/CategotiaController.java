package com.gcloud.restapi2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gcloud.restapi2.entity.Categoria;
import com.gcloud.restapi2.service.ICategoriaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/categorias")
public class CategotiaController {

	@Autowired
	private ICategoriaService categoriaService;

	@GetMapping
	@ApiOperation(value = "Busca todas las categorias registradas.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Categoria(s) encontrada(s) OK."),
		@ApiResponse(code = 204, message = "No se encontro recurso(s)."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Categoria no encontrada.")
	})
	public ResponseEntity<List<Categoria>> listarCategorias(@ApiParam(name="idCategoria", value="Opcional para buscar por Id de catoagoria.") 
	                        @RequestParam(name="idCategoria", required = false) Long idCategoria){
		List<Categoria> categorias = new ArrayList<Categoria>();
		if(null == idCategoria) {
			categorias = categoriaService.consultarCategorias();
			if(categorias.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen categorias registradas.");
			}
		}
		else {
			categorias = categoriaService.consultarCategorias(idCategoria);
			if(categorias.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen categorias registradas para el Id = " + idCategoria);
			}
		}
		return ResponseEntity.ok(categorias); 
	}

	@GetMapping(value = "/{idCategoria}")
	@ApiOperation(value = "Permite buscar una categoria dado su Id.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Categoria encontrada OK."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Categoria no encontrada.")
	})
	public ResponseEntity<Categoria> buscarCategoria(@PathVariable("idCategoria") Long idCategoria){
		Categoria categoria = categoriaService.buscarCategoria(idCategoria);
		if(null == categoria) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria "+idCategoria+" no encontrado.");
		}
		return ResponseEntity.ok(categoria);
	}

	@PostMapping
	@ApiOperation(value = "Permite crear una categoria nueva.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Categoria Creada"),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion incompleta")
	})
	public ResponseEntity<Categoria> crearCategoria(@ApiParam(name="categoria", value="Categoria a crear") @Valid @RequestBody Categoria categoria, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError("CREAR-01");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		Categoria nuevoPorducto = categoriaService.crearCategoria(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPorducto);
	}

	@PutMapping(value = "/{idCategoria}")
	@ApiOperation(value = "Permite modificar una categoria existente.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Categoria actualizada correctamente."),
		@ApiResponse(code = 400, message = "Informacion incompleta"),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Categoria no encontrada.")
	})
	public ResponseEntity<Categoria> modificarCategoria(@PathVariable("idCategoria") 
			Long idCategoria, @Valid @RequestBody Categoria categoria, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError("MODIFICAR-01");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		categoria.setIdCategoria(idCategoria);
		Categoria modPorducto = categoriaService.modificarCategoria(categoria);
		if(null == modPorducto) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria "+idCategoria+" no encontrada.");
		}
		return ResponseEntity.ok(modPorducto);
	}

	@DeleteMapping(value="/{idCategoria}")
	@ApiOperation(value = "Permite eliminar una categoria existente.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Categoria eliminada correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Categoria no encontrada.")
	})
	public ResponseEntity<Categoria> eliminarProduto(@PathVariable("idCategoria") Long idCategoria){
		Categoria categoria = categoriaService.eliminarCategoria(idCategoria);
		if(null == categoria) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria "+idCategoria+" no encontrada.");
		}
		return ResponseEntity.ok(categoria);
	}
}
