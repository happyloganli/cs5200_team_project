package game.dal;

import java.sql.*;
import game.model.*;

public class AttributeTypeDao {
    protected ConnectionManager connectionManager;

    private static AttributeTypeDao instance = null;

    protected AttributeTypeDao() {
        connectionManager = new ConnectionManager();
    }

    public static AttributeTypeDao getInstance() {
        if (instance == null) {
            instance = new AttributeTypeDao();
        }
        return instance;
    }

    public AttributeType create(AttributeType attributeType) throws SQLException {
        String insertAttributeType = 
            "INSERT INTO AttributeType(attributeName, description) " +
            "VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAttributeType, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, attributeType.getAttributeName());
            insertStmt.setString(2, attributeType.getDescription());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int attributeTypeId = -1;
            if (resultKey.next()) {
                attributeTypeId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            attributeType.setAttributeTypeID(attributeTypeId);
            return attributeType;
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

    public AttributeType getAttributeTypeByID(int attributeTypeID) throws SQLException {
        String selectAttributeType = 
            "SELECT * FROM AttributeType WHERE attributeTypeID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttributeType);
            selectStmt.setInt(1, attributeTypeID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new AttributeType(
                    resultSet.getInt("attributeTypeID"),
                    resultSet.getString("attributeName"),
                    resultSet.getString("description")
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
