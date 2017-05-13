package utils;


import org.testng.Assert;
import pageObjects.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sql {

    private Formatter f = new Formatter();
    private ArrayList validationToken, selectList;
    private String table;

    // JDBC variables for opening and managing connection
    private static Connection connection;
    private static Statement stmt;
    private static ResultSet rs;

    //----------------------------------------------------------------------------------------------------------------------
    private Base base;
    public Sql(Base base) {
        this.base = base;
        table = base.getDbSettingsWithKey("accountsTable");
    }
//----------------------------------------------------------------------------------------------------------------------


    /**
     * REQUIRED getConnection()
     *
     * @param row   - name of row for searching element in database (Login, Phone)
     * @param value - value of row for searching
     *              <p>
     *              example:
     *              sql.getDelete("Phone", "%1216815329%");
     */
    public void getDelete(String row, String value) {
        selectList = new ArrayList();
        String query = "DELETE FROM " + table + " WHERE " + row + " LIKE '" + value + "'";

        selectList = getSelect(row, value);
        if (!selectList.get(0).toString().isEmpty()) {
            try {
                connection = getConnection();

                Base.log(4, "getting Statement object to execute query");
                stmt = connection.createStatement();

                for (Object sqlResult : selectList) {
                    Base.log(1, "this objects will be deleted: \"\033[31;49m" + sqlResult + "\"\033[39;49m");
                }
                Base.log(4, "delete objects");
                stmt.executeUpdate(query);

            } catch (SQLException sqlEx) {
                Base.log(2, "SQLException: \n\n" + sqlEx + "\n");
            } finally {
                Base.log(1, "close all connections");
                try {
                    connection.close();
                } catch (SQLException se) { /*can't do anything */ }
                try {
                    stmt.close();
                } catch (SQLException se) { /*can't do anything */ }
            }
        }else {
            Base.log(1, "this entry is not found in database");
        }
    }


    /**
     * REQUIRED getConnection()
     *
     * @param row   - name of row for searching element in database (Login, Phone)
     * @param value - value of row for searching
     * @return ArrayList selectList - arrayList with all values from the SQL query
     * <p>
     * example:
     * sql.getSelect("Login", "test302@test.com");
     * System.out.println(selectList);
     */
    public ArrayList getSelect(String row, String value) {
        validationToken = new ArrayList();
        selectList = new ArrayList();

        validationToken.clear();
        selectList.clear();

        String query = "SELECT id,InnerID,Role,Phone,ConfirmationToken,Login FROM " + table + " WHERE " + row + " LIKE '" + value + "' ORDER BY id ASC";

        try {
            connection = getConnection();

            Base.log(1, "getting Statement object to execute query");
            stmt = connection.createStatement();

            Base.log(4, "executing query: [ " + query + " ]");
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String innerID = rs.getString("InnerID");
                String role = rs.getString("Role");
                String phone = rs.getString("Phone");
                String confirmationToken = rs.getString("ConfirmationToken");
                String login = rs.getString("Login");

                validationToken.add(confirmationToken);
                f.format("\n%-5d %-12s %-8s %-20s %-20s %-1s", id, innerID, role, phone, confirmationToken, login);
            }

            selectList.add(f);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            Base.log(1, "close all connections");
            try {
                connection.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        return selectList;
    }


    /**
     * REQUIRED getConnection()
     *
     * @param row   - name of row for searching element in database (Login, Phone)
     * @param value - value of row for searching
     * @return Map tokenMap - map with "smsToken" key and "emailToken" key
     * <p>
     * example:
     * tokenMap = sql.getTokenMap("Phone", "%+380681513888%");
     * tokenMap.get("smsToken");
     * tokenMap.get("emailToken");
     * or
     * tokenMap = sql.getTokenMap("Login", "vladislav@iondigi.com");
     * tokenMap.get("smsToken");
     * tokenMap.get("emailToken");
     */
    public Map getTokenMap(String row, String value) {
        Map tokenMap = new HashMap();
        validationToken = new ArrayList();

        int counter = 1;
        while (validationToken.size() < 1 || validationToken.get(0) == null) {
            Base.log(3, "Try to get Tokens from database. Attempt #" + counter);
            getSelect(row, value);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (counter == 5) {
                break;
            } else {
                counter++;
            }
        }

        if (validationToken.size() != 1) {
            Assert.fail("Check whether your credentials are correct! Wrong number of found elements: " + validationToken.size());
        } else if (validationToken.get(0) == null) {
            Assert.fail("Check whether your credentials are correct! Confirmation Token of found element: null");
        } else {
            String input = validationToken.get(0).toString();

            Base.log(1, "Validation Token Array is not empty, so we get string and clear Array");
            validationToken.clear();

            Base.log(1, "create matcher");
            Pattern pattern = Pattern.compile("[\\d]{6}");
            Matcher matcher = pattern.matcher(input);

            Base.log(1, "add matcher value to the Validation Token Array");
            while (matcher.find()) {
                validationToken.add(matcher.group());
            }
            if (validationToken.size() < 2) {
                Assert.fail("Validation Token Array is not correct! Tokens number is less then 2");
            } else {
                tokenMap.put("smsToken", validationToken.get(0));
                tokenMap.put("emailToken", validationToken.get(1));
            }
        }
        return tokenMap;
    }


    /**
     * This method for getting Connection to the SQL server with 10 attempts
     * Must be used with other methods.
     *
     * @return Connection connection
     */
    private Connection getConnection() {
        String url = base.getDbSettingsWithKey("url");
        String user = base.getDbSettingsWithKey("user");
        String password = base.getDbSettingsWithKey("password");
        for (int i = 1; i <= 10; i++) {
            try {
                Base.log(1, "opening database connection to MySQL server, attempt #" + i);
                connection = DriverManager.getConnection(url, user, password);
                Base.log(1, "connection to MySQL server is successfully opened");
                break;

            } catch (Exception e) {
                Base.log(3, "opening database connection fail: " + e.getClass());
                Base.log(3, "cause of problem: " + e.getCause().toString());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return connection;
    }
}
