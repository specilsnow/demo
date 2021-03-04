package com.cdutcm.tcms.biz.model;

/**
 * 〈一句话功能简述〉<br> 〈弹框症状〉
 *
 * @author weihao
 * @create 2018/10/23
 * @since 1.0.0
 */
public class Symptom {
  private Long id;

  private String name;

  private Integer menuId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getMenuId() {
    return menuId;
  }

  public void setMenuId(Integer menuId) {
    this.menuId = menuId;
  }
}
