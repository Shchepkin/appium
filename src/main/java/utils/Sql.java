package utils;


import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sql {

    private Setup s = new Setup();
    private Formatter f = new Formatter();
    private ArrayList validationToken = new ArrayList();

    // JDBC URL, username and password of MySQL server
    private String url = s.getDbSettings().get("url").toString();
    private String user = s.getDbSettings().get("user").toString();
    private String password = s.getDbSettings().get("password").toString();

    // JDBC variables for opening and managing connection
    private static Connection connection;
    private static Statement stmt;
    private static ResultSet rs;

    public ArrayList selectList = new ArrayList();
    public Map tokenMap = new HashMap();


    public Sql() {
    }

    /**
     *  REQUIRED getConnection()
        @param row  - name of row for searching element in database (Login, Phone)
        @param value - value of row for searching

        example:
            sql.getDelete("Phone", "%1216815329%");
     */
    public void getDelete(String row, String value) {
        s.log("Method is started");

        String query = "DELETE FROM csa_accounts WHERE " + row + " LIKE '" + value + "'";

        selectList = getSelect(row, value);

        try {
            connection = getConnection();

            s.log(2, "getting Statement object to execute query");
            stmt = connection.createStatement();

            s.log(3, "this objects will be deleted: ");
            for (Object sqlResult : selectList) {
                System.out.println("\033[31;49m" + sqlResult + "\033[39;49m");
            }

            s.log(3, "delete objects");
            stmt.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            s.log("close all connections");
            try {connection.close();} catch (SQLException se) { /*can't do anything */ }
            try {stmt.close();}catch (SQLException se) { /*can't do anything */ }
            try {rs.close();} catch (SQLException se) { /*can't do anything */ }
        }
        s.log("Method is finished");
    }




    /**
     *  REQUIRED getConnection()
        @param row      - name of row for searching element in database (Login, Phone)
        @param value    - value of row for searching
        @return  ArrayList selectList - arrayList with all values from the SQL query

        example:
            sql.getSelect("Login", "test302@test.com");
            System.out.println(selectList);
     */
    public ArrayList getSelect(String row, String value) {
        s.log("Method is started");
        validationToken.clear();
        selectList.clear();

        String query = "SELECT id,InnerID,Role,Phone,ConfirmationToken,Login FROM csa_accounts WHERE " + row + " LIKE '" + value + "' ORDER BY id ASC";

        try {
            connection = getConnection();

            s.log("getting Statement object to execute query");
            stmt = connection.createStatement();

            s.log(2, "executing query: [ " + query + " ]");
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String innerID = rs.getString("InnerID");
                String role = rs.getString("Role");
                String phone = rs.getString("Phone");
                String confirmationToken = rs.getString("ConfirmationToken");
                String login = rs.getString("Login");

                validationToken.add(confirmationToken);
                f.format("%-5d %-12s %-8s %-20s %-20s %-1s\n", id, innerID, role, phone, confirmationToken, login);
            }

            selectList.add(f);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            s.log("close all connections");
            try {connection.close();} catch (SQLException se) { /*can't do anything */ }
            try {stmt.close();}catch (SQLException se) { /*can't do anything */ }
            try {rs.close();} catch (SQLException se) { /*can't do anything */ }
        }

        s.log("Method is finished");
        return selectList;
    }


    /**
     *  REQUIRED getConnection()
        @param row   - name of row for searching element in database (Login, Phone)
        @param value - value of row for searching
        @return  Map tokenMap - map with "smsToken" key and "emailToken" key

        example:
            tokenMap = sql.getTokenMap("Phone", "%+380681513888%");
            tokenMap.get("smsToken");
            tokenMap.get("emailToken");
        or
            tokenMap = sql.getTokenMap("Login", "vladislav@iondigi.com");
            tokenMap.get("smsToken");
            tokenMap.get("emailToken");
     */
    public Map getTokenMap(String row, String value) {
        s.log("Method is started");
        int counter = 1;
        while (validationToken.size() < 1 || validationToken.get(0) == null) {
            s.log(3, "Try to get Tokens from database. Attempt #" + counter);
            getSelect(row, value);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (counter == 5){
                break;
            }else {counter++;}
        }

        if (validationToken.size() != 1) {
            Assert.fail("Check whether your credentials are correct! Wrong number of found elements: " + validationToken.size());
        }
        else if (validationToken.get(0) == null) {
            Assert.fail("Check whether your credentials are correct! Confirmation Token of found element: null");
        }
        else {
            String input = validationToken.get(0).toString();

            s.log("Validation Token Array is not empty, so we get string and clear Array");
            validationToken.clear();

            s.log("create matcher");
            Pattern pattern = Pattern.compile("[\\d]{6}");
            Matcher matcher = pattern.matcher(input);

            s.log("add matcher value to the Validation Token Array");
                while(matcher.find()){
                    validationToken.add(matcher.group());
                }
            if (validationToken.size() < 2) {
                Assert.fail("Validation Token Array is not correct! Tokens number is less then 2");
            }
            else {
                tokenMap.put("smsToken", validationToken.get(0));
                tokenMap.put("emailToken", validationToken.get(1));
            }
        }
        s.log("Method is finished");
        return tokenMap;
    }


    /**
     * This method for getting Connection to the SQL server with 10 attempts
     * Must be used with other methods.
     * @return Connection connection
     */
    private Connection getConnection (){
        s.log("Method is started");
        for (int i = 1; i <= 10; i++) {
            try {
                s.log("opening database connection to MySQL server, attempt #" + i);
                connection = DriverManager.getConnection(url, user, password);
                s.log("connection to MySQL server is successfully opened");
                break;

            }catch (Exception e){
                s.log(3, "opening database connection fail: " + e.getClass());
                s.log(3, "cause of problem: "+ e.getCause().toString());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        s.log("Method is finished");
        return connection;
    }
}
