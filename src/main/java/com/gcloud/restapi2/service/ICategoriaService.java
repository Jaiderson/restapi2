package com.gcloud.restapi2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gcloud.restapi2.entity.Categoria;

@Service
public interface ICategoriaService {
	
	public Categoria buscarCategoria(Long idCategoria);

	public Categoria crearCategoria(Categoria categoria);

	public Categoria modificarCategoria(Categoria categoria);

	public Categoria eliminarCategoria(Long idCategoria);

	public List<Categoria> consultarCategorias();

	public List<Categoria> consultarCategorias(Long idCategoria);
}
