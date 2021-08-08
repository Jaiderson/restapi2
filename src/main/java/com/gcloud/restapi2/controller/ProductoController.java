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
import com.gcloud.restapi2.entity.Producto;
import com.gcloud.restapi2.service.IProductoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/productos")
public class ProductoController {

	@Autowired
	private IProductoService productoService;

	@GetMapping
	@ApiOperation(value = "Permite listar productos registrados.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Productos encontrados."),
		@ApiResponse(code = 204, message = "Sin Contenido."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Producos para el <b>idCategoria<b> no encontrados.")
	})
	public ResponseEntity<List<Producto>> listarProductos(@ApiParam(name="idCategoria", value="Valor opcional para traer los productos de una categoria.")
			   @RequestParam(name="idCategoria", required = false) Long idCategoria){
		List<Producto> productos = new ArrayList<Producto>();
		if(null == idCategoria) {
			productos = productoService.consultarProductos();
			if(productos.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Productos no encontrados.");
			}
		}
		else {
			Categoria categoria = Categoria.builder().idCategoria(idCategoria).build();
			productos = productoService.consultarProductosCategoria(categoria);
			if(productos.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Productos de la categoria "+idCategoria+" no encontrados.");
			}
		}
		return ResponseEntity.ok(productos); 
	}

	@GetMapping(value = "/{idProducto}")
	@ApiOperation(value = "Permite buscar un producto dado su Id.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Producto encontrado."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Producto no encontrado.")
	})
	public ResponseEntity<Producto> buscarProducto(@ApiParam(name="idProdu", value="Id obligatorio del producto a buscar.") @PathVariable("idProducto") Long idProducto){
		Producto producto = productoService.buscarProductoById(idProducto);
		if(null == producto) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto "+idProducto+" no encontrado.");
		}
		return ResponseEntity.ok(producto);
	}

	@PostMapping
	@ApiOperation(value = "Permite crear un producto nuevo.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Producto ya existe."),
		@ApiResponse(code = 201, message = "Producto Creado."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion del producto incompleta.")
	})
	public ResponseEntity<Producto> crearProducto(@ApiParam(name="producto", value="Producto a crear")
			@Valid @RequestBody Producto producto, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError("CREAR-01");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		Producto nuevoPorducto = productoService.crearProducto(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPorducto);
	}

	@PutMapping(value = "/{idProduct}")
	@ApiOperation(value = "Permite modificar un producto existente.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Producto actualizado correctamente."),
		@ApiResponse(code = 400, message = "Informacion del producto incompleta"),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Producto no encontrada.")
	})
	public ResponseEntity<Producto> modificarProducto(@ApiParam(name="idProduct", value="Id obligatorio del producto a actualizar.") @PathVariable("idProduct") Long idProducto, 
			@ApiParam(name="producto", value="Producto a actualizar.") @Valid @RequestBody Producto producto, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError("MODIFICAR-01");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		producto.setIdProducto(idProducto);
		Producto modPorducto = productoService.modificarProducto(producto);
		if(null == modPorducto) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(modPorducto);
	}

	@DeleteMapping(value="/{idProdu}")
	@ApiOperation(value = "Permite eliminar una producto existente.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Producto eliminado correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Producto no encontrado.")
	})
	public ResponseEntity<Producto> eliminarProduto(@ApiParam(name="idProdu", value="Id del producto obligatorio a eliminar.") @PathVariable("idProdu") Long idProducto){
		Producto producto = productoService.eliminarProducto(idProducto);
		if(null == producto) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto "+idProducto+" no encontrado.");
		}
		return ResponseEntity.ok(producto);
	}

	/*
	 * Genera conflicto con el metodo buscarProducto.
	 * 
	@GetMapping(value = "/{productoId}")
	public ResponseEntity<Producto> modificarStockProducto(@PathVariable("productoId") Long idProducto, 
											@RequestParam(name="nuevoStock", required = true) Double nuevoStock){
		Producto modPorducto = productoService.modificarStockProducto(idProducto, nuevoStock);
		if(null == modPorducto) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(modPorducto);
	}
	*/

}
