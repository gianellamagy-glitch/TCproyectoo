package erpobserver.observer;

import erpobserver.model.Notificacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObservadorVentas implements ObservadorERP {
    private final List<Notificacion> notificaciones = new ArrayList<>();

    @Override
    public String getNombreArea() {
        return "Ventas";
    }

    @Override
    public void actualizar(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    public List<Notificacion> getNotificaciones() {
        return Collections.unmodifiableList(notificaciones);
    }
}
