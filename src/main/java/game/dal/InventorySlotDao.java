package game.dal;

import java.sql.*;
import game.model.*;

public class InventorySlotDao {
    protected ConnectionManager connectionManager;

    private static InventorySlotDao instance = null;

    protected InventorySlotDao() {
        connectionManager = new ConnectionManager();
    }

    public static InventorySlotDao getInstance() {
        if (instance == null) {
            instance = new InventorySlotDao();
        }
        return instance;
    }

    public InventorySlot getInventorySlotByID(int inventorySlotID) throws SQLException {
        String query = "SELECT * FROM InventorySlot WHERE inventorySlotID = ?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(query);
            selectStmt.setInt(1, inventorySlotID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new InventorySlot(
                    resultSet.getInt("inventorySlotID")
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

    public InventorySlot create(InventorySlot inventorySlot) throws SQLException {
        String insertInventorySlot = "INSERT INTO InventorySlot (inventorySlotID) VALUES (?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertInventorySlot, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, inventorySlot.getInventorySlotID());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return new InventorySlot(
                    resultKey.getInt(1)
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
        return inventorySlot;
    }
}

