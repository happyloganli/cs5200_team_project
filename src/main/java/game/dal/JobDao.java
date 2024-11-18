package game.dal;

import java.sql.*;
import game.model.*;

public class JobDao {
    protected ConnectionManager connectionManager;

    private static JobDao instance = null;

    protected JobDao() {
        connectionManager = new ConnectionManager();
    }

    public static JobDao getInstance() {
        if (instance == null) {
            instance = new JobDao();
        }
        return instance;
    }

    public Job getJobByID(int jobID) throws SQLException {
        String query = "SELECT * FROM Job WHERE jobID = ?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(query);
            selectStmt.setInt(1, jobID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new Job(
                    resultSet.getInt("jobID"),
                    resultSet.getString("description"),
                    resultSet.getString("jobName"),
                    resultSet.getString("jobCategory")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return null;
    }

    public Job create(Job job) throws SQLException {
        String insertJob =
            "INSERT INTO Job (description, jobName, jobCategory) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertJob, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, job.getDescription());
            insertStmt.setString(2, job.getJobName());
            insertStmt.setString(3, job.getJobCategory());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return new Job(
                    resultKey.getInt(1),
                    job.getDescription(),
                    job.getJobName(),
                    job.getJobCategory()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (resultKey != null) {
                resultKey.close();
            }
        }
        return job;
    }
}
