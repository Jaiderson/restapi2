package com.gcloud.restapi2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="producto")
@AllArgsConstructor @NoArgsConstructor @Builder @Data
@ApiModel(description = "Producto disponibles para la venta.")
public class Producto {

	@Id
	@Column (name="idproducto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, dataType = "Long", value = "Id del producto.", example = "100", required = true)
	private Long idProducto;
	
	@Column(name="codproducto", unique = true)
	@Size(min = 1, max = 10)
	@NotNull(message = "El codigo del producto no puede ser vacio. ")
	@ApiModelProperty(position = 2, dataType = "String", value = "Codigo unico del producto.", example = "01500", required = true)
	private String codProducto;
	
	@Column(nullable = false, name="nomproducto")
	@Size(min = 10, max = 100)
	@NotNull(message = "El nombre del producto no puede ser vacio. ")
	@ApiModelProperty(position = 3, dataType = "String", value = "Nombre del producto.", example = "JABON BARRIGON MAX", required = true)
	private String nomProducto;
	
	@Column(name="descripcion")
	@Size(min = 10, max = 1000)
	@NotNull(message = "La descripcion del producto no puede ser vacio. ")
	@ApiModelProperty(position = 4, dataType = "String", value = "Descripcion detallada del producto.", example = "JABON AZUL 700 gr PRESENTACION GIGANTE DIS P&G")
	private String descripcion;

	@Column(name="stock")
	@Positive(message = "El stock debe ser mayor a cero.")
	@ApiModelProperty(position = 5, dataType = "double", value = "Cantidad de la exisyencia del producto.", example = "150.00")
	private double stock;

	@Column(name="precio")
	@Positive(message = "El precio debe ser mayor a cero.")
	@ApiModelProperty(position = 6, dataType = "double", value = "Precio del producto.", example = "3200.00")
	private double precio;

	@Column(name="estado")
	@Size(min = 5, max = 20)
	@ApiModelProperty(position = 7, dataType = "String", value = "Estado del producto.", example = "ELIMINADO")
	private String estado;

	@Column(name="feccreacion")
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(position = 8, dataType = "Date", value = "Fecha creacion del producto.", example = "30/06/2021")
	private Date fecCreacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcategoria")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@NotNull(message = "Debe definir la categoria del producto.")
	@ApiModelProperty(position = 9, dataType = "Categoria", value = "Categoria a la que pertenece el producto.")
	private Categoria categoria;

	public Producto modificarProducto(Producto producto) {

		if(null != producto) {
			this.setCategoria(producto.getCategoria());
			this.setCodProducto(producto.getCodProducto());
			this.setDescripcion(producto.getDescripcion());
			this.setEstado(producto.getEstado());
			this.setFecCreacion(producto.getFecCreacion());
			this.setNomProducto(producto.getNomProducto());
			this.setPrecio(producto.getPrecio());
			this.setStock(producto.getStock());
		}
		return this;
	}

}
