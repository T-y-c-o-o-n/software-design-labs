package fitness.report_service.dao;

import fitness.report_service.model.DayStatistics;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public class StatisticsDao extends JdbcDaoSupport {
    public StatisticsDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public List<DayStatistics> dayStatistics(int lastDays) {
        String sql = """
                SELECT date(time) as Date, COUNT(*) as VisitCount, AVG(extract (second from (time - previous_time))) as AverageDuration
                FROM TurnstileEvents
                WHERE date(time) + ? > (SELECT CURRENT_DATE)
                AND not in_
                GROUP BY date(time);
            """;
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate is null");
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DayStatistics.class), lastDays);
    }
}
