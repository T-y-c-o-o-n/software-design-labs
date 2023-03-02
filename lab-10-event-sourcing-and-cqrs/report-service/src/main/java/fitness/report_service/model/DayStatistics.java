package fitness.report_service.model;

import org.postgresql.util.PGInterval;

import java.sql.Date;

public class DayStatistics {
    private Date date;
    private int averageDuration;
    private int visitCount;

    public DayStatistics() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(int averageDuration) {
        this.averageDuration = averageDuration;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
