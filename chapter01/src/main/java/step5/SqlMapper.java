package step5;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

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

    protected String parameterBindingByList(String sqlId, List<Object> parameters){
        String query = sqlById(sqlId);
        String[] querySplit = query.split("[?]");
        StringBuilder replaceSql = new StringBuilder("");
        for(int i=0;i<parameters.size();i++){

            replaceSql.append(querySplit[i]);

            String value = (parameters.get(i) == null)?"":parameters.get(i).toString();

            boolean isNumber = Pattern.matches("[0-9]",value);
            if(isNumber){
                replaceSql.append(value);
            }else {
                replaceSql.append("'").append(value).append("'");
            }
        }
        return replaceSql.toString();
    }

    protected String parameterBindingByMap(String sqlId, Map<String,Object> parameters){
        String query = sqlById(sqlId);
        Iterator<String> it = parameters.keySet().iterator();;
        while (it.hasNext()){
            String key = it.next();
            String value = parameters.get(key)==null?"":parameters.get(key).toString();
            //#{shopNo}
            String findKey = "#{"+key+"}";
            query = query.replaceAll(findKey,value);
        }
        return query;
    }
}
