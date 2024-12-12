package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    //Create New Account
    public Account addAccount(Account account){
        //Check for password and username requirements
        if(account.getUsername() == "" || account.password.length() < 4){
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL STATEMENT
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //setString for variable
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            
            //retrieve id that was generated when inserted
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,account.getUsername(), account.getPassword());
            }
            else 
            {
                return null;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

        //Verify an account
    public Account verifyAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //SQL STATEMENT
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //setString for variables
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account1 = new Account(rs.getInt("account_id"), rs.getString("username"),
                rs.getString("password"));
                return account1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
