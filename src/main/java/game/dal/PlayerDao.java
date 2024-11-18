package game.dal;

import java.sql.*;
import game.model.*;

public class PlayerDao {
    protected ConnectionManager connectionManager;

    private static PlayerDao instance = null;

    protected PlayerDao() {
        connectionManager = new ConnectionManager();
    }

    public static PlayerDao getInstance() {
        if (instance == null) {
            instance = new PlayerDao();
        }
        return instance;
    }

    public Player getPlayerByEmail(String email) throws SQLException {
        String query = "SELECT * FROM Player WHERE email = ?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(query);
            selectStmt.setString(1, email);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new Player(
                    resultSet.getString("email"),
                    resultSet.getString("playerName"),
                    resultSet.getBoolean("isActive")
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

    public Player create(Player player) throws SQLException {
        String insertPlayer = "INSERT INTO Player (email, playerName, isActive) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPlayer, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, player.getEmail());
            insertStmt.setString(2, player.getPlayerName());
            insertStmt.setBoolean(3, player.isActive());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return new Player(
                    player.getEmail(),
                    player.getPlayerName(),
                    player.isActive()
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
        return player;
    }
}
