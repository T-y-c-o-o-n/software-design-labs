package fitness.manager_admin.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

public class ClientDao extends JdbcDaoSupport {

    public ClientDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public int addClient(String clientName) {
        String sql = "INSERT INTO Clients (name) VALUES (?)";
        return getJdbcTemplate().update(sql, clientName);
    }

}
