package edu.uw.data.lecture5.model;

import javax.persistence.*;

/**
 * Created by credmond on 26/03/15.
 */
@Entity
@Table(name = "ORDERDETAILS" )
public class OrderDetail {
  private Integer orderNumber;
  private String productCode;
  private Integer quantityOrdered;
  private Double priceEach;
  private Short orderLineNumber;

  @Id
  @Column(name = "ORDERNUMBER" )
  public Integer getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Integer ordernumber) {
    this.orderNumber = ordernumber;
  }

  @Basic
  @Column(name = "PRODUCTCODE", nullable = true, insertable = true, updatable = true, length = 15)
  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productcode) {
    this.productCode = productcode;
  }

  @Basic
  @Column(name = "QUANTITYORDERED", nullable = true, insertable = true, updatable = true)
  public Integer getQuantityOrdered() {
    return quantityOrdered;
  }

  public void setQuantityOrdered(Integer quantityordered) {
    this.quantityOrdered = quantityordered;
  }

  @Basic
  @Column(name = "PRICEEACH", nullable = true, insertable = true, updatable = true, precision = 0)
  public Double getPriceEach() {
    return priceEach;
  }

  public void setPriceEach(Double priceeach) {
    this.priceEach = priceeach;
  }

  @Basic
  @Column(name = "ORDERLINENUMBER", nullable = true, insertable = true, updatable = true)
  public Short getOrderLineNumber() {
    return orderLineNumber;
  }

  public void setOrderLineNumber(Short orderlinenumber) {
    this.orderLineNumber = orderlinenumber;
  }

  @Override
  public String toString() {
    return "OrderDetail{" +
        "orderNumber=" + orderNumber +
        ", productCode='" + productCode + '\'' +
        ", quantityOrdered=" + quantityOrdered +
        ", priceEach=" + priceEach +
        ", orderLineNumber=" + orderLineNumber +
        '}';
  }
}
