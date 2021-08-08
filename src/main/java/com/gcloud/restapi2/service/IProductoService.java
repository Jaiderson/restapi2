package com.gcloud.restapi2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gcloud.restapi2.entity.Categoria;
import com.gcloud.restapi2.entity.Producto;

@Service
public interface IProductoService {

	public Producto buscarProductoById(Long id);

	public Producto buscarProductoByCodigo(String codProducto);

	public Producto crearProducto(Producto producto);

	public Producto modificarProducto(Producto producto);

	public Producto modificarStockProducto(Long idProducto, double cantidad);

	public Producto eliminarProducto(Long idProducto);

	public List<Producto> consultarProductos();

	public List<Producto> consultarProductosCategoria(Categoria categoria);

}
