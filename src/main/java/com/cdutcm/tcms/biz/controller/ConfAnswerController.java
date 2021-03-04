package com.cdutcm.tcms.biz.controller;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.model.ConfAnswer;
import com.cdutcm.tcms.biz.model.IndivEnum;
import com.cdutcm.tcms.biz.model.UserIndividual;
import com.cdutcm.tcms.biz.service.ConfAnswerService;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfAnswerController {
    @Autowired
    ConfAnswerService confAnswerService;

    @Autowired
    UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/findUserIndividual")
    public UserIndividual findUserIndividual(@RequestParam(value = "account") String account) {
        User user = userService.getUserByAccount(account);
        String userId = String.valueOf(user.getUserId());
//        System.out.println(userId);
        UserIndividual userIndividual = new UserIndividual();
        Object o = redisTemplate.opsForValue().get(user.getAccount() + "userIndividualBaseData");
        if(!StringUtil.objIsEmpty(o)){
            return (UserIndividual)o;
        }
        //设置theme
        ConfAnswer confAnswer = confAnswerService.findByUserIdAndChoiceId(userId, IndivEnum.THEME.getChoiceId());
        if (confAnswer != null) {
            userIndividual.setTheme(confAnswer.getChoiceAnswer());
        }else {
            userIndividual.setTheme(confAnswerService.findByChoiceIdAndCtype(IndivEnum.THEME.getChoiceId(),"S").getChoiceAnswer());
        }
        //设置input
        ConfAnswer confAnswer1 = confAnswerService.findByUserIdAndChoiceId(userId,IndivEnum.INPUT.getChoiceId());
        if (confAnswer1 != null){
            userIndividual.setInput(confAnswer1.getChoiceAnswer());
        }else {
            userIndividual.setInput(confAnswerService.findByChoiceIdAndCtype(IndivEnum.INPUT.getChoiceId(),"S").getChoiceAnswer());
        }
        return userIndividual;
    }

    @RequestMapping(value = "/findConfAnswerByUserIdAndChoiceId")
    public ConfAnswer findByUserIdAndChoiceId(@RequestParam(value = "userId") String userId, @RequestParam(value = "choiceId") String choiceId) {
        return confAnswerService.findByUserIdAndChoiceId(userId, choiceId);
    }

    @RequestMapping(value = "/findConfAnswerByChoiceIdAndCtype")
    public ConfAnswer findByChoiceIdAndCtype(@RequestParam(value = "choiceId") String choiceId, @RequestParam(value = "ctype") String ctype) {
//        System.out.println(1212121);
        return confAnswerService.findByChoiceIdAndCtype(choiceId, ctype);
    }
}
