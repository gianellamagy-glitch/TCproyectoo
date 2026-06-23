package erpobserver.model;

public class Producto {
    private String codigo;
    private String descripcion;
    private String categoria;
    private int stock;
    private double precio;
    private boolean activo;

    public Producto(String codigo, String descripcion, String categoria, int stock, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.activo = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void desactivar() {
        this.activo = false;
    }
}
