package com.powernode.test;

import com.powernode.beans.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void testSave() {
        //1.加载主配置文件：hibernate.cfg.xml
        Configuration configure = new Configuration().configure();
        //2.创建Session工厂对象
        SessionFactory sessionFactory = configure.buildSessionFactory();
        //3.获取Session对象
        Session session = sessionFactory.getCurrentSession();
        try {
            //4.开启事务
            session.beginTransaction();
//            session.getTransaction().begin();
            //5.执行操作
            Student student = new Student("张三", 23, 93.5);
            System.out.println("save()之前，student = " + student);
            /*
             * save()方法执行的过程：
             * 1)向MySQL发送一条信息，告诉MySQL要执行插入了，MySQL生成一个id，传回来。
             * 2)接收到MySQL传回来的id，并使用该id初始化Student对象的id属性。
             * 3)现在的Student对象已经具有id，那么Session缓存就可以管理它了：将Student对象的id作为key，Student对象的引用作为value，放入到Session缓存中。
             * 注意：Session缓存的本质，实际是一个Map，其key为被管理对象的id，value为被管理对象的引用。也就是说，只有id属性不为null的对象才有可能被Session管理。
             * 一个对象被Session管理，就意味着这个对象被放入到了Session缓存这个Map中。
             * 当对象由持久态转换为游离态时，实际就是将Map中的该对象删除了。
             * 当对象由游离态转换为持久态时，实际就是先将该对象放入到Session的这个Map中，再对其进行修改。
             */
            session.save(student);
            System.out.println("save()之后，student = " + student);
            //6.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //7.事务回滚
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void testSave2() {
        //1.加载主配置文件：xxx.cfg.xml
        Configuration configure = new Configuration().configure("xxx.cfg.xml");
        //2.创建Session工厂对象
        SessionFactory sessionFactory = configure.buildSessionFactory();
        //3.获取Session对象
        Session session = sessionFactory.getCurrentSession();
        try {
            //4.开启事务
            session.beginTransaction();
            //5.执行操作
            Student student = new Student("张三", 23, 93.5);
            session.save(student);
            //6.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //7.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void test03() {
        //1.加载主配置文件：hibernate.cfg.xml
        Configuration configure = new Configuration().configure();
        //2.创建Session工厂对象
        SessionFactory sessionFactory = configure.buildSessionFactory();
        //3.获取Session对象
        Session session1 = sessionFactory.openSession();
        Session session2 = sessionFactory.openSession();
        System.out.println((session1 == session2));
    }

    @Test
    public void test04() {
        //1.加载主配置文件：hibernate.cfg.xml
        Configuration configure = new Configuration().configure();
        //2.创建Session工厂对象
        SessionFactory sessionFactory = configure.buildSessionFactory();
        //3.获取Session对象
        Session session1 = sessionFactory.getCurrentSession();
        Session session2 = sessionFactory.getCurrentSession();
        System.out.println((session1 == session2));
    }

}
