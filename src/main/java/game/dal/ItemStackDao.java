package game.dal;

import game.model.*;

import java.sql.*;

public class ItemStackDao {
    protected ConnectionManager connectionManager;

    private static ItemStackDao instance = null;

    protected ItemStackDao() {
        connectionManager = new ConnectionManager();
    }

    public static ItemStackDao getInstance() {
        if (instance == null) {
            instance = new ItemStackDao();
        }
        return instance;
    }

    public ItemStack create(ItemStack itemStack) throws SQLException {

        String insertItemStack =
            "INSERT INTO ItemStack(inventorySlotID, itemID, quantity) " +
            "VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertItemStack, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, itemStack.getInventorySlot().getInventorySlotID());
            insertStmt.setInt(2, itemStack.getItem().getItemID());
            insertStmt.setInt(3, itemStack.getQuantity());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int itemStackID = -1;
            if (resultKey.next()) {
                itemStackID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key for ItemStack.");
            }
            itemStack.setItemStackID(itemStackID);
            return itemStack;
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

    public ItemStack getItemStackByID(int itemStackID) throws SQLException {
        String selectItemStack =
            "SELECT itemStackID, inventorySlotID, itemID, quantity " +
            "FROM ItemStack WHERE itemStackID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectItemStack);
            selectStmt.setInt(1, itemStackID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int inventorySlotID = resultSet.getInt("inventorySlotID");
                InventorySlot inventorySlot = InventorySlotDao.getInstance().getInventorySlotByID(inventorySlotID);

                int itemID = resultSet.getInt("itemID");
                Item item = ItemDao.getInstance().getItemByID(itemID);

                int quantity = resultSet.getInt("quantity");

                return new ItemStack(itemStackID, inventorySlot, item, quantity);
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

