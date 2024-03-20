package step1;

import java.sql.*;

public class SqlMapper {

    private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=mybatis;trustServerCertificate=true;";
    private static final String username = "sunghoonleee7";
    private static final String password = "qkqxld12!@";
    protected Connection connection;
    protected PreparedStatement ps;
    protected ResultSet rs;

    public SqlMapper() {
        try {
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    protected Connection connect() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url,username,password);
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
