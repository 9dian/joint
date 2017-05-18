package com.winbons.tech.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bill_info")
public class BillInfo {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    private String caller;
    private String callee;
    private String code;
    private Long start;
    private Long end;
    private Integer min;

    @Column(name = "order_id", length = 32)
    private String orderId;

    public BillInfo() {
    }

    public BillInfo(String caller, String callee, String code, Long start, Long end, Integer min, String orderId) {
        this.caller = caller;
        this.callee = callee;
        this.code = code;
        this.start = start;
        this.end = end;
        this.min = min;
        this.orderId = orderId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaller() {
        return this.caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return this.callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getStart() {
        return this.start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return this.end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Integer getMin() {
        return this.min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (int) (this.id ^ this.id >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BillInfo other = (BillInfo) obj;
        if (this.id != other.id)
            return false;
        return true;
    }
}