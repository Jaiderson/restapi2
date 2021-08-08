package com.gcloud.restapi2;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.gcloud.restapi2.entity.Categoria;
import com.gcloud.restapi2.entity.Producto;
import com.gcloud.restapi2.repository.IProductoRep;
import com.gcloud.restapi2.service.IProductoService;
import com.gcloud.restapi2.service.impl.ProductoServiceImp;

@SpringBootTest
public class ProductoServiceTest {

	@Mock
	private IProductoRep productoRep;

	private IProductoService productoService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		productoService = new ProductoServiceImp(productoRep);

		Categoria categoria = Categoria.builder().
				idCategoria(1L).
				codCategoria("1000").
				nomCategoria("Equipos de Computo").
				build();

		Producto producto1 = Producto.builder()
				.codProducto("00002")
				.nomProducto("Monitor")
				.descripcion("Monitor generico 21 puldas.")
				.estado("ACTIVO")
				.precio(500)
				.fecCreacion(new Date())
				.stock(80).categoria(categoria).build();
	
		Mockito.when(productoRep.findByIdProducto(1L)).thenReturn(producto1);
		Mockito.when(productoService.modificarStockProducto(1L, 10.0)).thenReturn(producto1);
	}

	@Test
	public void buscarProductoTest() {
		Producto producto = productoRep.findByIdProducto(1L);
		Assertions.assertThat(producto.getNomProducto().equals("Monitor"));
	}

	@Test
	public void modificarStockTest() {
		Producto producto = productoRep.findByIdProducto(1L);
		double stock = producto.getStock() + 10;
		producto = productoService.modificarStockProducto(1L, 10);
		Assertions.assertThat(producto.getStock()).isEqualTo(stock);		
	}

}
