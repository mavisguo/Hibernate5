package com.powernode.test;

import com.powernode.beans.Country;
import com.powernode.beans.Minister;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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
            Minister minister2 = new Minister("bbb");
            Minister minister3 = new Minister("ccc");

            Set<Minister> ministers = new HashSet<Minister>();
            ministers.add(minister);
            ministers.add(minister2);
            ministers.add(minister3);

            Country country = new Country("USA");
            //country，即one方在维护关联关系
            country.setMinisters(ministers);

            session.save(minister);
            session.save(minister2);
            session.save(minister3);
            session.save(country);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void test02() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Minister minister = new Minister("aaa");
            Minister minister2 = new Minister("bbb");
            Minister minister3 = new Minister("ccc");

            Set<Minister> ministers = new HashSet<Minister>();
            ministers.add(minister);
            ministers.add(minister2);
            ministers.add(minister3);

            Country country = new Country("USA");
            //country，即one方在维护关联关系
            country.setMinisters(ministers);

            session.save(country);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void test03() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Minister minister = new Minister("aaa");
            Minister minister2 = new Minister("bbb");
            Minister minister3 = new Minister("ccc");

            Country country = new Country("USA");
            //country，即one方在维护关联关系
            country.getMinisters().add(minister);
            country.getMinisters().add(minister2);
            country.getMinisters().add(minister3);

            session.save(country);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
