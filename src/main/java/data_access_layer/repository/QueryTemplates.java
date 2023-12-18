package main.java.data_access_layer.repository;

public class QueryTemplates {
    public static final String FIND_ALL_QUERY = """
            SELECT *
            FROM TABLE_NAME
            """;

    public static final String FIND_ALL_WHERE_QUERY = """
            SELECT *
            FROM TABLE_NAME
            WHERE PARAMETER = ?
            """;

    public static final String FIND_ONE_QUERY = """
            SELECT *
            FROM TABLE_NAME
            WHERE id = ?
            """;

    public static final String REMOVE_QUERY = """
            DELETE
            FROM TABLE_NAME
            WHERE id = ?
            """;

    public static final String SAVE_QUERY = """
            INSERT INTO TABLE_NAME (FIELDS)
            VALUES (PARAMETERS)
            """;

    public static final String UPDATE_SQL = """
            UPDATE TABLE_NAME
            SET PARAMETERS
            WHERE id = ?
            """;
}
