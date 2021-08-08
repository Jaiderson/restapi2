package com.gcloud.restapi2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcloud.restapi2.entity.Categoria;
import com.gcloud.restapi2.repository.ICategoriaRep;
import com.gcloud.restapi2.service.ICategoriaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements ICategoriaService{

	@Autowired
	private final ICategoriaRep categoriaRep;

	@Override
	public Categoria buscarCategoria(Long idCategoria) {
		return categoriaRep.getById(idCategoria);
	}

	@Override
	public Categoria crearCategoria(Categoria categoria) {
		Categoria categoriaBd = categoriaRep.findByCodCategoria(categoria.getCodCategoria());
		if(null != categoriaBd) {
			return categoriaBd;
		}
		return categoriaRep.save(categoria);
	}

	@Override
	public Categoria modificarCategoria(Categoria categoria) {
		return categoriaRep.save(categoria);
	}

	@Override
	public Categoria eliminarCategoria(Long idCategoria) {
		Categoria categoria = categoriaRep.getById(idCategoria);
		if(null == categoria) {
			return null;
		}
		categoriaRep.delete(categoria);
		return categoria;
	}

	@Override
	public List<Categoria> consultarCategorias() {
		return categoriaRep.findAll();
	}

	@Override
	public List<Categoria> consultarCategorias(Long idCotegoria) {
		return categoriaRep.findByIdCategoria(idCotegoria);
	}

}
