package game.dal;

import java.sql.*;
import game.model.*;

public class ConsumableDao {
    protected ConnectionManager connectionManager;

    private static ConsumableDao instance = null;

    protected ConsumableDao() {
        connectionManager = new ConnectionManager();
    }

    public static ConsumableDao getInstance() {
        if (instance == null) {
            instance = new ConsumableDao();
        }
        return instance;
    }

    public Consumable create(Consumable consumable) throws SQLException {
        String insertConsumable = 
            "INSERT INTO Consumable(itemName, maxStackSize, isSellable, vendorPrice, itemLevel, description) " +
            "VALUES(?, ?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertConsumable, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, consumable.getItemName());
            insertStmt.setInt(2, consumable.getMaxStackSize());
            insertStmt.setBoolean(3, consumable.isSellable());
            insertStmt.setInt(4, consumable.getVendorPrice());
            insertStmt.setInt(5, consumable.getItemLevel());
            insertStmt.setString(6, consumable.getDescription());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                consumable.setItemID(resultKey.getInt(1));
                return consumable;
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

    public Consumable getConsumableByID(int itemID) throws SQLException {
        String selectConsumable = 
            "SELECT itemID, itemName, maxStackSize, isSellable, vendorPrice, itemLevel, description " +
            "FROM Consumable WHERE itemID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectConsumable);
            selectStmt.setInt(1, itemID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new Consumable(
                    resultSet.getInt("itemID"),
                    resultSet.getString("itemName"),
                    resultSet.getInt("maxStackSize"),
                    resultSet.getBoolean("isSellable"),
                    resultSet.getInt("vendorPrice"),
                    resultSet.getInt("itemLevel"),
                    resultSet.getString("description")
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
