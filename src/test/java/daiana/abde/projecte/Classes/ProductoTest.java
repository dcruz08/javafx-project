package daiana.abde.projecte.Classes;

import abderrahim.ouabou.Fitxers;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {
    private static Producto testProducto;
    private static Producto testProducto2;

    @BeforeEach
    void setUp() {
        testProducto = new Producto("12345678901", "Lays", "patatas", 10.99, LocalDate.of(2024, 12, 31));
        testProducto2 = new Producto("98765432112", "Yogurt Griego", "yogurt pack 6", 15.99, LocalDate.of(2024, 6, 30));
    }

    @AfterEach
    void tearDown() throws IOException {
        try {
            testProducto.eliminarProducto();
        } catch (Exception e) {
            System.err.println("Error al eliminar testProducto: " + e.getMessage());
        }

        try {
            testProducto2.eliminarProducto();
        } catch (Exception e) {
            System.err.println("Error al eliminar testProducto2: " + e.getMessage());
        }

    }

    @Test
    @Order(1)
    void testGuardarProducto() throws Exception {
        testProducto.guardarProducto();
        List<Producto> productos = Producto.listarProductos();
        boolean productoEnLista = false;
        System.out.println(productos);

        for (Producto p : productos) {
            if (p.getCodigoBarras().equals(testProducto.getCodigoBarras())) {
                productoEnLista = true;
                break;
            }
        }
        assertTrue(productoEnLista, "El producto debe estar en el listado de productos");
    }

    @Test
    @Order(2)
    void testListarProductos() throws Exception {
        testProducto.guardarProducto();
        testProducto2.guardarProducto();
        List<Producto> productos = Producto.listarProductos();
        System.out.println(productos);
        assertEquals(2, productos.size());
    }

    @Test
    @Order(3)
    void testEliminarProducto() throws Exception {
        testProducto.guardarProducto();
        testProducto2.guardarProducto();
        testProducto.eliminarProducto();

        List<Producto> productosRestantes = Producto.listarProductos();

        boolean productoEnLista = false;
        for (Producto p : productosRestantes) {
            if (p.getCodigoBarras().equals(testProducto.getCodigoBarras())) {
                productoEnLista = true;
                break;
            }
        }
        assertFalse(productoEnLista, "El producto no debe estar en el listado de productos.");
        }
}