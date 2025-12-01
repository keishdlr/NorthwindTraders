package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class NorthwindTraders {

    public static void main(String[] args) throws SQLException {

        // did we pass in a username and password
        // if not the app must die
        if (args.length != 2){
            System.out.println("Application needs two args to run: A username and password for the DB");
            System.exit(1);
        }
        //get the username and password
        String username = args[0];
        String password = args[1];



        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind",username, password)){
                // create the prepared statement using the query
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

                //get the results from the query
                ResultSet results = preparedStatement.executeQuery()


            //print the results
            printResults(results);

        }catch (SQLException e){
            System.out.println("Could not get all the products");
            System.exit(1);
        }

    }
    public static void printResults(ResultSet results) throws SQLException{
        // get the metadata so we have access to the fields names
        ResultSetMetaData metaData = results.getMetaData();
        //get the number of rows returned
        int columnCount = metaData.getColumnCount();

        //This is looping over all the results from the DB
        while (results.next()){

            //loop over each column in the row and display the data
            for (int i = 1; i <= columnCount; i++){
                //gets the current column name
                String columnName = metaData.getColumnName(i);
                //get the current column value
                String value = results.getString(i);
                //print out the column name and the column value
                System.out.println(columnName + " " + value + " ");
            }

            //print an empty line to make the results prettier
            System.out.println();
        }
    }

}
