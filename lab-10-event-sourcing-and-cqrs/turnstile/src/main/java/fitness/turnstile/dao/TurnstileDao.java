package fitness.turnstile.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

public class TurnstileDao extends JdbcDaoSupport {

    public TurnstileDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public boolean in(int subscriptionId) {
        String sql = """
                INSERT INTO TurnstileEvents (in_, subscription_id, time)
                SELECT DISTINCT true as in_, subscription_id, now() as time
                FROM Subscriptions
                natural join SubscriptionEvents
                WHERE subscription_id = ?
                and until > (SELECT CURRENT_DATE);
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        int result = jdbcTemplate.update(sql, subscriptionId);
        return result > 0;
    }

    public boolean out(int subscriptionId) {
        // Пускаем на выход, если последнее событие было вход
        // Если какие-то сбои или кто-то перелез через турникет - пусть охрана разбирается
        String sql = """
                INSERT INTO TurnstileEvents (in_, subscription_id, time, previous_time)
                SELECT DISTINCT false as in_, subscription_id, now() as time, time as previous_time
                FROM TurnstileEvents t1
                WHERE subscription_id = ?
                AND in_ = true
                AND time = (SELECT max(time) FROM TurnstileEvents t2 WHERE t1.subscription_id = t2.subscription_id);
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        int result = jdbcTemplate.update(sql, subscriptionId);
        return result > 0;
    }
}
