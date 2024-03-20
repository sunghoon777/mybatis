package step0;

import org.mybatis.domain.Shop;

import java.sql.*;
import java.util.List;

public class Application {

    private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=mybatis;trustServerCertificate=true;";
    private static final String username = "sunghoonleee7";
    private static final String password = "qkqxld12!@";

    public Shop view(List<Object> parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Shop shop = new Shop();
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
            ps = connection.prepareStatement("SELECT * FROM SHOP WHERE SHOP_NO = ? AND SHOP_STATUS = ?");
            ps.setInt(1,(Integer)parameters.get(0));
            ps.setString(2,String.valueOf(parameters.get(1)));
            rs = ps.executeQuery();
            if(rs.next()){
                shop.setShopNo(rs.getInt("SHOP_NO"));
                shop.setShopLocation(rs.getString("SHOP_LOCATION"));
                shop.setShopName(rs.getString("SHOP_NAME"));
                shop.setShopStatus(rs.getString("SHOP_STATUS"));
            }
        } catch (Exception e) {
            throw new SQLException();
        }
        finally {
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
                throw e;
            }
        }
        return shop;
    }

}
