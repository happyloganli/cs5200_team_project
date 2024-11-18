package game.dal;

import game.model.*;
import java.sql.*;

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
}
