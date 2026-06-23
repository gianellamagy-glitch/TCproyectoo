package erpobserver.observer;

import erpobserver.model.Notificacion;

public interface ObservadorERP {
    String getNombreArea();

    void actualizar(Notificacion notificacion);
}
