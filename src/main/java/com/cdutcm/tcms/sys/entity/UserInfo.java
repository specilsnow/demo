/**
 * @Title: FamousHerbalist.java
 * @Package com.cdutcm.tcms.sys.entity
 * @Description: TODO
 * @author 魏浩
 * @date 2018年9月6日
 */
package com.cdutcm.tcms.sys.entity;

import com.cdutcm.core.page.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;


/**
 * @author 魏浩
 * @date 2018年9月6日
 * @ClassName: FamousHerbalist
 * @Description: 名医信息
 *
 */
public class UserInfo {

  private Long id;
  private String account;
  private String name;
  private String description;
  private Integer status;
  private String title;
  private String icon;
  private String specialty;
  private String accounts;
  private Page page;
  private String telephone;

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }




  public String getAccounts() {
    return accounts;
  }

  public void setAccounts(String accounts) {
    this.accounts = accounts;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public String getSpecialty() {
    return specialty;
  }

  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }
}
