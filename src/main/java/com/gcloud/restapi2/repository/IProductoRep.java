package com.gcloud.restapi2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcloud.restapi2.entity.Categoria;
import com.gcloud.restapi2.entity.Producto;

@Repository
public interface IProductoRep extends JpaRepository<Producto, Long>{

	public List<Producto> findByCategoria(Categoria categoria);

	public Producto findByIdProducto(Long idProducto);

	public Producto findByCodProducto(String codProducto);

}
