package com.gcloud.restapi2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="categoria")
@AllArgsConstructor @NoArgsConstructor @Builder @Data
@ApiModel(description = "Categoria a la que pertenece un producto.")
public class Categoria {

	@Id
	@Column (name="idcategoria", length=20)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, dataType = "Long", value = "Id de la categoria.", example = "125001")
	private Long idCategoria;

	@Column(nullable = false, unique = true, name="codcategoria", length=10)
	@NotNull(message = "El codigo de la categoria no puede ser vacio. ")
	@ApiModelProperty(position = 2, dataType = "String", value = "Codigo unico de la categoria.", example = "01200")
	private String codCategoria;

	@Column(name="nomcategoria", length=100)
	@NotNull(message = "El nombre de la categoria no puede ser vacio. ")
	@ApiModelProperty(position = 3, dataType = "String", value = "Nombre de la categoria.", example = "Articulos de aseo.")
	private String nomCategoria;

}
