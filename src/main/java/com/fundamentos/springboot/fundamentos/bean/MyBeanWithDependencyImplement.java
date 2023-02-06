package com.fundamentos.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency {

    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);

    private MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency(){

        LOGGER.info("hemos ingresado al metodo prinwithdependency");
        int numero = 1;
        LOGGER.debug("el numero enviamo domo parametro a la dependencia operation es : " + numero);
        System.out.println(myOperation.suma(numero));
        System.out.println("hola desde la implmentacion de un bean con dependencia ");
    }
}
