package main.java.view_layer.model;

import main.java.data_access_layer.dto.CategoryDTO;
import main.java.data_access_layer.dto.ProductDTO;
import main.java.service_layer.service.CategoryService;
import main.java.service_layer.service.ProductService;
import main.java.view_layer.model.cell_renderer.CategoryCellRenderer;
import main.java.view_layer.model.listener.ProductTableCellEditorListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {

    //Этот тот список, который фактически будет отображать таблица
    private List<ProductDTO> products;
    private final ProductService productService;
    private final CategoryService categoryService;

    //Таблица, управляющая моделью
    private JTable table;

    /**
     * В конструктор мы будем передавать таблицу, уоторая будет пользоваться этой моделью.
     * Этот такой интересный патерн, теперь мы можем вызывать методы на этой таблице изнутри модели
     */
    public ProductTableModel(JTable owner) {
        this.table = owner;
        this.productService = new ProductService();
        this.categoryService = new CategoryService();
        this.products = new ArrayList<>();
    }

    public ProductDTO getProduct(int rowIndex) {
        return products.get(rowIndex);
    }

    /**
     * Это наш метод, который будет использоваться при загрузке новых данных в таблицу
     */
    public void loadProducts(Long companyId) {
        products = productService.findAllByCompanyId(companyId);

        //Делаем кастомный редактор ячейки в виде выпадающего списка категорий продуктов
        DefaultCellEditor cellEditor = new DefaultCellEditor(new JComboBox<CategoryDTO>(categoryService.findAll().toArray(new CategoryDTO[0])));
        table.getColumnModel().getColumn(3).setCellEditor(cellEditor);
        //Делаем кастомную прорисовку для ячейки с категориями прокутов
        table.getColumnModel().getColumn(3).setCellRenderer(new CategoryCellRenderer());
        //Обрабатываем событие редактирования ячейки
        table.getDefaultEditor(Object.class).addCellEditorListener(new ProductTableCellEditorListener(table, this));

        fireTableRowsInserted(0, products.size());
    }

    public boolean deleteProduct(int rowIndex) {
        //Удаляем продукт из БД
        boolean removed = productService.removeById(products.get(rowIndex).getId());
        //Если из БД он удален, то удаляем его из списка
        if (removed) {
            products.remove(rowIndex);
            fireTableRowsDeleted(0, products.size());
        }
        return removed;
    }

    /**
     * Сколько таблице отрисовать строк? Столько, сколько элементов в списке products
     */
    @Override
    public int getRowCount() {
        return products.size();
    }

    /**
     * Сколько таблице отрисовать колонок? Тоже решаем мы.
     * Попробуй поставить здесь другое значение, если любопытно и увидишь, что таблица начнет отрисовывать другое колическо колонок
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Аналогичная ситуация с определением колчичества колонок и строк.
     * Только тут мы определяем названия колонок
     */
    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Название";
            case 1 -> "Цена";
            case 2 -> "Компания";
            case 3 -> "Категория";
            default -> super.getColumnName(column);
        };
    }

    /**
     * Метод, вызываемый каждый раз, когда необходимо получить значение из ячейки
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductDTO product = products.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> product.getProductName();
            case 1 -> product.getPrice();
            case 2 -> product.getCompany();
            case 3 -> product.getCategory();
            default -> null;
        };
    }

    /**
     * Этот метод вызывается каждый раз, когда пользователь хочет установить новое значение в ячейку.
     *
     * @param aValue      новое устанавливаемое значение
     * @param rowIndex    номер редактируемой строки
     * @param columnIndex номер редактируемой колонки
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        boolean anyErrors = false;
        ProductDTO product = products.get(rowIndex);
        switch (columnIndex) {
            case 0 -> product.setProductName(aValue.toString());
            case 1 -> {
                //Здесь происходит валидация вводимого значение в поле цены. Если цена не является числовым значением, выдаем ошибку.
                try {
                    product.setPrice(Double.parseDouble(aValue.toString()));
                } catch (NumberFormatException e) {
                    anyErrors = true;
                    JOptionPane.showMessageDialog(table, "Значение цены должно быть в виде числа");
                }
            }
            case 3 -> product.setCategory((CategoryDTO) aValue);
        }
        if (anyErrors)
            productService.update(product);
        //Метод, вызываемый у суперкласса для перерисовки таблицы
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * Не стоит забывать, что мы пишем МОДЕЛЬ, которую будет использовать какая то таблица.
     * То есть таблица будет использовать эту можель для отображения данных, следовательно будет использовать методы этой модели.
     * В абстрактном классе определен этот метод. Он просто позвращает false по умолчанию. Но мы его переопределяем.
     * Этот метод таблица использует каждый раз, когда пользователь пытается получить доступ к реадктированию ячейки.
     * Она вызывает этот метод и если он возвращает false, то доступ к редактированию закрыт.
     * Ячейка - это номер колоки с номером строки. Поэтому в аргуметах метода мы наблюдаем rowIndex и columnIndex.
     * В данном случае, если колона третья по счету (компания), то доступ к редактированию закрыт. В противном случае - редактировать можно.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 2;
    }


}
