package com.winbons.tech.controller;

import com.winbons.tech.dao.entity.BillInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 耘锦电话的对接 web controller
 * @author FangXiaoqiao
 */
@RequestMapping("/cdmobile")
@Controller
public class CdMobileController {

    private Logger log = LoggerFactory.getLogger(CdMobileController.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private HibernateTemplate mysqlHibernateTemplate;

    @RequestMapping("/index")
    public void index(HttpServletResponse response) throws IOException {
        log.info("=============Enter /cdmobile/index=======================");
        response.getWriter().println("index: test page.");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/feeInfo", method = RequestMethod.POST)
    public void putFeeInfo(String num, String param, HttpServletResponse response)

            throws IOException {
        log.info("=============Enter /cdmobile/feeInfo=======================");
        log.info("Bill quantity (num): " + num);
        log.info("param: " + param);
        List<Map<String, Object>> billParam = MAPPER.readValue(param,
                new TypeReference<List<Map<String, Object>>>()
                {
                });

        for (Map<String, Object> map : billParam) {
            // public BillInfo(String caller, String callee, String code, long start, long end, int min, String orderId) {
            BillInfo bill = new BillInfo(Objects.toString(map.get("caller"), null),
                    Objects.toString(map.get("callee"), null),
                    Objects.toString(map.get("code"), null),
                    null == map.get("start") ? null :Long.parseLong(Objects.toString(map.get("start"))),
                    null == map.get("end") ? null : Long.parseLong(Objects.toString(map.get("end"))),
                    null == map.get("min") ? null : Integer.parseInt(Objects.toString(map.get("min"))),
                    Objects.toString(map.get("orderId"), null)
            );
            mysqlHibernateTemplate.save(bill);
        }

        response.getWriter().println("success");
    }

}
