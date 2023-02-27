package fitness.manager_admin.dao;

import fitness.manager_admin.model.Client;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ClientDao extends JdbcDaoSupport {

    public ClientDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public Optional<Client> getClient(int clientId) {
        String sql = "SELECT client_id, name FROM Clients WHERE client_id = ?";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class), clientId).stream().findFirst();
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
