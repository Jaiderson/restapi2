package com.gcloud.restapi2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcloud.restapi2.entity.Categoria;

@Repository
public interface ICategoriaRep extends JpaRepository<Categoria, Long>{

	public List<Categoria> findByIdCategoria(Long idCategoria);

	public Categoria findByCodCategoria(String codCategoria);

}
