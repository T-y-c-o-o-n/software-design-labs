package fitness.manager_admin.dao;

import fitness.manager_admin.model.Subscription;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public class SubscriptionDao extends JdbcDaoSupport {

    public SubscriptionDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public int addSubscription(int clientId) {
        String sql = "INSERT INTO subscriptions (client_id) VALUES (?)";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, new BeanPropertyRowMapper<>(Subscription.class), clientId);
    }

    public List<Subscription> listSubscriptionsByClientId(int clientId) {
        String sql = """
            SELECT subscription_id, max(until)
            FROM Subscriptions
            natural left join SubscriptionEvents
            where client_id = ?
            GROUP BY subscription_id
        """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subscription.class), clientId);
    }
}
