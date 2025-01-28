/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.gestioneventos;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import java.util.Date;
public class GestionEventos extends JFrame {

    private final Color PRIMARY_COLOR = new Color(42, 53, 81);
    private final Color SECONDARY_COLOR = new Color(67, 130, 232);
    private final Color BACKGROUND_COLOR = new Color(245, 247, 251);

    private EventoDAO eventoDAO = new EventoDAO();
    private LugarDAO lugarDAO = new LugarDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();

    private JTable eventosTable;
    private JTable lugaresTable;
    private JTable reservasTable;

    public GestionEventos() throws SQLException {
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("Gestión de Eventos - Modern UI");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel header = createHeader();
        mainPanel.add(header, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new CustomTabbedPaneUI());
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(PRIMARY_COLOR);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tabbedPane.addTab("Eventos", createEventosTab());
        tabbedPane.addTab("Lugares", createLugaresTab());
        tabbedPane.addTab("Reservas", createReservasTab());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(0, 60));

        JLabel title = new JLabel("Gestión de Eventos");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 20, 0, 0));
        header.add(title, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton refreshBtn = createStyledButton("Actualizar", SECONDARY_COLOR);
        refreshBtn.addActionListener(e -> {
            try {
                loadData();
            } catch (SQLException ex) {
                Logger.getLogger(GestionEventos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JButton exitBtn = createStyledButton("Salir", new Color(220, 53, 69));
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(refreshBtn);
        buttonPanel.add(exitBtn);
        header.add(buttonPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createEventosTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columnNames = {"ID", "Nombre", "Fecha", "Tipo", "Duración"};
        eventosTable = new JTable();
        eventosTable.setModel(new DefaultTableModel(columnNames, 0));
        eventosTable.setRowHeight(40);
        eventosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        eventosTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(eventosTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton verDetallesBtn = createStyledButton("Ver Detalles Completo", PRIMARY_COLOR);
        verDetallesBtn.addActionListener(e -> showEventDetails());
        panel.add(verDetallesBtn, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLugaresTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columnNames = {"ID", "Nombre", "Disponibilidad", "Capacidad"};
        lugaresTable = new JTable();
        lugaresTable.setModel(new DefaultTableModel(columnNames, 0));
        lugaresTable.setRowHeight(40);
        lugaresTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(lugaresTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton reservarBtn = createStyledButton("Reservar Lugar", SECONDARY_COLOR);
        reservarBtn.addActionListener(e -> showReservaDialog());
        panel.add(reservarBtn, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReservasTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columnNames = {"ID", "Evento", "Lugar", "Fecha", "Estado"};
        reservasTable = new JTable();
        reservasTable.setModel(new DefaultTableModel(columnNames, 0));
        reservasTable.setRowHeight(40);
        reservasTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(reservasTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton cancelarBtn = createStyledButton("Cancelar Reserva", new Color(220, 53, 69));
        JButton confirmarBtn = createStyledButton("Confirmar Pago", new Color(40, 167, 69));

        cancelarBtn.addActionListener(e -> cambiarEstadoReserva(EstadoReserva.Cancelada));
        confirmarBtn.addActionListener(e -> showPagoDialog());

        buttonPanel.add(cancelarBtn);
        buttonPanel.add(confirmarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void showEventDetails() {
        int selectedRow = eventosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento primero", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEvento = (int) eventosTable.getValueAt(selectedRow, 0);
        Evento evento = eventoDAO.obtenerPorId(idEvento);

        JDialog dialog = new JDialog(this, "Detalles del Evento", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        details.setText(
                "Nombre: " + evento.getNombreEvento() + "\n\n"
                + "Tipo: " + evento.getTipoEvento() + "\n\n"
                + "Fecha: " + evento.getFecha() + "\n\n"
                + "Duración: " + evento.getDuracion() + " horas\n\n"
                + "Descripción:\n" + evento.getDescripcion()
        );

        content.add(new JScrollPane(details), BorderLayout.CENTER);
        dialog.add(content);
        dialog.setVisible(true);
    }

    private void showReservaDialog() {
        JDialog dialog = new JDialog(this, "Nueva Reserva", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new GridLayout(0, 2, 10, 10));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JComboBox<EventoWrapper> eventoCombo = new JComboBox<>();
        JComboBox<LugarWrapper> lugarCombo = new JComboBox<>();

        try {
            List<Evento> eventos = eventoDAO.listarEventos();
            for (Evento evento : eventos) {
                eventoCombo.addItem(new EventoWrapper(evento));
            }

            List<Lugar> lugares = lugarDAO.listarTodos();
            for (Lugar lugar : lugares) {
                lugarCombo.addItem(new LugarWrapper(lugar));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dialog, "Error cargando datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());

        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());

        content.add(new JLabel("Evento:"));
        content.add(eventoCombo);
        content.add(new JLabel("Lugar:"));
        content.add(lugarCombo);
        content.add(new JLabel("Fecha:"));
        content.add(dateChooser);
        content.add(new JLabel("Hora:"));
        content.add(timeSpinner);

        JButton confirmarBtn = createStyledButton("Confirmar", SECONDARY_COLOR);
        confirmarBtn.addActionListener(e -> {
            try {
                EventoWrapper eventoSeleccionado = (EventoWrapper) eventoCombo.getSelectedItem();
                LugarWrapper lugarSeleccionado = (LugarWrapper) lugarCombo.getSelectedItem();

                Date selectedDate = dateChooser.getDate();
                Date selectedTime = (Date) timeSpinner.getValue();

                LocalDateTime fechaHora = combineDateTime(selectedDate, selectedTime);

                Reserva reserva = new Reserva(
                        eventoSeleccionado.getId(),
                        lugarSeleccionado.getId(),
                        fechaHora,
                        eventoSeleccionado.getTipo()
                );

                reservaDAO.insertarReserva(reserva);
                lugarDAO.actualizarDisponibilidad(reserva.getIdLugar(), false);
                loadData();
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmarBtn);

        dialog.setLayout(new BorderLayout());
        dialog.add(content, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private LocalDateTime combineDateTime(Date date, Date time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
        }

        LocalDateTime dateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime timeOnly = time.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return dateTime
                .withHour(timeOnly.getHour())
                .withMinute(timeOnly.getMinute());
    }

    private void showPagoDialog() {
        int selectedRow = reservasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva primero", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Confirmar Pago", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new GridLayout(0, 2, 10, 10));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JComboBox<MetodoPago> metodoCombo = new JComboBox<>(MetodoPago.values());
        JTextField montoField = new JTextField();

        content.add(new JLabel("Método de Pago:"));
        content.add(metodoCombo);
        content.add(new JLabel("Monto:"));
        content.add(montoField);

        JButton pagarBtn = createStyledButton("Realizar Pago", new Color(40, 167, 69));
        pagarBtn.addActionListener(e -> {
            try {
                BigDecimal monto = new BigDecimal(montoField.getText());
                MetodoPago metodo = (MetodoPago) metodoCombo.getSelectedItem();

                int reservaId = (int) reservasTable.getValueAt(selectedRow, 0);
                Pago pago = new Pago(monto, LocalDateTime.now(), metodo, reservaId);
                new PagoDAO().insertarPago(pago);

                reservaDAO.actualizarEstadoReserva(reservaId, EstadoReserva.Confirmada);
                loadData();
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error en los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        content.add(pagarBtn);
        dialog.add(content);
        dialog.setVisible(true);
    }

    private void cambiarEstadoReserva(EstadoReserva estado) {
        int selectedRow = reservasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva primero", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int reservaId = (int) reservasTable.getValueAt(selectedRow, 0);
        try {
            reservaDAO.actualizarEstadoReserva(reservaId, estado);
            if (estado == EstadoReserva.Cancelada) {
                int lugarId = (int) reservasTable.getValueAt(selectedRow, 2);
                lugarDAO.actualizarDisponibilidad(lugarId, true);
            }
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() throws SQLException {
        loadEventos();
        loadLugares();
        loadReservas();
    }

    private void loadEventos() {
        DefaultTableModel model = (DefaultTableModel) eventosTable.getModel();
        model.setRowCount(0);

        List<Evento> eventos = eventoDAO.listarEventos();
        for (Evento evento : eventos) {
            model.addRow(new Object[]{
                evento.getIdEvento(),
                evento.getNombreEvento(),
                evento.getFecha(),
                evento.getTipoEvento(),
                evento.getDuracion()
            });
        }
    }

    private void loadLugares() {
        DefaultTableModel model = (DefaultTableModel) lugaresTable.getModel();
        model.setRowCount(0);

        try {
            List<Lugar> lugares = lugarDAO.listarTodos();
            for (Lugar lugar : lugares) {
                model.addRow(new Object[]{
                    lugar.getIdLugar(),
                    lugar.getNombre(),
                    lugar.isDisponibilidad() ? "DISPONIBLE" : "OCUPADO",
                    lugar.getCapacidad()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error cargando lugares: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReservas() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) reservasTable.getModel();
        model.setRowCount(0);

        List<Reserva> reservas = reservaDAO.listarTodasLasReservas();
        for (Reserva reserva : reservas) {
            model.addRow(new Object[]{
                reserva.getIdReserva(),
                reserva.getIdEvento(),
                reserva.getIdLugar(),
                reserva.getFecha(),
                reserva.getEstado()
            });
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setUI(new BasicButtonUI());
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            GestionEventos gui = null;
            try {
                gui = new GestionEventos();
            } catch (SQLException ex) {
                Logger.getLogger(GestionEventos.class.getName()).log(Level.SEVERE, null, ex);
            }
            gui.setVisible(true);
        });
    }
}

class CustomTabbedPaneUI extends javax.swing.plaf.metal.MetalTabbedPaneUI {

    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
        if (isSelected) {
            g.setColor(new Color(67, 130, 232));
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, w, h);
    }
}

class CustomTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        c.setBackground(Color.WHITE);
        c.setForeground(new Color(42, 53, 81));

        if (isSelected) {
            c.setBackground(new Color(67, 130, 232, 50));
        }

        if (table.getColumnName(column).equals("Estado") && value != null) {
            String estado = value.toString();
            switch (estado) {
                case "Confirmada":
                    c.setForeground(new Color(40, 167, 69));
                    break;
                case "Cancelada":
                    c.setForeground(new Color(220, 53, 69));
                    break;
                case "Pendiente":
                    c.setForeground(new Color(255, 193, 7));
                    break;
            }
        }

        if (table.getColumnName(column).equals("Disponibilidad") && value != null) {
            String disp = value.toString();
            if (disp.equals("DISPONIBLE")) {
                c.setForeground(new Color(40, 167, 69));
            } else {
                c.setForeground(new Color(220, 53, 69));
            }
        }

        return c;
    }
}
