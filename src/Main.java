import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("buah.properties"),
                    StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String buahName = "Tapestry";
        String query = "SELECT * FROM warna.jenis";

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try (var connection = dataSource.getConnection(
                props.getProperty("user"),
                props.getProperty("password")
        );
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.printf("%d %s %n", resultSet.getInt(1),resultSet.getString("buah_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}