package step3;

import org.mybatis.domain.Shop;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Executor {
    public static void main(String[] args) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(1);
        parameters.add("운영중");
        Application application = new Application();
        try {
            Shop shop = application.view(parameters);
            System.out.println(shop);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
