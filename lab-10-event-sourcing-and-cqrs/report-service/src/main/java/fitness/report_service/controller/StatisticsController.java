package fitness.report_service.controller;

import fitness.report_service.dao.StatisticsDao;
import fitness.report_service.model.DayStatistics;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report-service")
public class StatisticsController {

    private final StatisticsDao statisticsDao;

    public StatisticsController(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    @RequestMapping(value = "/stat-by-last-days", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DayStatistics> statisticsByLastDays(@RequestParam int lastDays) {
        return statisticsDao.dayStatistics(lastDays);
    }
}
