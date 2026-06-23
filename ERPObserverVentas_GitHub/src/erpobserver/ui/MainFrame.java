package erpobserver.ui;

import erpobserver.model.Inventario;
import erpobserver.model.Notificacion;
import erpobserver.model.Producto;
import erpobserver.observer.ObservadorVentas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private static final Color COLOR_PRINCIPAL = new Color(18, 96, 112);
    private static final Color COLOR_SECUNDARIO = new Color(239, 123, 69);
    private static final Color COLOR_FONDO = new Color(246, 249, 250);

    private final Inventario inventario = new Inventario();
    private final ObservadorVentas ventas = new ObservadorVentas();

    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtDescripcion = new JTextField();
    private final JTextField txtCategoria = new JTextField();
    private final JTextField txtStock = new JTextField();
    private final JTextField txtPrecio = new JTextField();
    private final JTextField txtNuevoStock = new JTextField();
    private final JTextField txtNuevoPrecio = new JTextField();

    private final JComboBox<String> cmbProductosStock = new JComboBox<>();
    private final JComboBox<String> cmbProductosPrecio = new JComboBox<>();
    private final JComboBox<String> cmbProductosDesactivar = new JComboBox<>();

    private final DefaultTableModel productosModel = new DefaultTableModel(
            new Object[]{"Código", "Descripción", "Categoría", "Stock", "Precio", "Estado"}, 0
    );
    private final DefaultTableModel notificacionesModel = new DefaultTableModel(
            new Object[]{"Fecha", "Observador", "Cambio", "Producto", "Mensaje"}, 0
    );

    private final JTable tablaNotificaciones = new JTable(notificacionesModel);
    private final JLabel lblResumen = new JLabel();

    public MainFrame() {
        inventario.agregarObservador(ventas);
        configurarVentana();
        construirInterfaz();
        cargarDatosIniciales();
        refrescarVista();
    }

    private void configurarVentana() {
        setTitle("ERP Inventario - Observer Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1080, 720));
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout(0, 12));
        raiz.setBackground(COLOR_FONDO);
        raiz.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        setContentPane(raiz);

        raiz.add(crearEncabezado(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Registrar producto", crearPanelRegistro());
        tabs.addTab("Actualizar stock", crearPanelStock());
        tabs.addTab("Modificar precio", crearPanelPrecio());
        tabs.addTab("Desactivar producto", crearPanelDesactivar());
        tabs.addTab("Historial y resumen", crearPanelHistorial());
        raiz.add(tabs, BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout(14, 0));
        panel.setBackground(COLOR_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel logo = new JLabel("TN");
        logo.setOpaque(true);
        logo.setBackground(Color.WHITE);
        logo.setForeground(COLOR_PRINCIPAL);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 26));
        logo.setPreferredSize(new Dimension(66, 66));
        panel.add(logo, BorderLayout.WEST);

        JLabel titulo = new JLabel("<html><b>TechNova Software</b><br>Inventario con patrón Observer - Observador seleccionado: Ventas</html>");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(titulo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = crearPanelBase();
        JPanel form = crearFormulario();

        agregarCampo(form, "Código", txtCodigo, 0);
        agregarCampo(form, "Descripción", txtDescripcion, 1);
        agregarCampo(form, "Categoría", txtCategoria, 2);
        agregarCampo(form, "Stock", txtStock, 3);
        agregarCampo(form, "Precio", txtPrecio, 4);

        JButton btnRegistrar = crearBoton("Registrar producto");
        btnRegistrar.addActionListener(event -> registrarProducto());
        agregarComponente(form, btnRegistrar, 5);

        panel.add(form, BorderLayout.WEST);
        panel.add(crearTablaProductos(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelStock() {
        JPanel panel = crearPanelBase();
        JPanel form = crearFormulario();
        agregarCampo(form, "Producto", cmbProductosStock, 0);
        agregarCampo(form, "Nuevo stock", txtNuevoStock, 1);

        JButton btnActualizar = crearBoton("Actualizar stock");
        btnActualizar.addActionListener(event -> actualizarStock());
        agregarComponente(form, btnActualizar, 2);

        panel.add(form, BorderLayout.WEST);
        panel.add(crearTablaProductos(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPrecio() {
        JPanel panel = crearPanelBase();
        JPanel form = crearFormulario();
        agregarCampo(form, "Producto", cmbProductosPrecio, 0);
        agregarCampo(form, "Nuevo precio", txtNuevoPrecio, 1);

        JButton btnActualizar = crearBoton("Modificar precio");
        btnActualizar.addActionListener(event -> modificarPrecio());
        agregarComponente(form, btnActualizar, 2);

        panel.add(form, BorderLayout.WEST);
        panel.add(crearTablaProductos(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelDesactivar() {
        JPanel panel = crearPanelBase();
        JPanel form = crearFormulario();
        agregarCampo(form, "Producto", cmbProductosDesactivar, 0);

        JButton btnDesactivar = crearBoton("Desactivar producto");
        btnDesactivar.addActionListener(event -> desactivarProducto());
        agregarComponente(form, btnDesactivar, 1);

        panel.add(form, BorderLayout.WEST);
        panel.add(crearTablaProductos(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = crearPanelBase();
        lblResumen.setOpaque(true);
        lblResumen.setBackground(Color.WHITE);
        lblResumen.setForeground(COLOR_PRINCIPAL);
        lblResumen.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        lblResumen.setFont(new Font("Arial", Font.BOLD, 15));

        tablaNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaNotificaciones.setRowHeight(28);

        panel.add(lblResumen, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaNotificaciones), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBase() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        return panel;
    }

    private JPanel crearFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 229, 233)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        form.setPreferredSize(new Dimension(320, 420));
        return form;
    }

    private JScrollPane crearTablaProductos() {
        JTable tablaProductos = new JTable(productosModel);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(218, 229, 233)));
        return scroll;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(COLOR_SECUNDARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        return boton;
    }

    private void agregarCampo(JPanel panel, String etiqueta, Component campo, int fila) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = fila * 2;
        labelConstraints.weightx = 1;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;
        labelConstraints.insets = new Insets(0, 0, 4, 0);

        JLabel label = new JLabel(etiqueta);
        label.setForeground(COLOR_PRINCIPAL);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(label, labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 0;
        fieldConstraints.gridy = fila * 2 + 1;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(0, 0, 12, 0);
        campo.setPreferredSize(new Dimension(260, 32));
        panel.add(campo, fieldConstraints);
    }

    private void agregarComponente(JPanel panel, Component componente, int fila) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = fila * 2;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(6, 0, 0, 0);
        panel.add(componente, constraints);
    }

    private void cargarDatosIniciales() {
        inventario.registrarProducto(new Producto("P001", "Licencia ERP Básica", "Software", 12, 450.00));
        inventario.registrarProducto(new Producto("P002", "Soporte mensual", "Servicio", 25, 120.00));
        inventario.registrarProducto(new Producto("P003", "Módulo inventario", "Software", 8, 780.00));
    }

    private void registrarProducto() {
        try {
            Producto producto = new Producto(
                    txtCodigo.getText().trim(),
                    txtDescripcion.getText().trim(),
                    txtCategoria.getText().trim(),
                    Integer.parseInt(txtStock.getText().trim()),
                    Double.parseDouble(txtPrecio.getText().trim())
            );
            validarProducto(producto);
            inventario.registrarProducto(producto);
            limpiarRegistro();
            refrescarVista();
            mostrarMensaje("Producto registrado y notificado al área de Ventas.");
        } catch (NumberFormatException ex) {
            mostrarError("El stock y el precio deben ser valores numéricos.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizarStock() {
        try {
            String codigo = obtenerCodigoSeleccionado(cmbProductosStock);
            int nuevoStock = Integer.parseInt(txtNuevoStock.getText().trim());
            if (nuevoStock < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo.");
            }
            inventario.actualizarStock(codigo, nuevoStock);
            txtNuevoStock.setText("");
            refrescarVista();
            mostrarMensaje("Stock actualizado y notificado al área de Ventas.");
        } catch (NumberFormatException ex) {
            mostrarError("El nuevo stock debe ser un número entero.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void modificarPrecio() {
        try {
            String codigo = obtenerCodigoSeleccionado(cmbProductosPrecio);
            double nuevoPrecio = Double.parseDouble(txtNuevoPrecio.getText().trim());
            if (nuevoPrecio <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor que cero.");
            }
            inventario.modificarPrecio(codigo, nuevoPrecio);
            txtNuevoPrecio.setText("");
            refrescarVista();
            mostrarMensaje("Precio modificado y notificado al área de Ventas.");
        } catch (NumberFormatException ex) {
            mostrarError("El nuevo precio debe ser numérico.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void desactivarProducto() {
        try {
            String codigo = obtenerCodigoSeleccionado(cmbProductosDesactivar);
            inventario.desactivarProducto(codigo);
            refrescarVista();
            mostrarMensaje("Producto desactivado y notificado al área de Ventas.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private String obtenerCodigoSeleccionado(JComboBox<String> combo) {
        Object seleccionado = combo.getSelectedItem();
        if (seleccionado == null) {
            throw new IllegalArgumentException("Seleccione un producto activo.");
        }
        return seleccionado.toString().split(" - ")[0];
    }

    private void validarProducto(Producto producto) {
        if (producto.getCodigo().isEmpty()
                || producto.getDescripcion().isEmpty()
                || producto.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("Complete todos los campos del producto.");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
    }

    private void limpiarRegistro() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCategoria.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
    }

    private void refrescarVista() {
        refrescarProductos();
        refrescarCombos();
        refrescarNotificaciones();
        lblResumen.setText("<html>Operaciones realizadas: " + inventario.getOperacionesRealizadas()
                + " &nbsp;&nbsp;|&nbsp;&nbsp; Notificaciones enviadas a Ventas: "
                + inventario.getNotificacionesEnviadas()
                + " &nbsp;&nbsp;|&nbsp;&nbsp; Observador activo: Ventas</html>");
    }

    private void refrescarProductos() {
        productosModel.setRowCount(0);
        for (Producto producto : inventario.listarProductos()) {
            productosModel.addRow(new Object[]{
                producto.getCodigo(),
                producto.getDescripcion(),
                producto.getCategoria(),
                producto.getStock(),
                String.format("S/ %.2f", producto.getPrecio()),
                producto.isActivo() ? "Activo" : "Desactivado"
            });
        }
    }

    private void refrescarCombos() {
        cmbProductosStock.removeAllItems();
        cmbProductosPrecio.removeAllItems();
        cmbProductosDesactivar.removeAllItems();
        for (Producto producto : inventario.listarProductos()) {
            if (producto.isActivo()) {
                String item = producto.getCodigo() + " - " + producto.getDescripcion();
                cmbProductosStock.addItem(item);
                cmbProductosPrecio.addItem(item);
                cmbProductosDesactivar.addItem(item);
            }
        }
    }

    private void refrescarNotificaciones() {
        notificacionesModel.setRowCount(0);
        List<Notificacion> notificaciones = ventas.getNotificaciones();
        for (Notificacion notificacion : notificaciones) {
            notificacionesModel.addRow(new Object[]{
                notificacion.getFechaHoraFormateada(),
                notificacion.getAreaDestino(),
                notificacion.getTipoCambio(),
                notificacion.getCodigoProducto(),
                notificacion.getMensaje()
            });
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación correcta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }
}
