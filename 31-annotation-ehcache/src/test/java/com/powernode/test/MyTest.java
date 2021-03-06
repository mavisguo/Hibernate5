package com.powernode.test;

import com.powernode.beans.Country;
import com.powernode.beans.Minister;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

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

    //查看系统默认的临时文件路径
    @Test
    public void test01() {
        String path = System.getProperty("java.io.tmpdir");
        System.out.println(path);
    }

    //证明二级缓存是存在的
    @Test
    public void test02() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            Country country = session.get(Country.class, 2);
            System.out.println("country = " + country);
            //第二次查询
            Country country2 = session.get(Country.class, 2);
            System.out.println("country2 = " + country2);
            //清空Session缓存
            session.clear();
            //第三次查询
            Country country3 = session.get(Country.class, 2);
            System.out.println("country3 = " + country3);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //演示集合缓存中所缓存的内容
    @Test
    public void test03() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            Country country = session.get(Country.class, 2);
            Set<Minister> ministers = country.getMinisters();
            System.out.println("ministers.size = " + ministers.size());
            //第二次查询
            Country country2 = session.get(Country.class, 2);
            Set<Minister> ministers2 = country2.getMinisters();
            System.out.println("ministers2.size = " + ministers2.size());
            //清空Session缓存
            session.clear();
            //第三次查询
            /*
             * 类缓存对象存放在专门的一个称为实体区域的缓存中，缓存的内容为对象的详情；
             * 集合缓存对象存放在专门的一个称为集合区域的缓存中，缓存的内容为集合中所包含对象的id。
             */
            Country country3 = session.get(Country.class, 2);
            Set<Minister> ministers3 = country3.getMinisters();
            System.out.println("ministers3.size = " + ministers3.size());
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
