package step5;

import org.mybatis.domain.Shop;

import java.sql.SQLException;
import java.util.List;

public class Application extends SqlMapper {
    public Shop view(List<Object> parameters) throws SQLException {
        Shop shop = new Shop();
        try {
            connection = connect();
            String sql =  parameterBindingByList("select", parameters);
            ps = connection.prepareStatement(sql);
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
