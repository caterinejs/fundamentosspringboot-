package com.fundamentos.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanImplementCate implements MyBeanWithDependencyCate {

    private final Log LOGGER = LogFactory.getLog( MyBeanImplementCate.class);



    public void saludoCate() {
        System.out.println("holaaaa xxxxs");

        LOGGER.info("fafasfasfas");


    }
}
