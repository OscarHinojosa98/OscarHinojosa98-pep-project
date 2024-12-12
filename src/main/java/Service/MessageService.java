package Service;

import DAO.MessageDao;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDao messageDao;
    
    
    public MessageService(){
        messageDao = new MessageDao();
    }

    public MessageService(MessageDao messageDao){
        this.messageDao = messageDao;
    }

    // Creates a message
    public Message createMessage(Message message){
        return messageDao.addMessage(message);
    }

    // Deletes a message
    public Message deleteMessage(int id){
        // Looks for specific message by if and returns null if not found
        Message tmp = messageDao.getMessageById(id);
        // If found, remove message then return message 
        if(tmp != null){
        messageDao.removeMessage(tmp);
        return tmp;
        }
        else
        {
            return null;
        }
    }

    // Updates a message
    public Message updateMessage(int id, String updtMsg){
        // Looks for specific message by if and returns null if not found
        Message tmp = messageDao.getMessageById(id);
        //Checks for message_text requirements and if message was found. If found, edits message then returns it
        if(tmp != null && updtMsg.length() > 0 && updtMsg.length() < 256){
        messageDao.editMessage(tmp, updtMsg);
        return messageDao.getMessageById(id);
        }
        else
        {
            return null;
        }
    }

    // Gets a specific message by its id
    public Message getMessageById(int message_id){
        return messageDao.getMessageById(message_id);
    }

    // Gets all messages
    public List<Message> getAllMessages(){
        return messageDao.getAllMessages();
    }

    //Gets all messages by a specific user
    public List<Message> getAllUsersMessages(int user_id){
        return messageDao.getMessagesByUser(user_id);
    }

}
