package modelo;

public class Celular {
    private int id;
    private String marca;
    private String modelo;
    private double precio;
    private int stock;

    public Celular(int id, String marca, String modelo, int stock, int aInt2, int aInt3, int aInt4) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
    }

    public Celular(int aInt, String string, String string0, int aInt0, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean verificarStock(int cantidad) {
        return this.stock >= cantidad;
    }

    public void reducirStock(int cantidad) {
        if (verificarStock(cantidad)) {
            this.stock -= cantidad;
        }
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}