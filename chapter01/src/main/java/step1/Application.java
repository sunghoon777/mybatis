package step1;

import org.mybatis.domain.Shop;

import java.sql.*;
import java.util.List;

public class Application extends SqlMapper{
    public Shop view(List<Object> parameters) throws SQLException {
        Shop shop = new Shop();
        try {
            connection = connect();
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
            release();
        }
        return shop;
    }
}
