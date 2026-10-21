package Controlador;

import java.io.IOException;
import modelo.Celular;
import modelo.DetalleVenta;
import utilidades.ArchivoUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import modelo.CelularFactory;
import java.text.DecimalFormat;

public final class ModuloVentas {
    private final List<Celular> inventarioMemoria = new ArrayList<>();
    private final List<DetalleVenta> carritoActual = new ArrayList<>();

    public ModuloVentas() {
        inventarioMemoria.add(CelularFactory.crearCelular(1, "Sara", "21/10/26", "Samsung", "Galaxy S25 Ultra", 4999900.0, 10, "Pancracio"));
        inventarioMemoria.add(CelularFactory.crearCelular(2,"Paula", "15/11/26", "Apple",  "iPhone 14", 3500000.0, 5, "Laurita"));
        inventarioMemoria.add(CelularFactory.crearCelular(3,"Katerin","01/11/26", "Xiaomi", "Redmi Note 13", 1200000.0, 8, "Felipe"));
        inventarioMemoria.add(CelularFactory.crearCelular(4,"Laura","15/04/26", "Motorola", "Moto G67 5G", 987900.0, 12,"Pepita"));
        inventarioMemoria.add(CelularFactory.crearCelular(5,"Lilian","17/11/26", "Infinix", "Note 60 5G", 1149900.0, 15, "Manuela"));
        inventarioMemoria.add(CelularFactory.crearCelular(6,"Paolo","06/01/27", "Realme", "12 Pro+", 1850000.0, 7,"eugenio"));
       
        reiniciarStockBD();
    }
    
    public void reiniciarStockBD() {
        String url = "jdbc:mysql://localhost:3306/tecnostore_SaraSandoval?useSSL=false&serverTimezone=UTC";
        String user = "campus2023"; 
        String password = "campus2023";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, password);
             java.sql.PreparedStatement pstmt = conn.prepareStatement("UPDATE celular SET stock = 50")) {
            pstmt.executeUpdate();
            System.out.println("Stock en la base de datos restablecido a 50 unidades.");
        } catch (Exception e) {
            System.out.println("Aviso al restablecer stock: " + e.getMessage());
        }
    }
    
    public List<Celular> getInventarioMemoria() {
        return inventarioMemoria;
    }

    public void agregarAlInventario(Celular c) {
        if (c != null) {
            inventarioMemoria.add(c);
        }
    }

    public List<Celular> filtrarPorMarca(String marcaBusqueda) {
        if (marcaBusqueda == null || marcaBusqueda.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String busquedaLimpia = marcaBusqueda.trim().toLowerCase();
        
        return inventarioMemoria.stream()
            .filter(c -> c != null && c.getMarca() != null)
            .filter(c -> c.getMarca().trim().toLowerCase().contains(busquedaLimpia))
            .collect(Collectors.toList());
    }

    public void agregarAlCarrito(Celular celular, int cantidad) throws Exception {
        if (!celular.verificarStock(cantidad)) {
            throw new Exception("Error: Stock insuficiente. Solo quedan " + celular.getStock() + " unidades.");
        }
        carritoActual.add(new DetalleVenta(celular, cantidad));
        System.out.println("Producto agregado al carrito temporal con éxito.");
    
    }
    public void noEncontrado() {
        
        try() { 
            System.out.println("PRODUCTO NO ENCONTRADO." + e.getMessage());
        } catch (Exception e) {
            System.out.println("Verifique nuevamente " + e.getMessage());
        }
    }
    public void finalizarVentaYGenerarReporte() {
        if (carritoActual.isEmpty()) {
            System.out.println("El carrito está vacío. No hay productos para procesar.");
            return;
        }

        double totalGeneral = carritoActual.stream()
            .mapToDouble(DetalleVenta::getSubtotal)
            .sum();

        int totalUnidades = carritoActual.stream()
            .mapToInt(DetalleVenta::getCantidad)
            .sum();

        String mensajeCantidad;
        if (totalUnidades >= 3) {
            mensajeCantidad = "Felicidades Has realizado una COMPRA AL POR MAYOR (" + totalUnidades + " unidades). ¡Gracias por preferir a TecnoStore para tus compras corporativas!";
        } else {
            mensajeCantidad = "Gracias por tu compra al por menor (" + totalUnidades + " unidad/es). ¡Disfruta tu nuevo dispositivo TecnoStore!";
        }

        System.out.println("\n--------------------------------------------------");
        System.out.println(mensajeCantidad);
        System.out.println("--------------------------------------------------\n");

        DecimalFormat df = new DecimalFormat("#,##0.00", new java.text.DecimalFormatSymbols(java.util.Locale.US));

        StringBuilder sb = new StringBuilder();
        sb.append("==== RESUMEN DE VENTAS TOTALES TECNOSTORE ====\n");
        sb.append("Vendedora: Sara Julieth Sandoval Orellan (DEV001)\n");
        sb.append("Fecha/Hora: ").append(java.time.LocalDateTime.now()).append("\n");
        sb.append("Mensaje: ").append(mensajeCantidad).append("\n");
        StringBuilder append = sb.append("total vendidos:").append(mensajeCantidad).append("\n");
        sb.append("----------------------------------------------\n");
        for (DetalleVenta dv : carritoActual) {
            sb.append("- ").append(dv.getCelular().getMarca()).append(" ").append(dv.getCelular().getModelo())
              .append(" | Cantidad: ").append(dv.getCantidad())
              .append(" | Subtotal: $").append(df.format(dv.getSubtotal())).append("\n");
        }
        sb.append("----------------------------------------------\n");
        sb.append("TOTAL UNIDADES: ").append(totalUnidades).append("\n");
        sb.append("TOTAL GENERAL: $").append(df.format(totalGeneral)).append("\n");
        sb.append("==============================================\n");

        try {
            String rutaProyecto = System.getProperty("user.dir");
            java.io.File carpeta = new java.io.File(rutaProyecto, "reportes");
            
            if (!carpeta.exists()) {
                boolean creada = carpeta.mkdirs();
                if (creada) {
                    System.out.println("Carpeta 'reportes' creada exitosamente en: " + carpeta.getAbsolutePath());
                }
            }

            String rutaArchivo = carpeta.getAbsolutePath() + "/ResumenVenta_" + System.currentTimeMillis() + ".txt";
            try (java.io.FileWriter writer = new java.io.FileWriter(rutaArchivo)) {
                writer.write(sb.toString());
            }

            System.out.println("Resumen de ventas exportado exitosamente a '" + rutaArchivo + "'.");

        } catch (IOException e) {
            System.out.println("Error al guardar el archivo en la carpeta reportes: " + e.getMessage());
        }

        String url = "jdbc:mysql://localhost:3306/tecnostore_SaraSandoval?useSSL=false&serverTimezone=UTC";
        String user = "campus2023"; 
        String password = "campus2023";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            String sqlPedido = "INSERT INTO pedido (cliente_id, vendedor_id, total) VALUES (?, ?, ?)";
            try (java.sql.PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                pstmtPedido.setInt(1, 1);
                pstmtPedido.setInt(2, 1);
                pstmtPedido.setDouble(3, totalGeneral);
                pstmtPedido.executeUpdate();

                try (java.sql.ResultSet generatedKeys = pstmtPedido.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int pedidoId = generatedKeys.getInt(1);

                        String sqlDetalle = "INSERT INTO detalle_venta (pedido_id, celular_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";
                        String sqlUpdateStock = "UPDATE celular SET stock = stock - ? WHERE id = ?";

                        try (java.sql.PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle);
                             java.sql.PreparedStatement pstmtStock = conn.prepareStatement(sqlUpdateStock)) {
                            
                            for (DetalleVenta dv : carritoActual) {
                                pstmtDetalle.setInt(1, pedidoId);
                                pstmtDetalle.setInt(2, dv.getCelular().getId()); 
                                pstmtDetalle.setInt(3, dv.getCantidad());
                                pstmtDetalle.setDouble(4, dv.getSubtotal());
                                pstmtDetalle.executeUpdate();

                                pstmtStock.setInt(1, dv.getCantidad());
                                pstmtStock.setInt(2, dv.getCelular().getId());
                                pstmtStock.executeUpdate();
                            }
                        }
                    }
                }
            }
            conn.commit();
            System.out.println("¡Venta registrada!");
        } catch (Exception e) {
            System.out.println("ERROR AL GUARDAR EN BASE DE DATOS: " + e.getMessage());
            e.printStackTrace();
        }

        carritoActual.clear();
    }
}