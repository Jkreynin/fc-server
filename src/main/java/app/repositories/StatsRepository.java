package app.repositories;

import app.models.Stats;

public interface StatsRepository {
    Stats getStats(String start_date);
}
