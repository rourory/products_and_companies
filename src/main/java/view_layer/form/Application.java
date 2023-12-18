package main.java.view_layer.form;

import main.java.data_access_layer.dto.CompanyDTO;
import main.java.view_layer.model.CompanyListModel;
import main.java.view_layer.model.ProductTableModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Application extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JScrollPane companyListPanel;
    private JList companyList;
    private JLabel companyLabel;
    private JLabel productLabel;
    private JButton addCompanyButton;
    private JButton addProductButton;
    private JButton deleteCompanyButton;
    private JButton deleteProductButton;
    private JButton changeCompanyButton;
    private JTable productsTable;
    private JScrollPane tableScroller;

    private CompanyListModel companyListModel;
    private ProductTableModel productsTableModel;

    /**
     * Главная форма приложения
     */
    public Application() {
        super("Продукты и компании");
        setContentPane(mainPanel);
        setLocation(300, 150);
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setCompanyListModel();
        setProductTableModel();
        addListListeners();
        addTableListeners();
        setButtonsInitialState();
        addButtonsListeners();
        setVisible(true);
    }

    /**
     * Тут мы добавляем обработку события "щелчок мышью на список"
     * Сам список (CompanyListModel) вызывает метод mouseClicker() каждый раз, когда совершается щелчок мыши по нему.
     * Т.е. мы создаем анонимный класс из функционального интерфеса MouseAdapter. У него единственный метод, мы его сразу описываем.
     * Объект этого класса передается методу addMouseListener, вызываемого у companyList. Таких объектов можно передать сколько угодно.
     * Он положит все эти объекты к себе в список и будет вызывать циклом по очереди каждый раз, когда будет совершаться зелчок мышью.
     * Смотрите паттерн "Слушатель"
     */
    private void addListListeners() {
        companyList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    deleteCompanyButton.setEnabled(!companyList.isSelectionEmpty());
                    changeCompanyButton.setEnabled(!companyList.isSelectionEmpty());
                }
                //Если щелчок был двойной, то делаем следующее.
                if (e.getClickCount() == 2) {
                    deleteProductButton.setEnabled(false);
                    addProductButton.setEnabled(true);
                    int indexOfCompany = companyList.locationToIndex(e.getPoint());
                    CompanyDTO company = companyListModel.getElementAt(indexOfCompany);
                    productsTableModel.loadProducts(company.getId());
                }
            }
        });
    }

    private void addTableListeners() {
        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteProductButton.setEnabled(productsTable.getSelectedRow() >= 0);
            }
        });
    }

    private void addButtonsListeners() {
        deleteCompanyButton.addActionListener(e -> new DeleteCompanyDialog(companyListModel, companyList));
        deleteProductButton.addActionListener(e -> new DeleteProductDialog(productsTable, productsTableModel));
        addCompanyButton.addActionListener(e -> new AddCompanyForm(companyListModel));
        changeCompanyButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Функциональность не реализована"));
        addProductButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Функциональность не реализована"));
    }

    private void setButtonsInitialState() {
        deleteCompanyButton.setEnabled(false);
        changeCompanyButton.setEnabled(false);
        addProductButton.setEnabled(false);
        deleteProductButton.setEnabled(false);
    }

    private void setCompanyListModel() {
        companyListModel = new CompanyListModel();
        companyList.setModel(companyListModel);
    }

    private void setProductTableModel() {
        productsTableModel = new ProductTableModel(productsTable);
        productsTable.setModel(productsTableModel);
    }
}