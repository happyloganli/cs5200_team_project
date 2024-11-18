package game.dal;

import game.model.Item;
import game.model.AttributeType;
import game.model.ItemBonus;

import java.sql.*;
import java.math.BigDecimal;

public class ItemBonusDao {
    protected ConnectionManager connectionManager;

    private static ItemBonusDao instance = null;

    protected ItemBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static ItemBonusDao getInstance() {
        if (instance == null) {
            instance = new ItemBonusDao();
        }
        return instance;
    }

    public ItemBonus create(ItemBonus itemBonus) throws SQLException {

        String insertItemBonus = 
            "INSERT INTO ItemBonus(itemID, attributeTypeID, bonusVal, foodBonusVal, foodBonusCap) " +
            "VALUES(?, ?, ?, ?, ?);";

        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertItemBonus, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, itemBonus.getItem().getItemID());
            insertStmt.setInt(2, itemBonus.getAttributeType().getAttributeTypeID()); 
            insertStmt.setInt(3, itemBonus.getBonusVal());
            insertStmt.setBigDecimal(4, itemBonus.getFoodBonusVal());
            insertStmt.setInt(5, itemBonus.getFoodBonusCap());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int itemBonusID = -1;
            if(resultKey.next()) {
                itemBonusID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key for ItemBonus.");
            }

            return itemBonus;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(resultKey != null) {
                resultKey.close();
            }
        }
    }

    public ItemBonus getItemBonusByID(int itemBonusID) throws SQLException {
        String selectItemBonus = 
            "SELECT itemBonusID, itemID, attributeTypeID, bonusVal, foodBonusVal, foodBonusCap " +
            "FROM ItemBonus WHERE itemBonusID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectItemBonus);
            selectStmt.setInt(1, itemBonusID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int itemID = resultSet.getInt("itemID");
                Item item = ItemDao.getInstance().getItemByID(itemID);

                int attributeTypeID = resultSet.getInt("attributeTypeID");
                AttributeType attributeType = AttributeTypeDao.getInstance().getAttributeTypeByID(attributeTypeID);

                Integer bonusVal = resultSet.getInt("bonusVal");
                BigDecimal foodBonusVal = resultSet.getBigDecimal("foodBonusVal");
                Integer foodBonusCap = resultSet.getInt("foodBonusCap");

                return new ItemBonus(item, attributeType, bonusVal, foodBonusVal, foodBonusCap);
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
