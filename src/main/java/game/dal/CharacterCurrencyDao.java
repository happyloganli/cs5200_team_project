package game.dal;

import java.sql.*;
import game.model.*;

public class CharacterCurrencyDao {
    protected ConnectionManager connectionManager;

    private static CharacterCurrencyDao instance = null;

    protected CharacterCurrencyDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterCurrencyDao getInstance() {
        if (instance == null) {
            instance = new CharacterCurrencyDao();
        }
        return instance;
    }

    public CharacterCurrency create(CharacterCurrency characterCurrency) throws SQLException {
        String insertCharacterCurrency = 
            "INSERT INTO CharacterCurrency(characterID, currencyID, totalAmount, weeklyEarned) " +
            "VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterCurrency, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, characterCurrency.getCharacter().getCharacterID());
            insertStmt.setInt(2, characterCurrency.getCurrency().getCurrencyID());
            insertStmt.setInt(3, characterCurrency.getTotalAmount());
            insertStmt.setInt(4, characterCurrency.getWeeklyEarned());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                return characterCurrency;
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

    public CharacterCurrency getCharacterCurrencyByID(int characterID, int currencyID) throws SQLException {
        String selectCharacterCurrency = 
            "SELECT characterID, currencyID, totalAmount, weeklyEarned " +
            "FROM CharacterCurrency " +
            "WHERE characterID = ? AND currencyID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterCurrency);
            selectStmt.setInt(1, characterID);
            selectStmt.setInt(2, currencyID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                CharaDao charaDao = new CharaDao();
                CurrencyDao currencyDao = new CurrencyDao();

                return new CharacterCurrency(
                    charaDao.getCharaByID(resultSet.getInt("characterID")),
                    currencyDao.getCurrencyByID(resultSet.getInt("currencyID")),
                    resultSet.getInt("totalAmount"),
                    resultSet.getInt("weeklyEarned")
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
