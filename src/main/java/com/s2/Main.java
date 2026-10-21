package com.s2;

import Controlador.ModuloVentas;
import modelo.Celular;
import utilidades.ArchivoUtils;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ModuloVentas controller = new ModuloVentas();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- SISTEMA TECNOSTORE ---");
            System.out.println("1. Filtrar celulares por Marca");
            System.out.println("2. Agregar celular al Carrito");
            System.out.println("3. Finalizar Venta y generar TXT de Resumen");
            System.out.println("4. Salir");

            int opcion = ArchivoUtils.leerEnteroValidado(scanner, "Seleccione una opción:");

            switch (opcion) {
                case 1 -> {
                    System.out.println("Ingrese la marca a buscar (ej. Samsung, Apple, Xiaomi, Motorola, Infinix, Realme):");
                    String marca = scanner.next(); 

                    List<Celular> filtrados = controller.filtrarPorMarca(marca);

                    if (filtrados.isEmpty()) {
                        System.out.println("No se encontraron celulares con esa marca.");
                    } else {
                        System.out.println("--- RESULTADOS ---");
                        filtrados.forEach(c -> System.out.println(c.getId() + " | " + c.getMarca() + " " + c.getModelo() + " - $" + c.getPrecio() + " (Stock: " + c.getStock() + ")"));
                    }
                }

                case 2 -> {
                    int idCel = ArchivoUtils.leerEnteroValidado(scanner, "Ingrese el ID del celular:");
                    int cant = ArchivoUtils.leerEnteroValidado(scanner, "Ingrese la cantidad:");
                    try {
                        Celular celularEncontrado = controller.getInventarioMemoria().stream()
                            .filter(c -> c.getId() == idCel)
                            .findFirst()
                            .orElse(null);

                        if (celularEncontrado != null) {
                            controller.agregarAlCarrito(celularEncontrado, cant);
                        } else {
                            System.out.println("Error: No se encontró ningún celular con el ID especificado.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 3 -> {
                    controller.finalizarVentaYGenerarReporte();
                }

                case 4 -> {
                    System.out.println("Saliendo del sistema TecnoStore.Hasta pronto");
                    salir = true;
                }

                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
