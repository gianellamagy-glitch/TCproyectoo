# ERPObserverVentas

Aplicación de escritorio en Java Swing desarrollada para Apache NetBeans.

## Descripción

El sistema permite gestionar productos del módulo de Inventario y notificar automáticamente al área de Ventas cuando ocurren cambios importantes.

## Patrón de diseño aplicado

Se aplica el patrón **Observer**:

- `Inventario`: sujeto observado.
- `ObservadorERP`: interfaz del observador.
- `ObservadorVentas`: observador concreto.
- `Notificacion`: mensaje enviado al observador.

## Funcionalidades

- Registrar productos.
- Actualizar stock.
- Modificar precios.
- Desactivar productos.
- Generar notificaciones automáticas.
- Mostrar historial de notificaciones.
- Mostrar resumen de operaciones realizadas.

## Archivos fuente principales

- `src/erpobserver/Main.java`
- `src/erpobserver/model/Producto.java`
- `src/erpobserver/model/Inventario.java`
- `src/erpobserver/model/Notificacion.java`
- `src/erpobserver/observer/ObservadorERP.java`
- `src/erpobserver/observer/ObservadorVentas.java`
- `src/erpobserver/ui/MainFrame.java`

## Cómo ejecutar

1. Abrir Apache NetBeans.
2. Seleccionar `File > Open Project`.
3. Elegir la carpeta del proyecto.
4. Ejecutar con `Run Project`.

