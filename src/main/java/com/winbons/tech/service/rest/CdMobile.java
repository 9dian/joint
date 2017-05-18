package com.winbons.tech.service.rest;

import com.winbons.tech.dao.entity.BillInfo;
import com.winbons.tech.domain.viewmodel.BillModel;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Path("/")
@Service
@Transactional(readOnly = false)
public class CdMobile {
    private Logger log = LoggerFactory.getLogger(CdMobile.class);

    @Autowired
    protected HibernateTemplate mysqlHibernateTemplate;

    @GET
    @Path("/index")
    public String index() {
        return "RESTful index.";
    }

    @POST
    @Path("/feeInfo")
    public String putFeeInfo(BillModel model) {
        this.log.info("=============Enter /CdMobile/putFeeInfo=======================");
        this.log.info("Bill quantity (num): " + model.getNum());
        this.log.info("param: " + model.getParam());

        for (Map<String, Object> map : model.getParam()) {
            BillInfo bill = new BillInfo(Objects.toString(map.get("caller"), null),
                    Objects.toString(map.get("callee"), null), Objects.toString(map.get("code"), null),
                    null == map.get("start") ? null : Long.valueOf(Long.parseLong(Objects.toString(map.get("start")))),
                    null == map.get("end") ? null : Long.valueOf(Long.parseLong(Objects.toString(map.get("end")))),
                    null == map.get("min") ? null : Integer.valueOf(Integer.parseInt(Objects.toString(map.get("min")))),
                    Objects.toString(map.get("orderId"), null));

            this.mysqlHibernateTemplate.save(bill);
        }

        return "OK";
    }

    @POST
    @Path("/feeInfo/{num}")
    public String putFeeInfo(@PathParam("num") int num, List<Map<String, Object>> param) {
        this.log.info("=============Enter /CdMobile/putFeeInfo(int, List)=======================");
        this.log.info("Bill quantity (num): " + num);
        this.log.info("param: " + param);

        for (Map<String, Object> map : param) {
            BillInfo bill = new BillInfo(Objects.toString(map.get("caller"), null),
                    Objects.toString(map.get("callee"), null), Objects.toString(map.get("code"), null),
                    null == map.get("start") ? null : Long.valueOf(Long.parseLong(Objects.toString(map.get("start")))),
                    null == map.get("end") ? null : Long.valueOf(Long.parseLong(Objects.toString(map.get("end")))),
                    null == map.get("min") ? null : Integer.valueOf(Integer.parseInt(Objects.toString(map.get("min")))),
                    Objects.toString(map.get("orderId"), null));

            this.mysqlHibernateTemplate.save(bill);
        }

        return "OK";
    }
}