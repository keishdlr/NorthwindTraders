package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class NorthwindTraders {

    public static void main(String[] args) {
        // did we pass in a username and password
        // if not the app must die
        if (args.length != 2) {
            System.out.println("Application needs two args to run: A username and password for the DB");
            System.exit(1);
        }
        //get the username and password
        String username = args[0];
        String password = args[1];

        Scanner myScanner = new Scanner(System.in);


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password)) {

            while (true) {

                System.out.println("""
                        What do you want to do?
                            1) Display All Products
                            2) Display All Customers
                            3) Display ALl Categories
                            0) Exit the dang app
                        """);
                switch (myScanner.nextInt()) {
                    case 1:
                        displayAllProducts(connection);
                        break;
                    case 2:
                        displayAllCustomers(connection);
                        break;
                    case 3:
                        displayAllCategories(connection);
                    case 0:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Input");
                }


            }} catch(SQLException e){
                System.out.println("Could not connect to DB");
                System.exit(1);
            }
    }

    public static void displayAllProducts(Connection connection){

        //we got to try to run a query and get the results with a prepared statement
        try (
                //create the prepared statement using the passed in connection
                PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT
                        ProductName,
                        UnitPrice,
                        UnitsInStock
                    FROM
                        Products
                    ORDER BY
                        ProductName
                    """
            );
        ) {
              try( ResultSet results = preparedStatement.executeQuery()) {
                  //print the results
                  printResults(results);
              }catch (SQLException e){
                  System.out.println("Results error");
              }
        }catch (SQLException e){
                    System.out.println("Could not get all the products");
                    System.exit(1);
                }
    }
    public static void displayAllCustomers(Connection connection){

        //we got to try to run a query and get the results with a prepared statement
                 try(
                     PreparedStatement preparedStatement = connection.prepareStatement("""
                             SELECT
                                 ContactName,
                                 CompanyName,
                                 City,
                                 Country,
                                 Phone
                             FROM
                                 Customers
                             ORDER BY
                                 Country
                             """
                     );
                 ){
                    try( ResultSet results = preparedStatement.executeQuery()) {

                         printResults(results);
                 }catch (SQLException e) {
                     System.out.println("Results error ");
                 }

    } catch (SQLException e){
                 System.out.println("Could not get all the customers");
                 System.exit(1);
                 }
    }
    public static void displayAllCategories(Connection connection){
        //we got to try to run a query and get the results with a prepared statement
        try (
                //create the prepared statement using the passed in connection
                PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT
                        CategoryID,
                        CategoryName,
                    FROM
                        Categories
                    ORDER BY
                        CategoryID
                    """
                );
        ) {
            try( ResultSet results = preparedStatement.executeQuery()) {
                //print the results
                printResults(results);
            }catch (SQLException e){
                System.out.println("Results error");
            }
        }catch (SQLException e){
            System.out.println("Could not get all the categories");
            System.exit(1);
        }
        System.out.println("which category ID would you like to view");
    }

    public static void printResults (ResultSet results) throws SQLException {
        //get the results from the query
        System.out.printf("%-5s %-30s %-10s %-10s\n",
                "ID", "Name", "price", "Available");
        System.out.println("-------------------------------------------------------");
        while (results.next()) {
            // column values
            int productID = results.getInt("productID");
            String productName = results.getString("productName");
            double unitsInStock = results.getDouble("unitsInStock");
            int unitPrice = results.getInt("UnitPrice");
            System.out.printf("% -5d %-30s %-10.2f %-10d\n",
                    productID, productName, unitsInStock, unitPrice);
                }
            }
}