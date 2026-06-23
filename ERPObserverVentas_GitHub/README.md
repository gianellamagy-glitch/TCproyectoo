# ERP Observer Ventas

Proyecto Java Swing para Apache NetBeans que aplica el patrón de diseño Observer en un módulo de Inventario.

## Área ERP seleccionada

El área elegida es **Ventas**. Esta área actúa como único observador del módulo de Inventario y recibe notificaciones automáticas cuando ocurre alguno de estos cambios:

- Registro de nuevos productos.
- Actualización de stock.
- Modificación de precio.
- Desactivación de productos.

## Empresa y personalización visual

Empresa de desarrollo elegida: **TechNova Software**.

La interfaz usa un logotipo textual `TN` y colores institucionales:

- Principal: verde azulado.
- Secundario: naranja.
- Fondo: gris claro.

## Cómo abrir en Apache NetBeans

1. Abrir Apache NetBeans.
2. Ir a `File > Open Project`.
3. Seleccionar la carpeta `ERPObserverVentas`.
4. Ejecutar el proyecto con `Run Project`.

## Patrón Observer aplicado

- `Inventario`: sujeto observado. Gestiona productos y avisa cambios.
- `ObservadorERP`: interfaz del observador.
- `ObservadorVentas`: observador concreto que recibe y guarda notificaciones.
- `MainFrame`: interfaz gráfica con formularios y tablas.

## Funcionalidades

- Registrar productos con código, descripción, categoría, stock y precio.
- Actualizar stock.
- Modificar precio.
- Desactivar productos.
- Mantener un solo observador ERP: Ventas.
- Generar notificaciones automáticas.
- Mostrar historial detallado de notificaciones.
- Mostrar resumen de operaciones y notificaciones enviadas.

