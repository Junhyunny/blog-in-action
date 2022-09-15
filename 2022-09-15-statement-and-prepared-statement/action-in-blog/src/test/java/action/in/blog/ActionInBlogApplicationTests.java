package action.in.blog;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;

@Log4j2
@SpringBootTest(properties = {
        "spring.sql.init.mode=embedded",
        "spring.sql.init.schema-locations=classpath:db/schema.sql",
        "spring.datasource.url=jdbc:h2:mem:test",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password="
})
public class ActionInBlogApplicationTests {

    Connection getConnection() {
        String url = "jdbc:h2:mem:test";
        String id = "sa";
        String pw = "";
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(url, id, pw);
            connection.setAutoCommit(false);
            return connection;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    void insertSamples(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into country (name) values (?)")) {
            for (int index = 0; index < 15000; index++) {
                preparedStatement.setString(1, "countryName-" + index);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    void select_15000_times_with_statement() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            insertSamples(connection);

            long startTime = System.currentTimeMillis();

            for (int index = 0; index < 15000; index++) {
                statement.execute("select name from country where name = 'countryName-" + index + "'");
            }

            long endTime = System.currentTimeMillis();
            log.info("{} milli seconds", (endTime - startTime));

            connection.rollback();
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    void select_15000_times_with_prepared_statement() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("select name from country where name = ?")) {
            insertSamples(connection);

            long startTime = System.currentTimeMillis();

            for (int index = 0; index < 15000; index++) {
                preparedStatement.setString(1, "countryName-" + index);
                preparedStatement.execute();
            }

            long endTime = System.currentTimeMillis();
            log.info("{} milli seconds", (endTime - startTime));

            connection.rollback();
        } catch (Exception e) {
            log.error(e);
        }
    }

    void printCountOfRows(Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("select count(*) as cnt from country")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                log.info("total count: {}", resultSet.getInt("cnt"));
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    void sql_injection_when_using_statement() {
        String parameter = "''); delete from country; --";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            insertSamples(connection);
            printCountOfRows(connection);

            statement.execute("insert into country (name) values (" + parameter + ")");

            printCountOfRows(connection);
            connection.rollback();
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    void defend_sql_injection_when_using_prepared_statement() {
        String parameter = "''); delete from country; --";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("insert into country (name) values (?)")) {
            insertSamples(connection);
            printCountOfRows(connection);

            preparedStatement.setString(1, parameter);
            preparedStatement.execute();

            printCountOfRows(connection);
            connection.rollback();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
