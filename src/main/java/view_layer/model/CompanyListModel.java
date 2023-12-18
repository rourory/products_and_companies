package main.java.view_layer.model;

import main.java.data_access_layer.dto.CompanyDTO;
import main.java.service_layer.service.CompanyService;

import javax.swing.*;
import java.util.List;

/**
 * Модель списка компаний. Тут описываются методы для работы с моделью, т.е. с самим списком компаний.
 */
public class CompanyListModel extends AbstractListModel<CompanyDTO> {

    private final CompanyService companyService;
    private List<CompanyDTO> companies;

    public CompanyListModel() {
        this.companyService = new CompanyService();
        //При создании класса список компаний сразу инициализируется списком из базы данных.
        this.companies = companyService.findAll();
    }

    @Override
    public int getSize() {
        return companies.size();
    }

    @Override
    public CompanyDTO getElementAt(int index) {
        return companies.get(index);
    }

    public void addCompany(CompanyDTO company) {
        //Сохраняем элемент в БД
        CompanyDTO saved = companyService.save(company);
        //Добавляем его же список
        companies.add(saved);
        //Метод суперкласса, который мы вызываем при добавлении в список нового элемента. Смотри документацию метода.
        fireIntervalAdded(CompanyListModel.this, 0, companies.size());
    }

    public void updateCompany(CompanyDTO company, int rowIndex) {
        //Меняем элемента списка на обновленный элемент
        companies.set(rowIndex, company);
        //Обновляем его же и в БД
        companyService.update(company);
        //Метод суперкласса, который мы вызываем при добавлении в список нового элемента. Смотри документацию метода.
        fireIntervalAdded(CompanyListModel.this, 0, 0);
    }

    public boolean deleteCompany(int rowIndex) {
        //Удаляем объект из БД
        Boolean removed = companyService.removeById(companies.get(rowIndex).getId());
        //Убираем его же из списка
        if (removed) {
            companies.remove(rowIndex);
            //Метод суперкласса, вызываемый при удалении элементов из списка. Смотри документацию метода.
            fireIntervalAdded(CompanyListModel.this, 0, companies.size());
        }
        return removed;
    }

    public void refresh() {
        companies = companyService.findAll();
    }
}
