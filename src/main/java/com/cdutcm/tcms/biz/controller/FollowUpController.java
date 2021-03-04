package com.cdutcm.tcms.biz.controller;

import com.cdutcm.tcms.biz.model.FollowUp;
import com.cdutcm.tcms.biz.service.FollowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年05月07日 16:56
 */
@RestController
@RequestMapping("/follow")
public class FollowUpController {
    @Autowired
    private FollowUpService followUpService;

    @RequestMapping("/add")
    public void add(FollowUp followUp){
        followUpService.insert(followUp);
    }

    @RequestMapping("/del")
    public void del(Long id){
        followUpService.delete(id);
    }

    @RequestMapping("/choose")
    public void choose(FollowUp followUp){
        followUpService.choose(followUp);
    }
}
