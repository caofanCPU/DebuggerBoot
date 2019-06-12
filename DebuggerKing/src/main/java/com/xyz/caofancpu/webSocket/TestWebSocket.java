package com.xyz.caofancpu.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FileName: TestWebSocket
 * Author:   caofanCPU
 * Date:     2018/11/21 16:35
 */
@ServerEndpoint(value = "/loginOrNot/testWebSocket/{uniqueId}")
@Component
public class TestWebSocket {
    
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(TestWebSocket.class);
    
    // 静态变量，用来记录当前在线连接数, 需要线程安全的设计
    private static int onlineCount = 0;
    
    // J.U.C.map，用来存放每个客户端对应的TestWebSocket对象。
    private static ConcurrentHashMap<Long, TestWebSocket> webSocketMap = new ConcurrentHashMap<>();
    
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    
    public static synchronized void addOnlineCount() {
        TestWebSocket.onlineCount++;
    }
    
    public static synchronized void subOnlineCount() {
        TestWebSocket.onlineCount--;
    }
    
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "uniqueId") Long uniqueId, Session session) {
        this.session = session;
        webSocketMap.put(uniqueId, this);
        addOnlineCount();
        logger.info("uniqueId={}加入, 当前连接数: {}", uniqueId, getOnlineCount());
        sendMessage("WebSocket服务连接成功!");
    }
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "uniqueId") Long uniqueId) {
        webSocketMap.remove(uniqueId);
        subOnlineCount();
        logger.info("客户端uniqueId={}连接关闭, 当前连接数: {}", uniqueId, getOnlineCount());
    }
    
    /**
     * 收到客户端消息后群发消息
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(@PathParam(value = "orderId") Long uniqueId, String message, Session session) {
        logger.info("接收到客户端uniqueId={}的消息:\n{}", uniqueId, message);
        webSocketMap.keySet().stream()
                .filter(Objects::nonNull)
                .forEach(itemId -> webSocketMap.get(itemId).sendMessage(message));
    }
    
    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(@PathParam(value = "uniqueId") Long uniqueId, Session session, Throwable error) {
        logger.error("客户端uniqueId={}连接发生错误!, 原因: {}", uniqueId, error.getMessage());
    }
    
    public Boolean sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            return Boolean.TRUE;
        } catch (IOException e) {
            logger.error("向客户端发送消息失败!\n消息内容: {}", message);
        }
        return Boolean.FALSE;
        //this.session.getAsyncRemote().sendText(message);
    }
    
    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     *
     * @param message
     * @param uniqueId
     * @throws IOException
     */
    public void sendToUser(String message, Long uniqueId) {
        if (Objects.isNull(webSocketMap.get(uniqueId))) {
            logger.error("客户端uniqueId={}离线!\n消息内容: {}", uniqueId, message);
            return;
        }
        webSocketMap.get(uniqueId).sendMessage(message);
        logger.info("客户端uniqueId={}接收消息!\n消息内容: {}", uniqueId, message);
    }
    
    /**
     * 发送信息给所有人
     *
     * @param message
     * @throws IOException
     */
    public void sendToAll(String message) {
        webSocketMap.keySet().stream()
                .filter(Objects::nonNull)
                .forEach(orderId -> webSocketMap.get(orderId).sendMessage(message));
    }
    
}
