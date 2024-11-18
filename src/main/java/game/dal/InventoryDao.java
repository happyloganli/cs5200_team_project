package game.dal;

import java.sql.*;
import game.model.*;

public class InventoryDao {
    protected ConnectionManager connectionManager;

    private static InventoryDao instance = null;

    protected InventoryDao() {
        connectionManager = new ConnectionManager();
    }

    public static InventoryDao getInstance() {
        if (instance == null) {
            instance = new InventoryDao();
        }
        return instance;
    }

    public Inventory getInventoryByID(int characterID, int inventorySlotID) throws SQLException {
        String query = "SELECT i.characterID, i.inventorySlotID, i.itemStackID, "
                     + "is.itemID, is.quantity, inv.itemName, inv.maxStackSize, inv.isSellable, inv.vendorPrice, inv.itemLevel "
                     + "FROM Inventory i "
                     + "JOIN ItemStack is ON i.itemStackID = is.itemStackID "
                     + "JOIN Item inv ON is.itemID = inv.itemID "
                     + "WHERE i.characterID = ? AND i.inventorySlotID = ?";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(query);
            selectStmt.setInt(1, characterID);
            selectStmt.setInt(2, inventorySlotID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                Chara character = new Chara(
                    resultSet.getInt("characterID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    new Player(resultSet.getString("playerEmail"), resultSet.getString("playerName"), resultSet.getBoolean("isActive")),
                    resultSet.getInt("HP"),
                    resultSet.getInt("MP")
                );

                InventorySlotDao inventorySlotDao = InventorySlotDao.getInstance();
                InventorySlot inventorySlot = inventorySlotDao.getInventorySlotByID(resultSet.getInt("inventorySlotID"));

                ItemStack itemStack = new ItemStack(
                    resultSet.getInt("itemStackID"),
                    inventorySlot,
                    new Item(
                        resultSet.getInt("itemID"),
                        resultSet.getString("itemName"),
                        resultSet.getInt("maxStackSize"),
                        resultSet.getBoolean("isSellable"),
                        resultSet.getInt("vendorPrice"),
                        resultSet.getInt("itemLevel")
                    ),
                    resultSet.getInt("quantity")
                );

                return new Inventory(character, inventorySlot, itemStack);
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

    public Inventory create(Inventory inventory) throws SQLException {
        String insertInventory =
            "INSERT INTO Inventory (characterID, inventorySlotID, itemStackID) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertInventory, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, inventory.getCharacter().getCharacterID());
            insertStmt.setInt(2, inventory.getInventorySlot().getInventorySlotID());
            insertStmt.setInt(3, inventory.getItemStack().getItemStackID());

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                resultKey = insertStmt.getGeneratedKeys();
                if (resultKey.next()) {
                    int generatedItemStackID = resultKey.getInt(1);
                    inventory.getItemStack().setItemStackID(generatedItemStackID);
                    return inventory;
                }
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
        return null;
    }
}
