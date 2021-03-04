package com.cdutcm.tcms.biz.mqtt;

import com.cdutcm.tcms.biz.model.Assist;
import com.cdutcm.tcms.biz.model.WXLoginVO;
import com.cdutcm.tcms.sys.entity.MqttData;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.entity.WebSocketData;
import com.cdutcm.tcms.sys.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.cdutcm.tcms.biz.service.WebSocket;
import com.cdutcm.tcms.itf.model.PatientWx;
import com.cdutcm.tcms.itf.service.PatientWxService;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xxx
 * Created by StoneGeek on 2018/6/5. 博客地址：http://www.cnblogs.com/sxkgeek 发布消息的回调类
 * <p>
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。 必须在回调类中实现三个方法：
 * <p>
 * public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 * <p>
 * public void connectionLost(Throwable cause)在断开连接时调用。
 * <p>
 * public void deliveryComplete(MqttDeliveryToken token)) 接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。 由
 * MqttClient.connect 激活此回调。
 */

@Component
public class PushCallback implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(PushCallback.class);

    @Autowired
    private WebSocket webSocket;
    @Autowired
    private PatientWxService patientWxService;
    @Autowired
    private UserService userService;


    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
//    log.info("【{}】", "连接断开");
        System.out.println("连接断开，可以做重连");
//		ClientMQTT client = new ClientMQTT();
//		client.start();
//		System.out.println("已重连");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public synchronized void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        String msg = new String(message.getPayload());
        // 判断是否接收到消息 然后 发送websocket消息
//        log.info("接收消息主题 :【{}】 接收消息内容 :【{}】", topic, msg);
        WebSocketData data = new WebSocketData();
        if (!"close".equals(msg) && msg != null) {
            //扫码登陆
            if ("ybm/login".equals(topic)) {
                wxLogin(msg, data);
                return;
            }
            JSONObject jsonObject = JSONObject.fromObject(msg);
            MqttData mqttData = (MqttData) JSONObject.toBean(jsonObject, MqttData.class);
            String mqttDataTopic = mqttData.getTopic();
            /*病人微信扫描获取病人姓名*/
            if ("patientSao".equals(mqttDataTopic)) {
                log.info("病人微信扫描获取病人信息");
                patientSao(data, mqttData, topic);
                return;
            }
            /*医生请求协助*/
            if ("doctorHelp".equals(mqttDataTopic)) {
                log.info("医生请求协助");
                JSONObject assistJson = JSONObject.fromObject(mqttData.getData());
                Assist assist = (Assist) JSONObject.toBean(assistJson, Assist.class);
                data.setTopic("doctorHelp");
                data.setData(assist);
                webSocket.singleSendUser(JSONObject.fromObject(data).toString(), assist.getHelper());
                return;
            }
        }
    }

    /**
     * 病人扫码获取病人信息
     */
    private void patientSao(WebSocketData data, MqttData mqttData, String topic) {
        JSONObject patientJson = JSONObject.fromObject(mqttData.getData());
        PatientWx patientWx = (PatientWx) JSONObject.toBean(patientJson, PatientWx.class);
        String wxOpenId = patientWx.getWxOpenId();
        String visitNo = patientWx.getVisitNo();
        if (patientWx != null) {
            PatientWx patientWx2 = patientWxService.getUserByWxOpenId(wxOpenId);
            if (patientWx2 == null) {
                patientWxService.save(patientWx);
            } else {
                patientWxService.bindWxOpenId(wxOpenId, visitNo);
            }
            System.out.println(patientWx);
        }
        data.setTopic("patient");
        data.setData(patientWx);
        webSocket.singleSend(JSONObject.fromObject(data).toString(), mqttData.getTimestamp());
    }

    /**
     * 扫码登录
     */
    private void wxLogin(String msg, WebSocketData data) {
        try {
            WXLoginVO vo =  new ObjectMapper()
                    .readValue(msg, WXLoginVO.class);
            String timestamp = vo.getData();
            User user = userService.getUserByOpenid(vo.getOpenId());
            if (user == null) {
                log.info("openid为【{}】的用户不存在", vo.getOpenId());
                data.setStatus("F");
                data.setOpenId(vo.getOpenId());
                webSocket.singleSend(JSONObject.fromObject(data).toString(),timestamp);
            } else {
                log.info("openid为【{}】的用户扫码登陆", vo.getOpenId());
                data.setStatus("T");
                data.setTopic("login");
                data.setData(user);
                webSocket.singleSend(JSONObject.fromObject(data).toString(), timestamp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//timestamp  /**
//   * 第一次wx,注册
//   */
//  private void wxRegister(String msg, IdWorker idWorker) {
//    WXRegistVO vo = (WXRegistVO) JSONObject
//        .toBean(JSONObject.fromObject(msg), WXRegistVO.class);
//    User user = vo.getDate();
//    user.setPwd(user.getPassword());
//    long userId = idWorker.nextId();
//    String password = user.getPassword();
//    password = new SimpleHash("md5", password, ByteSource.Util.bytes(user.getAccount()), 2)
//        .toHex();
//    user.setPassword(password);
//    user.setUserId(userId);
//    boolean b = userService.insertUser(user);
//    if (b) {
//      // 绑定诊所 药房才绑定
//      if (user.getRoleId() == 2) {
//        clinicService.insertUserAssociatedClinic(user.getAccount(), user.getClinicId());
//      }
//    }
//  }
}