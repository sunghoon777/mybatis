package step3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SqlMapper {
    private Properties configuration;
    private Properties sql;
    protected Connection connection;
    protected PreparedStatement ps;
    protected ResultSet rs;


    public SqlMapper() {
        try {
            configAsProperties();
            sqlAsProperties();
            Class.forName(configuration.getProperty("driver"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void configAsProperties() throws IOException {
        configuration = new Properties();
        ClassLoader  classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("jdbc/config-jdbc.properties");
        configuration.load(inputStream);
        inputStream.close();
    }

    private void sqlAsProperties() throws IOException {
        sql = new Properties();
        ClassLoader  classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("jdbc/sql.properties");
        sql.load(inputStream);
        inputStream.close();
    }


    protected Connection connect() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(configuration.getProperty("url"),configuration.getProperty("username"),configuration.getProperty("password"));
        }
        return connection;
    }

    protected void release(){
        try {
            if(rs != null){
                rs.close();
            }
            if(ps != null){
                ps.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch (SQLException e){

        }
    }

    protected String sqlById(String sqlId){
        return sql.getProperty(sqlId);
    }
}
