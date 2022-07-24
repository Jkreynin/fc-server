package app.repositories;

import app.models.Stats;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

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
        String rawSQL = """
                WITH today_avg_weight as (
                SELECT coalesce(AVG((data ->'value')::double precision),0) today_avg_weight
                	FROM core.entries
                	WHERE type = 'Weight' AND start_time >= DATE_TRUNC('day',@date::timestamp)
                ),
                                
                today_total_calories as (
                SELECT coalesce(SUM((data ->'value')::double precision),0) today_total_calories
                	FROM core.entries
                	WHERE type = 'Meal' AND start_time >= DATE_TRUNC('day',@date::timestamp)),
                                
                this_week_total_calories as (
                SELECT coalesce(SUM((data ->'value')::double precision),0) this_week_total_calories
                	FROM core.entries
                	WHERE type ='Meal' and date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval <= start_time AND
                	start_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval + '1 week'::interval),
                                
                this_week_avg_weight as (
                SELECT coalesce(AVG((data ->'value')::double precision),0) this_week_avg_weight
                	FROM core.entries
                	WHERE type = 'Weight' AND date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval <= start_time AND
                	start_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - '1 day'::interval + '1 week'::interval),
                	
                	
                last_week_avg_weight as (
                SELECT coalesce(AVG((data ->'value')::double precision),0) last_week_avg_weight
                	FROM core.entries
                	WHERE type = 'Weight' AND date_trunc('week', DATE_TRUNC('day',@date::timestamp) - interval '1 week') - interval '1 day' <= start_time AND
                	start_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - interval '1 day' ),
                	
                last_week_total_calories as (
                SELECT coalesce(SUM((data ->'value')::double precision),0) last_week_total_calories
                	FROM core.entries
                	WHERE type = 'Meal' AND date_trunc('week', DATE_TRUNC('day',@date::timestamp) - interval '1 week') - interval '1 day' <= start_time AND
                	start_time < date_trunc('week', DATE_TRUNC('day',@date::timestamp)) - interval '1 day' )
                	
                	
                SELECT today_avg_weight, today_total_calories, this_week_total_calories, this_week_avg_weight, last_week_avg_weight, last_week_total_calories from today_avg_weight CROSS JOIN
                today_total_calories CROSS JOIN
                this_week_total_calories CROSS JOIN
                this_week_avg_weight CROSS JOIN
                last_week_avg_weight CROSS JOIN
                last_week_total_calories            
                """;
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