package main.java.view_layer.form;

import main.java.view_layer.model.ProductTableModel;

import javax.swing.*;
import java.awt.event.*;

public class DeleteProductDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table;
    private ProductTableModel model;

    /**
     * Диалог, требующий подтверждения действия по удалению продукта из БД
     *
     * @param model модель, с которой работает даннный диалог
     * @param table форма (таблица), использующая вышеупомянутую модель
     */
    public DeleteProductDialog(JTable table, ProductTableModel model) {
        this.table = table;
        this.model = model;

        setLocation(500, 500);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        defineButtonListeners();
        // Вызвать метод onCancel() закрытии диалога путем нажатия на "крестик"
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // Вызвать onCancel() по нажатию на кнопку ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.pack();
        this.setVisible(true);
    }

    private void defineButtonListeners() {
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
    }

    private void onOK() {
        boolean removed = model.deleteProduct(table.getSelectedRow());
        JOptionPane.showMessageDialog(this, removed ? "Продукт успешно удален" : "Ошибка при удалении продукта");
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
