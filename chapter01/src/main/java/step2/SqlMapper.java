package step2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SqlMapper {
    private Properties properties;
    protected Connection connection;
    protected PreparedStatement ps;
    protected ResultSet rs;


    public SqlMapper() {
        try {
            properties = new Properties();
            ClassLoader  classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("jdbc/config-jdbc.properties");
            properties.load(inputStream);
            inputStream.close();
            Class.forName(properties.getProperty("driver"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected Connection connect() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("username"),properties.getProperty("password"));
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
}
