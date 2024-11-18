package game.dal;

import java.sql.*;
import game.model.*;

public class CurrencyDao {
    protected ConnectionManager connectionManager;

    private static CurrencyDao instance = null;

    protected CurrencyDao() {
        connectionManager = new ConnectionManager();
    }

    public static CurrencyDao getInstance() {
        if (instance == null) {
            instance = new CurrencyDao();
        }
        return instance;
    }

    public Currency create(Currency currency) throws SQLException {
        String insertCurrency =
            "INSERT INTO Currency(name, maxCap, weeklyCap, lastResetTime) " +
            "VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCurrency, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, currency.getName());
            insertStmt.setObject(2, currency.getMaxCap());
            insertStmt.setObject(3, currency.getWeeklyCap());
            insertStmt.setTimestamp(4, Timestamp.valueOf(currency.getLastResetTime()));
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                currency.setCurrencyID(resultKey.getInt(1));
                return currency;
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

    public Currency getCurrencyByID(int currencyID) throws SQLException {
        String selectCurrency =
            "SELECT currencyID, name, maxCap, weeklyCap, lastResetTime " +
            "FROM Currency WHERE currencyID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCurrency);
            selectStmt.setInt(1, currencyID);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                return new Currency(
                    resultSet.getInt("currencyID"),
                    resultSet.getString("name"),
                    resultSet.getObject("maxCap", Integer.class),
                    resultSet.getObject("weeklyCap", Integer.class),
                    resultSet.getTimestamp("lastResetTime").toLocalDateTime()
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
