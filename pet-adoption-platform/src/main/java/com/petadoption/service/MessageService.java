package com.petadoption.service;

import com.petadoption.dao.MessageDAO;
import com.petadoption.dao.impl.MessageDAOImpl;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.Message;
import com.petadoption.thread.NotificationThreadPool;
import com.petadoption.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service managing direct messaging between shelters and potential adopters.
 * Dispatches async notifications when new messages arrive.
 */
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final MessageDAO messageDAO;
    private final NotificationThreadPool notificationPool;

    public MessageService() {
        this.messageDAO = new MessageDAOImpl();
        this.notificationPool = NotificationThreadPool.getInstance();
    }

    public MessageService(MessageDAO messageDAO, NotificationThreadPool notificationPool) {
        this.messageDAO = messageDAO;
        this.notificationPool = notificationPool;
    }

    public Result<Message> sendMessage(int senderId, int receiverId, Integer applicationId, String content) throws ValidationException {
        if (content == null || content.trim().isEmpty()) {
            throw new ValidationException("Message text cannot be empty.");
        }

        if (senderId == receiverId) {
            throw new ValidationException("Cannot send a message to yourself.");
        }

        Message msg = new Message();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setApplicationId(applicationId != null && applicationId > 0 ? applicationId : null);
        msg.setContent(content.trim());

        int id = messageDAO.save(msg);
        if (id > 0) {
            msg.setId(id);
            // Retrieve full joined entity for accurate notification info
            messageDAO.findById(id).ifPresent(notificationPool::sendNotificationAsync);

            return Result.success("Message sent successfully.", msg);
        }

        return Result.failure("Failed to send message.");
    }

    public List<Message> getUserMessages(int userId) {
        return messageDAO.findByUserId(userId);
    }

    public List<Message> getConversation(int user1Id, int user2Id, Integer applicationId) {
        return messageDAO.findConversation(user1Id, user2Id, applicationId);
    }

    public List<Message> getApplicationMessages(int applicationId) {
        return messageDAO.findByApplicationId(applicationId);
    }
}
