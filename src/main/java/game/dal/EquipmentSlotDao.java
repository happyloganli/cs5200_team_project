package game.dal;

import java.sql.*;
import game.model.*;

public class EquipmentSlotDao {
    protected ConnectionManager connectionManager;

    private static EquipmentSlotDao instance = null;

    protected EquipmentSlotDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquipmentSlotDao getInstance() {
        if (instance == null) {
            instance = new EquipmentSlotDao();
        }
        return instance;
    }

    public EquipmentSlot getEquipmentSlotByID(int equipSlotID) throws SQLException {
        String selectEquipmentSlot =
            "SELECT equipSlotID, slotName " +
            "FROM EquipmentSlot WHERE equipSlotID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquipmentSlot);
            selectStmt.setInt(1, equipSlotID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new EquipmentSlot(
                    resultSet.getInt("equipSlotID"),
                    resultSet.getString("slotName")
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

    public EquipmentSlot create(EquipmentSlot equipmentSlot) throws SQLException {
        String insertEquipmentSlot =
            "INSERT INTO EquipmentSlot (slotName) VALUES (?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquipmentSlot, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, equipmentSlot.getSlotName());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int equipSlotID = -1;
            if (resultKey.next()) {
                equipSlotID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }

            return new EquipmentSlot(
                equipSlotID,
                equipmentSlot.getSlotName()
            );
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
}
