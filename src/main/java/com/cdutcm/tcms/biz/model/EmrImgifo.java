package com.cdutcm.tcms.biz.model;

/**
 * @author: mxq
 * @date: 2019/10/12 10:09
 * @desc: 病历图片信息
 */


public class EmrImgifo {
    private  Long id;
    private  String visitNo;
    private  String imgUrl;
    private  String imgName;
    public  Long  getId(){
        return  this.id;
    };
    public  void  setId(Long id){
        this.id=id;
    }

    public  String  getVisitNo(){
        return  this.visitNo;
    };
    public  void  setVisitNo(String visitNo){
        this.visitNo=visitNo;
    }

    public  String  getImgUrl(){
        return  this.imgUrl;
    };
    public  void  setImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }

    public  String  getImgName(){
        return  this.imgName;
    };
    public  void  setImgName(String imgName){
        this.imgName=imgName;
    }

    public EmrImgifo() {
    }

    public EmrImgifo(String visitNo, String imgUrl, String imgName) {
        this.visitNo = visitNo;
        this.imgUrl = imgUrl;
        this.imgName = imgName;
    }
}