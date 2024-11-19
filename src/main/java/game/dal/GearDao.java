package game.dal;

import game.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GearDao {
    protected ConnectionManager connectionManager;

    private static GearDao instance = null;

    protected GearDao() {
        connectionManager = new ConnectionManager();
    }

    public static GearDao getInstance() {
        if (instance == null) {
            instance = new GearDao();
        }
        return instance;
    }

    public Gear create(Gear gear) throws SQLException {
        String insertGear =
            "INSERT INTO Gears (equipSlotID, defenseRating, magicDefenseRating) "
            + "VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertGear, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, gear.getEquipSlot().getEquipSlotID());
            insertStmt.setInt(2, gear.getDefenseRating());
            insertStmt.setInt(3, gear.getMagicDefenseRating());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int gearID = -1;
            if (resultKey.next()) {
                gearID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }

            return new Gear(
                    gearID, 
                    gear.getItemName(), 
                    gear.getMaxStackSize(), 
                    gear.isSellable(), 
                    gear.getVendorPrice(), 
                    gear.getItemLevel(), 
                    gear.getRequiredLevel(),
                    gear.getEquipSlot(), 
                    gear.getDefenseRating(), 
                    gear.getMagicDefenseRating()
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

    public Gear getGearByID(int gearID) throws SQLException {
        String selectGear =
            "SELECT * FROM Gears WHERE gearID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectGear);
            selectStmt.setInt(1, gearID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                EquipmentSlotDao equipmentSlotDao = new EquipmentSlotDao();
                EquipmentSlot equipSlot = equipmentSlotDao.getEquipmentSlotByID(resultSet.getInt("equipSlotID"));

                return new Gear(
                        gearID,
                        resultSet.getString("itemName"),
                        resultSet.getInt("maxStackSize"),
                        resultSet.getBoolean("isSellable"),
                        resultSet.getInt("vendorPrice"),
                        resultSet.getInt("itemLevel"),
                        resultSet.getInt("requiredLevel"),
                        equipSlot,
                        resultSet.getInt("defenseRating"),
                        resultSet.getInt("magicDefenseRating")
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
    
    public Gear updateDescription(Gear gearItem, String newDescription) throws SQLException {
        String updateGear = "UPDATE Item SET ItemName=? WHERE ItemID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateGear);
            updateStmt.setString(1, newDescription);
            updateStmt.setInt(2, gearItem.getItemID());
            updateStmt.executeUpdate();
            gearItem.setItemName(newDescription);
            return gearItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }
    
    public List<Gear> getGearByPartialName(String name) throws SQLException {
        List<Gear> gearList = new ArrayList<Gear>();
        String selectGear = 
            "SELECT Item.ItemID, Item.ItemName, Item.MaxStackSize, Item.IsSellable, Item.VendorPrice, " +
            "Item.ItemLevel, Equipment.RequiredLevel, Gear.EquipSlotID, Gear.DefenseRating, Gear.MagicDefenseRating " +
            "FROM Item " +
            "INNER JOIN Equipment ON Item.ItemID = Equipment.ItemID " +
            "INNER JOIN Gear ON Equipment.ItemID = Gear.ItemID " +
            "WHERE Item.ItemName LIKE ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectGear);
            selectStmt.setString(1, "%" + name + "%");
            results = selectStmt.executeQuery();
            while (results.next()) {
                int itemID = results.getInt("ItemID");
                String itemName = results.getString("ItemName");
                int maxStackSize = results.getInt("MaxStackSize");
                boolean isSellable = results.getBoolean("IsSellable");
                Integer vendorPrice = results.getObject("VendorPrice") != null ? results.getInt("VendorPrice") : null;
                int itemLevel = results.getInt("ItemLevel");
                int requiredLevel = results.getInt("RequiredLevel");
                int equipSlotID = results.getInt("EquipSlotID");
                Integer defenseRating = results.getObject("DefenseRating") != null ? results.getInt("DefenseRating") : null;
                Integer magicDefenseRating = results.getObject("MagicDefenseRating") != null ? results.getInt("MagicDefenseRating") : null;

                EquipmentSlotDao equipmentSlotDao = EquipmentSlotDao.getInstance();
                EquipmentSlot equipSlot = equipmentSlotDao.getEquipmentSlotByID(equipSlotID);
                Gear gear = new Gear(itemID, itemName, maxStackSize, isSellable, vendorPrice, itemLevel, requiredLevel, equipSlot, defenseRating, magicDefenseRating);
                gearList.add(gear);
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
            if (results != null) {
                results.close();
            }
        }
        return gearList;
    }
}
