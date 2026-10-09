<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Shelter Messages" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-4">
    <div class="mb-4">
        <h3 class="fw-bold mb-0">Adopter Communications</h3>
        <p class="text-muted small mb-0">Direct messaging with prospective pet parents and applicants</p>
    </div>

    <div class="row g-4">
        <!-- Message History / Inbox List -->
        <div class="col-lg-4">
            <div class="card card-paw shadow-sm h-100 border-0">
                <div class="card-header bg-white py-3 border-bottom">
                    <h6 class="fw-bold mb-0 text-dark"><i class="bi bi-inbox-fill text-primary me-2"></i> All Messages (${inbox.size()})</h6>
                </div>
                <div class="card-body p-0" style="max-height: 520px; overflow-y: auto;">
                    <c:choose>
                        <c:when test="${empty inbox}">
                            <div class="p-4 text-center text-muted small">No message history yet.</div>
                        </c:when>
                        <c:otherwise>
                            <div class="list-group list-group-flush">
                                <c:forEach var="msg" items="${inbox}">
                                    <c:set var="otherUserId" value="${msg.senderId == sessionScope.currentUser.id ? msg.receiverId : msg.senderId}" />
                                    <c:set var="otherUserName" value="${msg.senderId == sessionScope.currentUser.id ? msg.receiverName : msg.senderName}" />
                                    <a href="${pageContext.request.contextPath}/messages?recipientId=${otherUserId}&applicationId=${msg.applicationId}"
                                       class="list-group-item list-group-item-action p-3 ${recipient != null and recipient.id == otherUserId ? 'active' : ''}">
                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                            <strong class="small"><c:out value="${otherUserName}" /></strong>
                                            <span class="small opacity-75" style="font-size: 0.7rem;"><c:out value="${msg.createdAt}" /></span>
                                        </div>
                                        <div class="small text-truncate opacity-75"><c:out value="${msg.content}" /></div>
                                        <c:if test="${not empty msg.petName}">
                                            <span class="badge bg-light text-dark border mt-1" style="font-size: 0.65rem;">
                                                Pet: <c:out value="${msg.petName}" />
                                            </span>
                                        </c:if>
                                    </a>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Conversation & Composer -->
        <div class="col-lg-8">
            <div class="card card-paw shadow-sm h-100 border-0">
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="fw-bold mb-0 text-dark">
                            <i class="bi bi-chat-square-text text-danger me-2"></i>
                            <c:choose>
                                <c:when test="${not empty recipient}">
                                    Conversation with <c:out value="${recipient.name}" /> (${recipient.role})
                                </c:when>
                                <c:otherwise>Select a message thread</c:otherwise>
                            </c:choose>
                        </h6>
                    </div>
                </div>

                <div class="card-body p-4 d-flex flex-column">
                    <c:choose>
                        <c:when test="${not empty recipient}">
                            <!-- Chat Bubble Feed -->
                            <div class="chat-container flex-grow-1 mb-3">
                                <c:choose>
                                    <c:when test="${empty conversation}">
                                        <div class="text-center text-muted p-4 small">
                                            Start a conversation with <c:out value="${recipient.name}" /> below.
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="chat" items="${conversation}">
                                            <c:set var="isMe" value="${chat.senderId == sessionScope.currentUser.id}" />
                                            <div class="chat-bubble ${isMe ? 'chat-bubble-sent' : 'chat-bubble-received'} shadow-sm">
                                                <div class="small fw-semibold mb-1 ${isMe ? 'text-white-50' : 'text-primary'}">
                                                    <c:out value="${isMe ? 'You (Shelter)' : chat.senderName}" />
                                                </div>
                                                <div><c:out value="${chat.content}" /></div>
                                                <div class="text-end text-muted mt-1" style="font-size: 0.68rem;">
                                                    <c:out value="${chat.createdAt}" />
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- Send Message Form -->
                            <form action="${pageContext.request.contextPath}/messages" method="post" class="mt-auto">
                                <input type="hidden" name="receiverId" value="${recipient.id}">
                                <c:if test="${not empty applicationId}">
                                    <input type="hidden" name="applicationId" value="${applicationId}">
                                </c:if>

                                <div class="input-group">
                                    <textarea class="form-control" name="content" rows="2" required placeholder="Type your message to applicant..."></textarea>
                                    <button class="btn btn-paw-primary px-4" type="submit">
                                        <i class="bi bi-send-fill"></i> Send
                                    </button>
                                </div>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <div class="p-5 text-center text-muted m-auto">
                                <i class="bi bi-chat-dots fs-1 text-secondary mb-2"></i>
                                <h6 class="mt-2 fw-bold text-dark">No conversation selected</h6>
                                <p class="small mb-0">Select an adopter from the left or message them directly from an application card.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
