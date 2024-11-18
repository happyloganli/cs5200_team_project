package game.dal;

import java.sql.*;
import game.model.*;

public class CharacterAttributeDao {
    protected ConnectionManager connectionManager;

    private static CharacterAttributeDao instance = null;

    protected CharacterAttributeDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterAttributeDao getInstance() {
        if (instance == null) {
            instance = new CharacterAttributeDao();
        }
        return instance;
    }

    public CharacterAttribute create(CharacterAttribute characterAttribute) throws SQLException {
        String insertCharacterAttribute = 
            "INSERT INTO CharacterAttribute(characterID, attributeTypeID, value) " +
            "VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterAttribute, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, characterAttribute.getCharacter().getCharacterID());
            insertStmt.setInt(2, characterAttribute.getAttributeType().getAttributeTypeID());
            insertStmt.setInt(3, characterAttribute.getValue());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return characterAttribute;
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

    public CharacterAttribute getCharacterAttributeByID(int characterID, int attributeTypeID) throws SQLException {
        String selectCharacterAttribute = 
            "SELECT ca.characterID, ca.attributeTypeID, ca.value, c.firstName, c.lastName, c.HP, c.MP, p.email, p.playerName, p.isActive " +
            "FROM CharacterAttribute ca " +
            "JOIN Chara c ON ca.characterID = c.characterID " +
            "JOIN Player p ON c.playerID = p.playerID " +
            "WHERE ca.characterID = ? AND ca.attributeTypeID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterAttribute);
            selectStmt.setInt(1, characterID);
            selectStmt.setInt(2, attributeTypeID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                Player player = new Player(
                    resultSet.getString("email"),
                    resultSet.getString("playerName"),
                    resultSet.getBoolean("isActive")
                );
                Chara character = new Chara(
                    resultSet.getInt("characterID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    player,
                    resultSet.getInt("HP"),
                    resultSet.getInt("MP")
                );
                AttributeType attributeType = new AttributeType(
                    resultSet.getInt("attributeTypeID"),
                    resultSet.getString("attributeName"),
                    resultSet.getString("description")
                );
                return new CharacterAttribute(character, attributeType, resultSet.getInt("value"));
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
