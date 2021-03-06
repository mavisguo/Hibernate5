package com.powernode.test;

import com.powernode.beans.Country;
import com.powernode.beans.Minister;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void test01() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Minister minister = new Minister("aaa");

            Country country = new Country("USA");
            //minister，即many方在维护关联关系
            minister.setCountry(country);

            session.save(minister);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
