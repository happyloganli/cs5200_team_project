package game.dal;

import java.sql.*;
import game.model.*;

public class CharaDao {
    protected ConnectionManager connectionManager;

    private static CharaDao instance = null;

    protected CharaDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharaDao getInstance() {
        if (instance == null) {
            instance = new CharaDao();
        }
        return instance;
    }

    public Chara getCharaByID(int characterID) throws SQLException {
        String query = "SELECT * FROM Chara WHERE characterID = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, characterID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                PlayerDao playerDao = new PlayerDao();
                Player player = rs.getString("playerEmail") != null
                    ? playerDao.getPlayerByEmail(rs.getString("playerEmail"))
                    : null;

                return new Chara(
                    rs.getInt("characterID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    player,
                    rs.getInt("HP"),
                    rs.getInt("MP")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }

    public Chara create(Chara chara) throws SQLException {
        String query = "INSERT INTO Chara (firstName, lastName, playerEmail, HP, MP) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, chara.getFirstName());
            stmt.setString(2, chara.getLastName());
            stmt.setString(3, chara.getPlayer() != null ? chara.getPlayer().getEmail() : null);
            stmt.setInt(4, chara.getHP());
            stmt.setInt(5, chara.getMP());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return new Chara(
                    rs.getInt(1),
                    chara.getFirstName(),
                    chara.getLastName(),
                    chara.getPlayer(),
                    chara.getHP(),
                    chara.getMP()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return chara;
    }
}
