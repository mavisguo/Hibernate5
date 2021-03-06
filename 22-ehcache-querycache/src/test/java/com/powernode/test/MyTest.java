package com.powernode.test;

import com.powernode.beans.Country;
import com.powernode.beans.Minister;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

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

    //证明Query查询结果同样会放到一、二级缓存中
    @Test
    public void test01() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            String hql = "from Country where cid=2";
            Country country = (Country) session.createQuery(hql).uniqueResult();
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

    //证明Query查询不会从一、二级缓存中读取数据
    @Test
    public void test02() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            String hql = "from Country where cid=2";
            Country country = (Country) session.createQuery(hql).uniqueResult();
            System.out.println("country = " + country);
            //第二次查询
            Country country2 = (Country) session.createQuery(hql).uniqueResult();
            System.out.println("country2 = " + country2);
            //清空Session缓存
            session.clear();
            //第三次查询
            Country country3 = (Country) session.createQuery(hql).uniqueResult();
            System.out.println("country3 = " + country3);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //Query缓存
    @Test
    public void test03() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            String hql = "from Country where cid=2";
            Country country = (Country) session.createQuery(hql).setCacheable(true).uniqueResult();
            System.out.println("country = " + country);
            //第二次查询
            Country country2 = (Country) session.createQuery(hql).setCacheable(true).uniqueResult();
            System.out.println("country2 = " + country2);
            //清空Session缓存
            session.clear();
            //第三次查询
            Country country3 = (Country) session.createQuery(hql).setCacheable(true).uniqueResult();
            System.out.println("country3 = " + country3);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //演示Query缓存内容：从Query缓存中查找的依据不再是查询结果对象的id，而是Query查询语句；也就是说，Query查询结果存放到Query缓存时，其key为Query查询语句，value为Query查询结果。
    @Test
    public void test04() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            String hql = "from Country where cid=2";
            Country country = (Country) session.createQuery(hql).setCacheable(true).uniqueResult();
            System.out.println("country = " + country);
            //第二次查询
            String hql2 = "from Country where cid in (2)";
            Country country2 = (Country) session.createQuery(hql2).setCacheable(true).uniqueResult();
            System.out.println("country2 = " + country2);
            //清空Session缓存
            session.clear();
            //第三次查询
            String hql3 = "from Country where cid like 2";
            Country country3 = (Country) session.createQuery(hql3).setCacheable(true).uniqueResult();
            System.out.println("country3 = " + country3);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void test05() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询的结果，肯定会放到一、二级缓存
            Country country = session.get(Country.class, 2);
            System.out.println("更新前 country.name = " + country.getCname());
            //此更新绕过了一级缓存，直接更新了数据库
            String hql = "update Country set cname='AAA' where cid=2";
            session.createQuery(hql).executeUpdate();
            Country country2 = session.get(Country.class, 2);
            System.out.println("更新后 country2.name = " + country2.getCname());
            session.clear();
            /*
             * 按照之前的学习理论，这里的get()查询，会先查找一级缓存，结果是没有；再查找二级缓存，结果是有的。
             * 但为什么没有从二级缓存读取这个数据，而是从数据库中进行了查询？
             * 因为Query的executeUpdate()方法，会修改二级缓存对象中的一个属性：updateTimestamp。
             * 实际上二级缓存对象中缓存的内容，要比一级缓存内容多一个属性，即修改时间戳；一旦这个属性被修改，那么查询会不从二级缓存中读取数据，而是直接从数据库中查询。
             */
            Country country3 = session.get(Country.class, 2);
            System.out.println("二级缓存 country3.name = " + country3.getCname());
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
