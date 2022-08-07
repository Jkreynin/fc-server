package app.repositories;

import app.models.Stats;
import app.models.WeightByDay;

import java.util.List;

public interface StatsRepository {
    Stats getStats(String start_date);

    List<WeightByDay> getWeightByDay(String startDate, String end_date, String graph_type);
}
