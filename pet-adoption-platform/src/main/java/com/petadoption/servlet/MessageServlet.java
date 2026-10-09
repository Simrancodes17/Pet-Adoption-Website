package com.petadoption.servlet;

import com.petadoption.exception.ValidationException;
import com.petadoption.model.Message;
import com.petadoption.model.User;
import com.petadoption.service.MessageService;
import com.petadoption.service.UserService;
import com.petadoption.util.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller managing communication between shelters and prospective adopters.
 */
@WebServlet(name = "MessageServlet", urlPatterns = {
        "/messages",
        "/shelter/messages",
        "/adopter/messages"
})
public class MessageServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private MessageService messageService;
    private UserService userService;

    @Override
    public void init() {
        this.messageService = new MessageService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getLoggedInUser(req);
        if (currentUser == null) {
            redirect(req, resp, "/login");
            return;
        }

        int recipientId = getIntParameter(req, "recipientId", -1);
        int applicationId = getIntParameter(req, "applicationId", -1);

        if (recipientId > 0) {
            // Viewing active conversation with a specific contact
            Optional<User> recipientOpt = userService.getUserById(recipientId);
            if (recipientOpt.isPresent()) {
                req.setAttribute("recipient", recipientOpt.get());
                List<Message> thread = messageService.getConversation(currentUser.getId(), recipientId, applicationId > 0 ? applicationId : null);
                req.setAttribute("conversation", thread);
                req.setAttribute("applicationId", applicationId > 0 ? applicationId : null);
            }
        }

        // All user messages for inbox listing
        List<Message> inbox = messageService.getUserMessages(currentUser.getId());
        req.setAttribute("inbox", inbox);

        if ("SHELTER".equalsIgnoreCase(currentUser.getRole())) {
            forward(req, resp, "shelter/messages.jsp");
        } else {
            forward(req, resp, "adopter/messages.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getLoggedInUser(req);
        if (currentUser == null) {
            redirect(req, resp, "/login");
            return;
        }

        int receiverId = getIntParameter(req, "receiverId", -1);
        int applicationId = getIntParameter(req, "applicationId", -1);
        String content = req.getParameter("content");

        if (receiverId <= 0) {
            setFlashError(req, "Invalid message recipient.");
            redirect(req, resp, "/messages");
            return;
        }

        try {
            Result<Message> res = messageService.sendMessage(currentUser.getId(), receiverId,
                    applicationId > 0 ? applicationId : null, content);
            if (res.isSuccess()) {
                setFlashSuccess(req, "Message delivered successfully!");
            } else {
                setFlashError(req, res.getMessage());
            }
        } catch (ValidationException e) {
            setFlashError(req, e.getMessage());
        }

        String backUrl = "/messages?recipientId=" + receiverId;
        if (applicationId > 0) {
            backUrl += "&applicationId=" + applicationId;
        }
        redirect(req, resp, backUrl);
    }
}
