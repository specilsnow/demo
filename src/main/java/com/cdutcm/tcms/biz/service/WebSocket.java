package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.Assist;
import com.cdutcm.tcms.sys.entity.WebSocketData;
import com.cdutcm.tcms.sys.mapper.UserMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.server.PathParam;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Auther: Mxq
 * @Date: 2018/8/28 09:13
 * @description:
 */
@Component
@ServerEndpoint("/websocket/{userid}/{timestamp}")
public class WebSocket {

  private String timestamp;
  private String account;

  private Session session;
  private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
  private static Map<String, WebSocket> webSocketMap = new ConcurrentHashMap<String, WebSocket>();
  private static Map<String, WebSocket> webSocketMapUser = new ConcurrentHashMap<String, WebSocket>();
  private static final Logger log = LoggerFactory.getLogger(WebSocket.class);
  private static int onlineCount = 0; // 记录连接数目

  public static Map<String, WebSocket> getWebSocketMapUser() {
    return webSocketMapUser;
  }

  @OnOpen
  public void onOpen(Session session, @PathParam(value = "timestamp") String timestamp,@PathParam(value = "userid") String account) {
//    System.out.println("onOpen");
    this.timestamp = timestamp;
    this.session = session;
    webSockets.add(this);
    webSocketMap.put(timestamp, this);
    if (!"login".equals(account)){
    webSocketMapUser.put(account, this);
    }
//    log.info("【websocket消息:有新的连接，总数{}】", webSockets.size());
  }

  @OnClose
  public void onClose(@PathParam(value = "timestamp") String timestamp,@PathParam(value = "userid") String account) {
//    System.out.println("onClose");
    webSockets.remove(this);
    webSocketMap.remove(timestamp);
    if (!"login".equals(account)){
      webSocketMapUser.remove(account);
    }
//    log.info("【websocket消息连接断开，总数{}】", webSockets.size());
  }

  @OnMessage
  public void onMessage(String message) {
    WebSocketData webSocketData = new WebSocketData();
   Class clazz = UserMapper.class;

    Assist assist = (Assist) JSONObject.toBean(JSONObject.fromObject(message), Assist.class);
    webSocketData.setTopic("doctorHelp");
    webSocketData.setData(assist);
    singleSendUser(JSONObject.fromObject(webSocketData).toString(),assist.getHelper());
//    log.info("【websocket消息，收到客户端发来的消息{}】", message);
  }

  public void sendMessage(String message) {
//    System.out.println("sendMessage");
    for (WebSocket webSocket : webSockets) {
//      log.info("【[websocket消息]，广播消息，message={}】", message);
      try {
        webSocket.session.getBasicRemote().sendText(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 发送给指定浏览器 （）
   *
   * @param timestamp 浏览器标识
   */
  public void singleSend(String message, String timestamp) {
    WebSocket webSocket1 = webSocketMap.get(timestamp);
    try {
      webSocket1.session.getBasicRemote().sendText(message);
    } catch (IOException e) {
      e.printStackTrace();
//      log.info("【消息发送失败！】" + e.getMessage());
    }
  }

  /**
   * 申请远程协助
   * @param message
   * @param account
   */
  public void singleSendUser(String message, String account) {
    WebSocket webSocket = webSocketMapUser.get(account);
    try {
      webSocket.session.getBasicRemote().sendText(message);
    } catch (IOException e) {
      e.printStackTrace();
//      log.info("【消息发送失败！】" + e.getMessage());
    }
  }
}
