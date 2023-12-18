package main.java.view_layer.model.listener;

import main.java.data_access_layer.dto.ProductDTO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

public class ProductTableCellEditorListener implements javax.swing.event.CellEditorListener {

    private final JTable ownerTable;
    private final AbstractTableModel tableModel;

    public ProductTableCellEditorListener(JTable ownerTable, AbstractTableModel tableModel) {
        this.ownerTable = ownerTable;
        this.tableModel = tableModel;
    }

    @Override
    public void editingStopped(ChangeEvent e) {

    }

    @Override
    public void editingCanceled(ChangeEvent e) {

    }
}
