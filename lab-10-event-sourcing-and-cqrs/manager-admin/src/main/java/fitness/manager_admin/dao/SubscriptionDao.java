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

    public List<Subscription> listSubscriptionsByClientId(int clientId) {
        String sql = """
                SELECT subscription_id, max(until) as until
                FROM Subscriptions
                natural left join SubscriptionEvents
                where client_id = ?
                GROUP BY subscription_id
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        List<Subscription> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subscription.class), clientId);
        return query;
    }

    public int addSubscription(int clientId, int days) {
        String sql = "INSERT INTO subscriptions (client_id) VALUES (?)";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        jdbcTemplate.update(sql, clientId);

        sql = """
                INSERT INTO subscriptionevents (subscription_id, extension_of_existed, time, until)
                VALUES (
                    (SELECT subscriptions.subscription_id FROM subscriptions WHERE client_id = ? ORDER BY subscription_id DESC LIMIT 1),
                    false,
                    now(),
                    (SELECT CURRENT_DATE + ?)
                )
            """;
        jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, clientId, days);
    }

    public int extendSubscription(int subscriptionId, int days) {
        String sql = """
                INSERT INTO subscriptionevents (subscription_id, extension_of_existed, time, until)
                VALUES (
                    ?,
                    true,
                    now(),
                    ((SELECT max(until) FROM subscriptionevents WHERE subscription_id = ?) + ?)
                )
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.update(sql, subscriptionId, subscriptionId, days);
    }
}
