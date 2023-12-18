package main.java.view_layer.model.cell_renderer;

import main.java.data_access_layer.dto.CategoryDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CategoryCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        CategoryDTO category = (CategoryDTO) value;
        return new JLabel(category.getCategoryName());
    }
}
