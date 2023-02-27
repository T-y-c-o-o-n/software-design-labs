package fitness.manager_admin.dao;

import fitness.manager_admin.model.Client;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

public class ClientDao extends JdbcDaoSupport {

    public ClientDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public List<Client> listClients() {
        String sql = "SELECT client_id, name FROM Clients";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class));
    }

    public int addClient(String clientName) {
        String sql = "INSERT INTO Clients (name) VALUES (?)";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, clientName);
    }
}
