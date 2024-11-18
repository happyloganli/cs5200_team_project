package game.dal;

import java.sql.*;
import game.model.*;

public class EquipmentDao {
    protected ConnectionManager connectionManager;

    private static EquipmentDao instance = null;

    protected EquipmentDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquipmentDao getInstance() {
        if (instance == null) {
            instance = new EquipmentDao();
        }
        return instance;
    }

    public Equipment getEquipmentByID(int equipmentID) throws SQLException {
        String selectEquipment =
            "SELECT equipmentID, requiredLevel " +
            "FROM Equipment WHERE equipmentID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquipment);
            selectStmt.setInt(1, equipmentID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                ItemDao itemDao = new ItemDao();
                Item item = itemDao.getItemByID(equipmentID);

                return new Equipment(
                    item.getItemID(),
                    item.getItemName(),
                    item.getMaxStackSize(),
                    item.isSellable(),
                    item.getVendorPrice(),
                    item.getItemLevel(),
                    resultSet.getInt("requiredLevel")
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

    public Equipment create(Equipment equipment) throws SQLException {
        String insertItem =
            "INSERT INTO Item(itemName, maxStackSize, isSellable, vendorPrice, itemLevel) " +
            "VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, equipment.getItemName());
            insertStmt.setInt(2, equipment.getMaxStackSize());
            insertStmt.setBoolean(3, equipment.isSellable());
            insertStmt.setInt(4, equipment.getVendorPrice());
            insertStmt.setInt(5, equipment.getItemLevel());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int itemID = -1;
            if (resultKey.next()) {
                itemID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }

            String insertEquipment = 
                "INSERT INTO Equipment(equipmentID, requiredLevel) " +
                "VALUES(?, ?);";
            PreparedStatement insertEquipmentStmt = connection.prepareStatement(insertEquipment);
            insertEquipmentStmt.setInt(1, itemID);
            insertEquipmentStmt.setInt(2, equipment.getRequiredLevel());
            insertEquipmentStmt.executeUpdate();

            return new Equipment(
                itemID,
                equipment.getItemName(),
                equipment.getMaxStackSize(),
                equipment.isSellable(),
                equipment.getVendorPrice(),
                equipment.getItemLevel(),
                equipment.getRequiredLevel()
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
