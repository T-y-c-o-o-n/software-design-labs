package fitness.turnstile.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

public class TurnstileDao extends JdbcDaoSupport {

    public TurnstileDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public boolean in(int clientId) {
        String sql = """
                INSERT TurnstileEvents (in_, subscription_id, time)
                SELECT true as in_, subscription_id, now() as time
                FROM Subscription
                natural join SubscriptionEvents
                WHERE client_id = ?
                and until > (SELECT CURRENT_DATE);
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        int result = jdbcTemplate.update(sql, clientId);
        return result > 0;
    }

}
