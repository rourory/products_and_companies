package main.java.data_access_layer.repository;

import main.java.data_access_layer.connection.ConnectionManager;
import main.java.data_access_layer.entity.Entity;
import main.java.util.CaseConverter;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Абстрактный класс позволяет нам реализовать часть повторяющегося функционала и определить обязательные к реализации методы
 *
 * @param <T> тип возвращаемой сущности
 */
public abstract class AbstractRepository<T extends Entity> {

    /**
     * Возвращает все записи из указанной таблицы
     *
     * @param tableName имя таблицы
     */
    protected List<T> findAll(String tableName) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(QueryTemplates.FIND_ALL_QUERY.replaceFirst("TABLE_NAME", tableName))) {
            ResultSet rs = ps.executeQuery();
            List<T> entities = new ArrayList<>();
            while (rs.next()) {
                entities.add(build(rs));
            }
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает записи по заданному параметру. К примеру, по сompany_id = ?
     *
     * @param parameterName имя параметра, по которому будет делаться выборка
     * @param id            значение параметра, по которому будет делаться выборка
     * @param tableName     имя таблицы
     */
    protected List<T> findAllWhereParameterID(String parameterName, Long id, String tableName) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(QueryTemplates.FIND_ALL_WHERE_QUERY.replaceFirst("TABLE_NAME", tableName).replaceFirst("PARAMETER", parameterName))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<T> entities = new ArrayList<>();
            while (rs.next()) {
                entities.add(build(rs));
            }
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает запись из таблицы с укзанным идентификатором
     *
     * @param id        идентификатор записи
     * @param tableName имя таблицы
     */
    protected Optional<T> findOneById(Long id, String tableName) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryTemplates.FIND_ONE_QUERY.replaceFirst("TABLE_NAME", tableName))) {
            preparedStatement.setLong(1, (Long) id);
            ResultSet rs = preparedStatement.executeQuery();
            Optional<T> entity = Optional.empty();
            while (rs.next()) {
                entity = Optional.of(build(rs));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаляет запись из БД с указанным идетификатором
     *
     * @param id идентификатор удаляемой записи
     * @return результат операции
     */
    protected Boolean removeById(Long id, String tableName) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryTemplates.REMOVE_QUERY.replaceFirst("TABLE_NAME", tableName))) {
            preparedStatement.setLong(1, (Long) id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохраняет запись в БД
     * Используется Java Reflection
     *
     * @param entity    сохраняемая запись
     * @param tableName имя таблицы
     * @return сохраненная запись
     */
    protected T save(T entity, String tableName) {
        // Перед выполнением запроса необходимо сформировать его из шаблона запросов QueryTemplates
        StringBuilder parametersAsQuestionMarks = new StringBuilder();
        StringBuilder fieldsAsString = new StringBuilder();
        // Поэтому получаем все поля обновляемой сущности
        Field[] fields = entity.getClass().getDeclaredFields();
        // И для каждого из них определяем параметры и значения для запроса
        for (int i = 0; fields.length > i; i++) {
            if (i == fields.length - 1) {
                parametersAsQuestionMarks.append("?");
                fieldsAsString.append("").append(CaseConverter.convertToSnakeCase(fields[i].getName()));
            } else {
                parametersAsQuestionMarks.append("?, ");
                fieldsAsString.append(CaseConverter.convertToSnakeCase(fields[i].getName())).append(", ");
            }
        }
        String query = QueryTemplates.SAVE_QUERY
                .replaceFirst("TABLE_NAME", tableName)
                .replaceFirst("FIELDS", fieldsAsString.toString())
                .replaceFirst("PARAMETERS", parametersAsQuestionMarks.toString());
        return executeUpdate(entity, query, false);
    }

    /**
     * Обновляет запись в БД
     *
     * @param entity обновляемая запись
     * @return обновленная запись
     */
    protected T update(T entity, String tableName) {
        // Перед выполнением запроса необходимо сформировать его из шаблона запросов QueryTemplates
        StringBuilder parametersWithQuestionMarks = new StringBuilder();
        // Поэтому получаем все поля обновляемой сущности
        Field[] fields = entity.getClass().getDeclaredFields();
        // И для каждого из них определяем параметры для запроса
        for (int i = 0; fields.length > i; i++) {
            parametersWithQuestionMarks.append(CaseConverter.convertToSnakeCase(fields[i].getName()));
            if (i == fields.length - 1) parametersWithQuestionMarks.append(" = ?");
            else parametersWithQuestionMarks.append(" = ?, ");
        }
        // Здесь формируется сам запрос из шаблона
        String query = QueryTemplates.UPDATE_SQL.replaceFirst("TABLE_NAME", tableName).replaceFirst("PARAMETERS", parametersWithQuestionMarks.toString());
        return executeUpdate(entity, query, true);
    }

    /**
     * Строит объект T из ResultSet
     */
    protected abstract T build(ResultSet resultSet) throws SQLException;

    /**
     * Выполняет обновления в базе данных. Этот метод нельзя переопределить.
     * Он необходим для таких методов этотго класса, как {@code save()} и {@code update()}
     *
     * @param entity обновляемая сущность
     * @param query  выполняемый запрос
     * @return обновленный объект
     */
    private T executeUpdate(T entity, String query, Boolean updateOperation) {
        int i;
        // Тут создается подключение к БД и объект PreparedStatement выполнения запросов в рамках покдлючения
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Получаем все поля обновляемой сущности
            Field[] fields = entity.getClass().getDeclaredFields();
            // Значение каждого поля станавливаем в preparedStatement. Все с помощью java reflection
            for (i = 0; fields.length > i; i++) {
                fields[i].setAccessible(true);
                preparedStatement.setObject(i + 1, fields[i].get(entity));
            }
            // Если опрерация является операцией обновления, то нужно установить значение для параметра id
            if (updateOperation) preparedStatement.setObject(i + 1, entity.getId());
            // Выполняем обновление в БД и возвращаем обновленный объект.
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) return build(generatedKeys);
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
}