package com.algaworks.algafood.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseCleaner implements InitializingBean {

    private List<String> tableNames = new ArrayList<>();

    final String TRUNCATE_TABLES_STATEMENT = "TRUNCATE TABLE ";
    final String DISABLE_CONSTRAINTS_STATEMENT = "SET FOREIGN_KEY_CHECKS = 0";
    final String ENABLE_CONSTRAINTS_STATEMENT = "SET FOREIGN_KEY_CHECKS = 1";

    @Autowired
    private DataSource dataSource;

    final Logger logger = LoggerFactory.getLogger(getClass());

    public void run() throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.addBatch(DISABLE_CONSTRAINTS_STATEMENT);
        tableNames.forEach(tableName -> {
            try {
                logger.info(tableName);
                statement.addBatch(TRUNCATE_TABLES_STATEMENT + tableName);
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
        });
        statement.addBatch(ENABLE_CONSTRAINTS_STATEMENT);
        statement.executeBatch();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(connection.getCatalog(), null, null,
                new String [] { "TABLE" });
        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
        }
        tableNames.remove("flyway_schema_history");
    }
}