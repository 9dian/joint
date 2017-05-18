package com.winbons.tech.domain.viewmodel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BillModel {
    private Integer num;
    private List<Map<String, Object>> param = Collections.emptyList();

    public BillModel() {
    }

    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Map<String, Object>> getParam() {
        return this.param;
    }

    public void setParam(List<Map<String, Object>> param) {
        this.param = param;
    }

    public BillModel(Integer num, List<Map<String, Object>> param) {
        this.num = num;
        this.param = param;
    }
}