package app.repositories;

import app.models.Stats;
import app.models.WeightByDay;

import java.util.List;

public interface StatsRepository {
    Stats getStats(String start_date);

    List<WeightByDay> getWeightByDay(String start_date, String end_date);
}
