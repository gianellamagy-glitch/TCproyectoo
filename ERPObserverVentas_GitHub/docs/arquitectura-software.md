# Arquitectura de Software

## Estilo

La solución utiliza una arquitectura por capas sencilla para una aplicación de escritorio:

1. **Capa de presentación**
   - Clase principal: `erpobserver.ui.MainFrame`.
   - Contiene formularios, tablas, botones y mensajes.

2. **Capa de dominio**
   - Clases: `Producto`, `Inventario`, `Notificacion`.
   - Administra reglas de negocio: registrar, actualizar stock, cambiar precio y desactivar productos.

3. **Capa de patrón Observer**
   - Interfaz: `ObservadorERP`.
   - Observador concreto: `ObservadorVentas`.
   - El inventario notifica automáticamente al observador cuando cambia el estado de un producto.

## Flujo general

El usuario realiza una operación desde el formulario. La interfaz llama al método correspondiente de `Inventario`. El inventario modifica el producto, crea una `Notificacion` y la envía al observador `Ventas`. Luego la interfaz actualiza la tabla de productos, el historial y el resumen.

## Módulos

- **Inventario**
  - Registra productos.
  - Actualiza stock.
  - Modifica precios.
  - Desactiva productos.
  - Envía notificaciones.

- **Ventas**
  - Recibe notificaciones.
  - Guarda historial detallado.

- **Interfaz gráfica**
  - Formularios para operaciones.
  - Tabla de productos.
  - Historial de notificaciones.
  - Resumen operativo.

## Decisión de diseño

Se eligió únicamente el área **Ventas** como observador para cumplir con el requisito de seleccionar una sola área o proceso del ERP. El diseño permite agregar en el futuro otros observadores, como Compras, Contabilidad o Reportes, sin modificar la lógica principal de Inventario.

