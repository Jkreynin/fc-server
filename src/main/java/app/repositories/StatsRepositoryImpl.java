package app.repositories;

import app.models.Stats;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatsRepositoryImpl implements StatsRepository {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Stats getStats(String start_date) {
        String rawSQL = "WITH today_avg_weight as (\n" +
                "                SELECT coalesce(AVG((data ->'weight')::double precision),0) today_avg_weight\n" +
                "                \tFROM core.entries\n" +
                "                \tWHERE type = 'Weight' AND start_time >= DATE_TRUNC('day',@date::timestamp)\n" +
                "                ),\n" +
                "                                \n" +
                "                today_total_calories as (\n" +
                "                SELECT coalesce(SUM((data ->'score')::double precision),0) today_total_calories\n" +
                "                \tFROM core.entries\n" +
                "                \tWHERE type = 'Meal' AND start_time >= DATE_TRUNC('day',@date::timestamp)),\n" +
                "                                \n" +
                "                this_week_total_calories as (\n" +
                "                SELECT coalesce(SUM((data ->'score')::double precision),0) this_week_total_calories\n" +
                "                \tFROM core.entries\n" +
                "                \tWHERE type ='Meal' and date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval <= start_time AND\n" +
                "                \tstart_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval + '1 week'::interval),\n" +
                "                                \n" +
                "                this_week_avg_weight as (\n" +
                "                SELECT coalesce(AVG((data ->'weight')::double precision),0) this_week_avg_weight\n" +
                "                \tFROM core.entries\n" +
                "                \tWHERE type = 'Weight' AND date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval <= start_time AND\n" +
                "                \tstart_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval + '1 week'::interval),\n" +
                "                \t\n" +
                "                \t\n" +
                "                last_week_avg_weight as (\n" +
                "                SELECT coalesce(AVG((data ->'weight')::double precision),0) last_week_avg_weight\n" +
                "                \tFROM core.entries\n" +
                "                \tWHERE type = 'Weight' AND date_trunc('week', DATE_TRUNC('day',@date::timestamp) - interval '1 week') - interval '1 day' <= start_time AND\n" +
                "                \tstart_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - interval '1 day' ),\n" +
                "                \t\n" +
                "                last_week_total_calories as (\n" +
                "                SELECT coalesce(SUM((data ->'score')::double precision),0) last_week_total_calories\n" +
                "                \tFROM core.entries\n" +
                "                \tWHERE type = 'Meal' AND date_trunc('week', DATE_TRUNC('day',@date::timestamp) - interval '1 week') - interval '1 day' <= start_time AND\n" +
                "                \tstart_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - interval '1 day' )\n" +
                "                \t\n" +
                "                \t\n" +
                "                SELECT today_avg_weight, today_total_calories, this_week_total_calories, this_week_avg_weight, last_week_avg_weight, last_week_total_calories from today_avg_weight CROSS JOIN\n" +
                "                today_total_calories CROSS JOIN\n" +
                "                this_week_total_calories CROSS JOIN\n" +
                "                this_week_avg_weight CROSS JOIN\n" +
                "                last_week_avg_weight CROSS JOIN\n" +
                "                last_week_total_calories            ";
        try (Session session = entityManager.unwrap(Session.class)) {

            return session.doReturningWork(connection -> {
                // do something useful
                try (PreparedStatement stmt = connection.prepareStatement(rawSQL.replace("@date", String.format("'%s'", start_date)))) {
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    return new Stats(
                            rs.getInt("today_total_calories"),
                            rs.getDouble("today_avg_weight"),
                            rs.getInt("this_week_total_calories"),
                            rs.getDouble("this_week_avg_weight"),
                            rs.getInt("last_week_total_calories"),
                            rs.getDouble("last_week_avg_weight")
                    );
                }
            });
        }

    }
}
