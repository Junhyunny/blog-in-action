package action.in.blog;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Log4j2
@SpringBootTest(properties = {
        "spring.sql.init.mode=always",
        "spring.sql.init.schema-locations=classpath:db/schema-mysql.sql",
        "spring.datasource.url=jdbc:mysql://localhost:3306/mysql",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.username=root",
        "spring.datasource.password=123"
})
public class MySqlDatabaseTests {

    // please run mysql-server.sh before testing

    Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/mysql";
        String id = "root";
        String pw = "123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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
            for (int index = 0; index < 10000; index++) {
                preparedStatement.setString(1, "countryName-" + index);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    void select_10000_times_with_statement() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            insertSamples(connection);

            long startTime = System.currentTimeMillis();

            for (int index = 0; index < 10000; index++) {
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
    void select_10000_times_with_prepared_statement() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("select name from country where name = ?")) {
            insertSamples(connection);

            long startTime = System.currentTimeMillis();

            for (int index = 0; index < 10000; index++) {
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

    int getCountOfRows(Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("select count(*) as cnt from country")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cnt");
            }
            return 0;
        } catch (Exception e) {
            log.error(e);
        }
        return -1;
    }

    @Test
    void sql_injection_when_using_statement() {
        String parameter = "''); delete from country; --";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            insertSamples(connection);
            int beforeCount = getCountOfRows(connection);

            statement.execute("insert into country (name) values (" + parameter + ")");

            int afterCount = getCountOfRows(connection);

            assertThat(beforeCount, equalTo(10000));
            assertThat(afterCount, equalTo(0));

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
            int beforeCount = getCountOfRows(connection);

            preparedStatement.setString(1, parameter);
            preparedStatement.execute();

            int afterCount = getCountOfRows(connection);

            assertThat(beforeCount, equalTo(10000));
            assertThat(afterCount, equalTo(10001));

            connection.rollback();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
