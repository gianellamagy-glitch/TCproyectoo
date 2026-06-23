package erpobserver.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificacion {
    private final LocalDateTime fechaHora;
    private final String areaDestino;
    private final String tipoCambio;
    private final String codigoProducto;
    private final String mensaje;

    public Notificacion(String areaDestino, String tipoCambio, String codigoProducto, String mensaje) {
        this.fechaHora = LocalDateTime.now();
        this.areaDestino = areaDestino;
        this.tipoCambio = tipoCambio;
        this.codigoProducto = codigoProducto;
        this.mensaje = mensaje;
    }

    public String getFechaHoraFormateada() {
        return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getAreaDestino() {
        return areaDestino;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getMensaje() {
        return mensaje;
    }
}
