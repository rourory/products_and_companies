package main.java.view_layer.form;

import main.java.view_layer.model.CompanyListModel;

import javax.swing.*;
import java.awt.event.*;

public class DeleteCompanyDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private CompanyListModel model;
    private JList<?> list;

    /**
     * Диалог, требующий подтверждения действия по удалению компании из БД
     *
     * @param model модель, с которой работает даннный диалог
     * @param list  форма (список), использующая вышеупомянутую модель
     */
    public DeleteCompanyDialog(CompanyListModel model, JList<?> list) {
        this.model = model;
        this.list = list;
        setLocation(500, 500);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        defineButtonListeners();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Вызвать метод onCancel() закрытии диалога путем нажатия на "крестик"
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

    /**
     * Действия по подтверждунию выбора
     */
    private void onOK() {
        boolean removed = model.deleteCompany(list.getSelectedIndex());
        JOptionPane.showMessageDialog(this, removed ? "Компания успешно удален" : "Ошибка при удалении компании");
        dispose();
    }

    /**
     * Действия по отмене выбора.
     */
    private void onCancel() {
        dispose();
    }
}
