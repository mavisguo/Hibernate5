package com.powernode.test;

import com.powernode.beans.Country;
import com.powernode.beans.Minister;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class MyTest {

    @Test
    public void test00() {
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

            Minister minister4 = new Minister("ddd");
            Minister minister5 = new Minister("eee");
            Country country2 = new Country("UK");
            country2.getMinisters().add(minister4);
            country2.getMinisters().add(minister5);

            session.save(country);
            session.save(country2);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void test01() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Country country = session.get(Country.class, 2);
            Set<Minister> ministers = country.getMinisters();
            System.out.println("ministers.size = " + ministers.size());
            for (Minister minister : ministers) {
                System.out.println(minister);
            }
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
            String hql = "from Country";
            List<Country> countries = session.createQuery(hql).list();
            for (Country country : countries) {
                Set<Minister> ministers = country.getMinisters();
                System.out.println("ministers.size = " + ministers.size());
                for (Minister minister : ministers) {
                    System.out.println(minister);
                }
            }
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
