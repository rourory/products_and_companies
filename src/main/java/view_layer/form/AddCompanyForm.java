package main.java.view_layer.form;

import main.java.data_access_layer.dto.CompanyDTO;
import main.java.data_access_layer.dto.CountryDTO;
import main.java.service_layer.service.CountryService;
import main.java.view_layer.model.CompanyListModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCompanyForm extends JFrame {
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel valuesPanel;
    private JPanel buttonsPanel;
    private JButton addButton;
    private JButton cancelButton;
    private JTextField textField;
    private JComboBox comboBox;
    private JLabel companyNameLabel;
    private JLabel countrylabel;
    private CompanyListModel model;
    private CountryService countryService;

    /**
     * Форма добавления новой компании в БД
     *
     * @param model модель, использующаяся данной формой
     */
    public AddCompanyForm(CompanyListModel model) {
        super("Добавить компанию");
        this.model = model;
        this.countryService = new CountryService();
        setComboBoxData();
        setContentPane(mainPanel);
        setLocation(300, 150);
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        defineButtonsListeners();
        setVisible(true);
    }

    /**
     * Метод, определяющий действия, необходимвые к выполнению в случае генерации события {@link java.awt.event.ActionEvent}
     */
    private void defineButtonsListeners() {
        // В данном случае мы наблюдаем лямбда-выражение. Им заменяют обяъвление анонимного класса из функционального интерфейса.
        // Функциональный интерфей - это интерфейс с лишь одним методом
        addButton.addActionListener(e -> {
            if (!textField.getText().equals("")) {
                CompanyDTO company = new CompanyDTO(null, textField.getText(), (CountryDTO) comboBox.getSelectedItem());
                model.addCompany(company);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Введите название компании");
            }
        });
        // Тут для примера создается анонимный класс. Его можно заменить лямбдой и записать в одну строчку
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Выпадающий список требует в себя каких-либо данных. Они загружатся в следующем методе.
    private void setComboBoxData() {
        comboBox.setModel(new DefaultComboBoxModel<CountryDTO>(countryService.findAll().toArray(new CountryDTO[0])));
    }
}
