package repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableLoader {

    private Connection getConnection() throws SQLException {
        // Implement database connection logic
        return DriverManager.getConnection("jdbc:sqlite:your-database-file.db");
    }

    public <T> List<T> loadTable(Class<T> clazz, String tableName) {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                T instance = clazz.getDeclaredConstructor().newInstance();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(instance, value);
                }
                list.add(instance);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Replace with better error handling
        }
        return list;
    }
}
