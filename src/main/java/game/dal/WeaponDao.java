package game.dal;
import game.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeaponDao extends EquipmentDao {
    private static WeaponDao instance = null;

    protected WeaponDao() {
        super();
    }

    public static WeaponDao getInstance() {
        if (instance == null) {
            instance = new WeaponDao();
        }
        return instance;
    }

    public Weapon create(Weapon weapon) throws SQLException {
        EquipmentDao equipmentDao = EquipmentDao.getInstance();
        equipmentDao.create(weapon);

        String insertWeapon = "INSERT INTO Weapon(ItemID, EquipSlotID, DamageDone, AutoAttack, AttackDelay) VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertWeapon);
            insertStmt.setInt(1, weapon.getItemID());
            insertStmt.setInt(2, weapon.getEquipSlot().getEquipSlotID());
            insertStmt.setInt(3, weapon.getDamageDone());
            insertStmt.setBoolean(4, weapon.getAutoAttack());
            insertStmt.setInt(5, weapon.getAttackDelay());
            insertStmt.executeUpdate();
            return weapon;
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
        }
    }

    public Weapon getWeaponByID(int itemID) throws SQLException {
        String selectWeapon = "SELECT Weapon.ItemID, EquipSlotID, DamageDone, AutoAttack, AttackDelay, " +
                "Equipment.ItemName, Equipment.MaxStackSize, Equipment.IsSellable, Equipment.VendorPrice, " +
                "Equipment.ItemLevel, Equipment.RequiredLevel, EquipmentSlot.SlotName " +
                "FROM Weapon " +
                "INNER JOIN Equipment ON Weapon.ItemID = Equipment.ItemID " +
                "INNER JOIN EquipmentSlot ON Weapon.EquipSlotID = EquipmentSlot.EquipSlotID " +
                "WHERE Weapon.ItemID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWeapon);
            selectStmt.setInt(1, itemID);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int equipSlotID = results.getInt("EquipSlotID");
                String slotName = results.getString("SlotName");
                EquipmentSlot equipSlot = new EquipmentSlot(equipSlotID, slotName);

                return new Weapon(
                        results.getInt("ItemID"),
                        results.getString("ItemName"),
                        results.getInt("MaxStackSize"),
                        results.getBoolean("IsSellable"),
                        results.getInt("VendorPrice"),
                        results.getInt("ItemLevel"),
                        results.getInt("RequiredLevel"),
                        equipSlot,
                        results.getInt("DamageDone"),
                        results.getBoolean("AutoAttack"),
                        results.getInt("AttackDelay")
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
            if (results != null) {
                results.close();
            }
        }
        return null;
    }
}
