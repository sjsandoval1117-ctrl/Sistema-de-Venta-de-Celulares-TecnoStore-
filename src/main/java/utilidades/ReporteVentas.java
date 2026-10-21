package utilidades;

import modelo.Producto;
import java.util.List;

public class ReporteVentas {

    public static void generarCabeceraReporte() {
        System.out.println("==================================");
        System.out.println("     REPORTE VENTAS   ");
        System.out.println("==================================");
    }

    public static void imprimirReporteInventario(List<Producto> productos) {
        generarCabeceraReporte();
        if (productos != null) {
            for (Producto p : productos) {
                System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " | Stock: " + p.getStock() + " | Precio: $" + p.getPrecio());
            }
        }
        System.out.println("==================================");
    }
}