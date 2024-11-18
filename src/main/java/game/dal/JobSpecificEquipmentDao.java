package game.dal;

import game.model.Equipment;
import game.model.Job;
import game.model.JobSpecificEquipment;

import java.sql.*;

public class JobSpecificEquipmentDao {
    protected ConnectionManager connectionManager;

    private static JobSpecificEquipmentDao instance = null;

    protected JobSpecificEquipmentDao() {
        connectionManager = new ConnectionManager();
    }

    public static JobSpecificEquipmentDao getInstance() {
        if (instance == null) {
            instance = new JobSpecificEquipmentDao();
        }
        return instance;
    }

    public JobSpecificEquipment create(JobSpecificEquipment jobSpecificEquipment) throws SQLException {
        EquipmentDao equipmentDao = EquipmentDao.getInstance();
        JobDao jobDao = JobDao.getInstance();

        equipmentDao.create(jobSpecificEquipment.getEquipment());
        jobDao.create(jobSpecificEquipment.getJob());

        String insertJobSpecificEquipment =
            "INSERT INTO JobSpecificEquipment(equipmentID, jobID) " +
            "VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertJobSpecificEquipment);
            insertStmt.setInt(1, jobSpecificEquipment.getEquipment().getItemID());
            insertStmt.setInt(2, jobSpecificEquipment.getJob().getJobID());
            insertStmt.executeUpdate();
            return jobSpecificEquipment;
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
        }
    }

    public JobSpecificEquipment getJobSpecificEquipmentByIDs(int equipmentID, int jobID) throws SQLException {
        String selectJobSpecificEquipment =
            "SELECT equipmentID, jobID " +
            "FROM JobSpecificEquipment " +
            "WHERE equipmentID = ? AND jobID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJobSpecificEquipment);
            selectStmt.setInt(1, equipmentID);
            selectStmt.setInt(2, jobID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                Equipment equipment = EquipmentDao.getInstance().getEquipmentByID(equipmentID);
                Job job = JobDao.getInstance().getJobByID(jobID);
                return new JobSpecificEquipment(equipment, job);
            } else {
                return null;
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
    }
}

