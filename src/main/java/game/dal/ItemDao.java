package game.dal;

import java.sql.*;
import game.model.*;

public class ItemDao {
    protected ConnectionManager connectionManager;

    private static ItemDao instance = null;

    protected ItemDao() {
        connectionManager = new ConnectionManager();
    }

    public static ItemDao getInstance() {
        if (instance == null) {
            instance = new ItemDao();
        }
        return instance;
    }

    public Item getItemByID(int itemID) throws SQLException {
        String query = "SELECT * FROM Item WHERE itemID = ?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(query);
            selectStmt.setInt(1, itemID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new Item(
                    resultSet.getInt("itemID"),
                    resultSet.getString("itemName"),
                    resultSet.getObject("maxStackSize", Integer.class),
                    resultSet.getBoolean("isSellable"),
                    resultSet.getObject("vendorPrice", Integer.class),
                    resultSet.getInt("itemLevel")
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

    public Item create(Item item) throws SQLException {
        String insertItem =
            "INSERT INTO Item (itemName, maxStackSize, isSellable, vendorPrice, itemLevel) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, item.getItemName());
            insertStmt.setObject(2, item.getMaxStackSize());
            insertStmt.setBoolean(3, item.isSellable());
            insertStmt.setObject(4, item.getVendorPrice());
            insertStmt.setInt(5, item.getItemLevel());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return new Item(
                    resultKey.getInt(1),
                    item.getItemName(),
                    item.getMaxStackSize(),
                    item.isSellable(),
                    item.getVendorPrice(),
                    item.getItemLevel()
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
        return item;
    }
}
