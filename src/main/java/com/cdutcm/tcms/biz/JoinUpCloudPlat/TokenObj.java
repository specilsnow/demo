package com.cdutcm.tcms.biz.JoinUpCloudPlat;

/**
 * @author: mxq
 * @date: 2019/10/9 17:30
 * @desc:
*/
public class TokenObj {

    private String access_token;
    private long expires_in;
    private String token_type;
    private String refresh_token;
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getAccess_token() {
        return access_token;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
    public long getExpires_in() {
        return expires_in;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public String getToken_type() {
        return token_type;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public String getRefresh_token() {
        return refresh_token;
    }

}
