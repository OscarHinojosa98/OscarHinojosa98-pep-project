package Controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::createAccountHandler);
        app.post("login", this::verifyLoginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Get account provided in context body
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        //If added successfully return account and 200 status
        if (addedAccount!=null)
        {
        context.json(mapper.writeValueAsString(addedAccount));
        context.status(200);
        }
        else
        {
            context.status(400);
        }
    }

    private void verifyLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        // Get account provided in context body
        Account account = mapper.readValue(context.body(), Account.class);
        Account authorizedAccount = accountService.authorizeLogin(account);
        //If authorized successfully return account and 200 status
        if (authorizedAccount!=null)
        {
        context.json(mapper.writeValueAsString(authorizedAccount));
        context.status(200);
        }
        else
        {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Get message provided in context body
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        //If added successfully return message and 200 status
        if (addedMessage!=null)
        {
        context.json(mapper.writeValueAsString(addedMessage));
        context.status(200);
        }
        else
        {
            context.status(400);
        }
    }

    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Get message_id provided by path parameter "message_id"
        int id = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message deletedMessage = messageService.deleteMessage(id);
        //If deleted successfully return message and 200 status
        if (deletedMessage!=null)
        {
        context.json(mapper.writeValueAsString(deletedMessage));
        context.status(200);
        }
        else
        {
            context.status(200);
        }
    }

    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Get message_text provided in context body
        Map<String, String> body = mapper.readValue(context.body(), Map.class);
        String str = body.get("message_text");
        // Get message_id provided by path parameter "message_id"
        int id = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message updatedMessage = messageService.updateMessage(id, str);
        // If updated successfully return message and 200 status
        if (updatedMessage!=null)
        {
        context.json(mapper.writeValueAsString(updatedMessage));
        context.status(200);
        }
        else
        {
            context.status(400);
        }
    }

    public void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        // Returns all messages 
        context.json(messages);
    }

    public void getAllMessagesByUserHandler(Context context){
        // Get account_id provided by path parameter "account_id"
        int id = Integer.parseInt(Objects.requireNonNull(context.pathParam("account_id")));
        List<Message> messages = messageService.getAllUsersMessages(id);
        // Returns all messages by a user
        context.json(messages);
        context.status(200);
    }

    public void getMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        // Get message_id provided by path parameter "message_id"
        int id = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message foundMessage = messageService.getMessageById(id);
        //If found successfully return message and 200 status
        if (foundMessage!=null)
        {
        context.json(mapper.writeValueAsString(foundMessage));
        }
        else
        {
            context.json("");
        }
        context.status(200);
    }


}