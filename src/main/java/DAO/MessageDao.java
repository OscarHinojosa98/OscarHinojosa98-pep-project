package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    // Create New Message
    public Message addMessage(Message message){
        // Check for message_text requirements
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255){
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL STATEMENT
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // setString or setInt for variables
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
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
    // Delete message
    public void removeMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL STATEMENT
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // setInt for variables
            preparedStatement.setInt(1, message.getMessage_id());

            preparedStatement.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Edit message
    public void editMessage(Message message, String newMsg){
               
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL STATEMENT
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // setString or setInt for variables
            preparedStatement.setString(1, newMsg);
            preparedStatement.setInt(2, message.getMessage_id());

            preparedStatement.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }


    // Get all messages in table
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            // SQL STATEMENT
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Get a messabe by its ID
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL STATEMENT
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // setInt for variables
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Get all messages posted by a user
    public List<Message> getMessagesByUser(int user_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //SQL STATEMENT
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //setInt for variables
            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
}
