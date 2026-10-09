package com.petadoption.dao;

import com.petadoption.model.Message;

import java.util.List;

/**
 * Data Access Object interface for shelter-adopter direct messaging.
 */
public interface MessageDAO extends DAO<Message> {

    List<Message> findByUserId(int userId);

    List<Message> findConversation(int user1Id, int user2Id, Integer applicationId);

    List<Message> findByApplicationId(int applicationId);

    int countMessages();
}
