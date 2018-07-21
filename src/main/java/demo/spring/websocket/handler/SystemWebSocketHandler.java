package demo.spring.websocket.handler;

import demo.spring.websocket.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @@
 */
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final List<WebSocketSession> users = new ArrayList<WebSocketSession>();

    private static final Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    //连接已建立
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        users.add(session);
        logger.debug("连接已建立 {}", session.getId());
    }

    //消息接收处理
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> ws_msg)
            throws Exception {
        String text = ws_msg.getPayload().toString();
        sendMessageToUsers(new TextMessage(text));
        //消息处理
        logger.debug("消息处理 {} {}", text, session.getId());

    }

    //连接已关闭
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        users.remove(session);
        logger.debug("连接已关闭 {}", session.getId());
    }


    //异常处理
    public void handleTransportError(WebSocketSession session, Throwable e)
            throws Exception {
        if(session.isOpen()) session.close();
        users.remove(session);
    }

    public boolean supportsPartialMessages() {
        return true;
    }

    /**
     * 自定义接口
     * 给所有在线用户发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessageToUsers(TextMessage message) throws IOException {
        for (WebSocketSession user : users) {
            if (user.isOpen()) user.sendMessage(message);
        }
    }

    /**
     * 自定义接口
     * 给某个用户发送消息
     * @param userName
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(String userName, TextMessage message) throws IOException {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Constants.WEBSOCKET_USERNAME).equals(userName)) {
                if (user.isOpen()) {
                    user.sendMessage(message);
                    break;
                }
            }
        }
    }

}
