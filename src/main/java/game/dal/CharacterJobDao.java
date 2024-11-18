package game.dal;

import java.sql.*;
import game.model.*;

public class CharacterJobDao {
    protected ConnectionManager connectionManager;

    private static CharacterJobDao instance = null;

    protected CharacterJobDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterJobDao getInstance() {
        if (instance == null) {
            instance = new CharacterJobDao();
        }
        return instance;
    }

    public CharacterJob create(CharacterJob characterJob) throws SQLException {
        String insertCharacterJob = 
            "INSERT INTO CharacterJob(jobID, characterID, jobLevel, experience) " +
            "VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterJob, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, characterJob.getJob().getJobID());
            insertStmt.setInt(2, characterJob.getCharacter().getCharacterID());
            insertStmt.setInt(3, characterJob.getJobLevel());
            insertStmt.setInt(4, characterJob.getExperience());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return characterJob;
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
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
    }

    public CharacterJob getCharacterJobByID(int jobID, int characterID) throws SQLException {
        String selectCharacterJob = 
            "SELECT jobID, characterID, jobLevel, experience " +
            "FROM CharacterJob " +
            "WHERE jobID = ? AND characterID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterJob);
            selectStmt.setInt(1, jobID);
            selectStmt.setInt(2, characterID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                JobDao jobDao = new JobDao();
                CharaDao charaDao = new CharaDao();

                return new CharacterJob(
                    jobDao.getJobByID(resultSet.getInt("jobID")),
                    charaDao.getCharaByID(resultSet.getInt("characterID")),
                    resultSet.getInt("jobLevel"),
                    resultSet.getInt("experience")
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
}
