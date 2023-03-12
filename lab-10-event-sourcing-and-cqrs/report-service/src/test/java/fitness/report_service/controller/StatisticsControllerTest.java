package fitness.report_service.controller;

import fitness.report_service.dao.StatisticsDao;
import fitness.report_service.model.DayStatistics;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticsControllerTest {

    @Mock
    private StatisticsDao statisticsDao;

    private StatisticsController statisticsController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        statisticsController = new StatisticsController(statisticsDao);
    }

    @Test
    public void dayStatisticsTest() {
        int lastDays = 1;
        DayStatistics expectedStatistics = new DayStatistics();
        expectedStatistics.setDate(Date.valueOf(LocalDate.now()));
        expectedStatistics.setVisitCount(1);
        expectedStatistics.setAverageDuration(6);
        List<DayStatistics> expectedStatisticsList = List.of(expectedStatistics);
        when(statisticsDao.dayStatistics(lastDays))
            .thenReturn(expectedStatisticsList);
        List<DayStatistics> actualStatistics = statisticsController.statisticsByLastDays(lastDays);
        assertEquals(expectedStatisticsList, actualStatistics);
    }

}
