package com.gcloud.restapi2.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcloud.restapi2.entity.Categoria;
import com.gcloud.restapi2.entity.Producto;
import com.gcloud.restapi2.repository.IProductoRep;
import com.gcloud.restapi2.service.IProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImp implements IProductoService {

	@Autowired
	private final IProductoRep productoRep;

	@Override
	public Producto buscarProductoById(Long idProducto) {
		return productoRep.findByIdProducto(idProducto);
	}

	@Override
	public Producto buscarProductoByCodigo(String codProducto) {
		return productoRep.findByCodProducto(codProducto);
	}

	@Override
	public Producto crearProducto(Producto producto) {
		Producto productoDb = buscarProductoByCodigo(producto.getCodProducto());
		if(null != productoDb) {
			return productoDb;
		}
		producto.setEstado("CREADO");
		producto.setFecCreacion(new Date());
		return productoRep.save(producto);
	}

	@Override
	public Producto modificarProducto(Producto producto) {
		Producto productoDb = buscarProductoById(producto.getIdProducto());
		if(null == productoDb) {
			return null;
		}
		productoDb.modificarProducto(producto);
		return productoRep.save(producto);
	}

	@Override
	public Producto modificarStockProducto(Long idProducto, double cantidad) {
		Producto productoDb = buscarProductoById(idProducto);

		if(null == productoDb) {
			return null;
		}
		productoDb.setStock(productoDb.getStock() + cantidad);
		return productoRep.save(productoDb);
	}

	@Override
	public Producto eliminarProducto(Long idProducto) {
		Producto productoDb = buscarProductoById(idProducto);
		if(null == productoDb) {
			return null;
		}
		productoDb.setEstado("DELETED");
		productoRep.delete(productoDb);
		return productoDb;
	}

	@Override
	public List<Producto> consultarProductos() {
		return productoRep.findAll();
	}

	@Override
	public List<Producto> consultarProductosCategoria(Categoria categoria) {
		return productoRep.findByCategoria(categoria);
	}

}
