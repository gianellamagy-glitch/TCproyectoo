package erpobserver.model;

import erpobserver.observer.ObservadorERP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Inventario {
    private final Map<String, Producto> productos = new LinkedHashMap<>();
    private final List<ObservadorERP> observadores = new ArrayList<>();
    private int operacionesRealizadas;
    private int notificacionesEnviadas;

    public void agregarObservador(ObservadorERP observador) {
        observadores.clear();
        observadores.add(observador);
    }

    public void registrarProducto(Producto producto) {
        if (productos.containsKey(producto.getCodigo())) {
            throw new IllegalArgumentException("El código del producto ya existe.");
        }
        productos.put(producto.getCodigo(), producto);
        operacionesRealizadas++;
        notificar("Nuevo producto", producto, "Se registró un nuevo producto en inventario.");
    }

    public void actualizarStock(String codigo, int nuevoStock) {
        Producto producto = buscarProductoActivo(codigo);
        int stockAnterior = producto.getStock();
        producto.setStock(nuevoStock);
        operacionesRealizadas++;
        notificar("Actualización de stock", producto,
                "El stock cambió de " + stockAnterior + " a " + nuevoStock + ".");
    }

    public void modificarPrecio(String codigo, double nuevoPrecio) {
        Producto producto = buscarProductoActivo(codigo);
        double precioAnterior = producto.getPrecio();
        producto.setPrecio(nuevoPrecio);
        operacionesRealizadas++;
        notificar("Cambio de precio", producto,
                String.format("El precio cambió de S/ %.2f a S/ %.2f.", precioAnterior, nuevoPrecio));
    }

    public void desactivarProducto(String codigo) {
        Producto producto = buscarProductoActivo(codigo);
        producto.desactivar();
        operacionesRealizadas++;
        notificar("Producto desactivado", producto, "El producto fue desactivado del catálogo.");
    }

    public List<Producto> listarProductos() {
        return Collections.unmodifiableList(new ArrayList<>(productos.values()));
    }

    public int getOperacionesRealizadas() {
        return operacionesRealizadas;
    }

    public int getNotificacionesEnviadas() {
        return notificacionesEnviadas;
    }

    private Producto buscarProductoActivo(String codigo) {
        Producto producto = productos.get(codigo);
        if (producto == null) {
            throw new IllegalArgumentException("No existe un producto con ese código.");
        }
        if (!producto.isActivo()) {
            throw new IllegalArgumentException("El producto está desactivado.");
        }
        return producto;
    }

    private void notificar(String tipoCambio, Producto producto, String detalle) {
        for (ObservadorERP observador : observadores) {
            Notificacion notificacion = new Notificacion(
                    observador.getNombreArea(),
                    tipoCambio,
                    producto.getCodigo(),
                    detalle + " Producto: " + producto.getDescripcion()
            );
            observador.actualizar(notificacion);
            notificacionesEnviadas++;
        }
    }
}
