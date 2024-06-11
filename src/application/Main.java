package application;

import db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {

        Connection connection;
        Statement statement = null;
        ResultSet resultSet = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        PreparedStatement preparedStatement = null;

        try {
            connection = DB.getConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE seller "
                        + "SET BaseSalary = BaseSalary + ? "
                        + "WHERE "
                        + "(DepartmentId = ?)");

            preparedStatement.setDouble(1, 200.0);
            preparedStatement.setInt(2, 2);

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Done! Rows Affected: " + rowsAffected);

        } catch (SQLException e) {
            e.getMessage();
        }

        try {
            connection = DB.getConnection();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, "Filipe Mota");
            preparedStatement.setString(2, "filipe@gmail.com");
            preparedStatement.setDate(3, new java.sql.Date(simpleDateFormat.parse("03/01/2006").getTime()));
            preparedStatement.setDouble(4, 3500.0);
            preparedStatement.setInt(5, 4);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();

                while(resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println("Done! Id = " + id);
                }

            } else {
                System.out.println("No rows affected!");
            }

        }  catch (SQLException e) {
            e.getMessage();
        } catch (ParseException e) {
            e.getMessage();


        System.out.println();

        try {
            connection = DB.getConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");

            while(resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("Id") + ", " + resultSet.getString("Name"));
            }

        } catch (SQLException error) {
            error.printStackTrace();
        }

        finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
    }
}

