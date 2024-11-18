package game.dal;

import java.sql.*;
import game.model.*;

public class EquippedItemDao {
    protected ConnectionManager connectionManager;

    private static EquippedItemDao instance = null;

    protected EquippedItemDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquippedItemDao getInstance() {
        if (instance == null) {
            instance = new EquippedItemDao();
        }
        return instance;
    }

    public EquippedItem getEquippedItemByID(int characterID, int equipSlotID) throws SQLException {
        String selectEquippedItem =
            "SELECT ei.characterID, ei.equipSlotID, ei.equipmentID, "
            + "c.firstName, c.lastName, c.HP, c.MP, eq.itemID, eq.itemName, eq.maxStackSize, eq.isSellable, eq.vendorPrice, eq.itemLevel, eq.requiredLevel, "
            + "es.slotName "
            + "FROM EquippedItems ei "
            + "JOIN Chara c ON ei.characterID = c.characterID "
            + "JOIN EquipmentSlot es ON ei.equipSlotID = es.equipSlotID "
            + "JOIN Equipment eq ON ei.equipmentID = eq.itemID "
            + "WHERE ei.characterID = ? AND ei.equipSlotID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquippedItem);
            selectStmt.setInt(1, characterID);
            selectStmt.setInt(2, equipSlotID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                Player player = new Player(
                    resultSet.getString("email"),
                    "",
                    true
                );

                Chara character = new Chara(
                    resultSet.getInt("characterID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    player,
                    resultSet.getInt("HP"),
                    resultSet.getInt("MP")
                );

                EquipmentSlot equipSlot = new EquipmentSlot(
                    resultSet.getInt("equipSlotID"),
                    resultSet.getString("slotName")
                );

                Equipment equipment = new Equipment(
                    resultSet.getInt("itemID"),
                    resultSet.getString("itemName"),
                    resultSet.getInt("maxStackSize"),
                    resultSet.getBoolean("isSellable"),
                    resultSet.getInt("vendorPrice"),
                    resultSet.getInt("itemLevel"),
                    resultSet.getInt("requiredLevel")
                );

                return new EquippedItem(character, equipSlot, equipment);
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

    public EquippedItem create(EquippedItem equippedItem) throws SQLException {
        String insertEquippedItem =
            "INSERT INTO EquippedItems (characterID, equipSlotID, equipmentID) "
            + "VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquippedItem, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, equippedItem.getCharacter().getCharacterID());
            insertStmt.setInt(2, equippedItem.getEquipSlot().getEquipSlotID());
            insertStmt.setInt(3, equippedItem.getEquipment().getItemID());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int equippedItemID = -1;
            if (resultKey.next()) {
                equippedItemID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }

            return equippedItem;
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
