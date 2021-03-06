package com.powernode.test;

import com.powernode.beans.Student;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void testSave() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = new Student("张三", 23, 93.5);
            session.save(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void testPersist() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = new Student("张三", 23, 93.5);
            session.persist(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void testDelete() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = new Student();
            student.setId(1);
            //删除的条件是对象要具有id
            session.delete(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void testUpdate() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = new Student("李四", 24, 94.5);
            student.setId(2);
            //修改的条件是对象要具有id
            session.update(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void testSaveOrUpdate() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = new Student("李小四", 24, 94.5);
            student.setId(2);
            //被操作对象若存在id，则执行update，否则执行save
            session.saveOrUpdate(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void testGet() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = session.get(Student.class, 2);
            System.out.println(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    /**
     * get()与load()方法的共同点：根据id加载对象
     * get()与load()方法的区别：get()：若加载的对象不存在，则返回null；load()：若加载的对象不存在，则抛出异常
     */
    @Test
    public void testLoad() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = session.load(Student.class, 2);
            System.out.println(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void testGetLoad() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        //2.执行操作
        //通过getCurrentSession()获取Session对象后所执行的查询，必须在事务环境下运行
        Student student = session.load(Student.class, 2);
        System.out.println(student);
    }

    @Test
    public void testGetLoad2() {
        //1.获取Session对象
        Session session = HibernateUtils.getSessionFactory().openSession();
        //2.执行操作
        //通过openSession()获取Session对象后所执行的查询，无须在事务环境下运行
        Student student = session.load(Student.class, 2);
        System.out.println(student);
    }

    @Test
    public void testCRUD() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //增删改操作若放在同一事务下运行，则系统默认的执行顺序为：增改删
            //删除
            Student student = session.get(Student.class, 5);
            session.delete(student);
            //刷新Session缓存
            session.flush();
            //插入
            Student student2 = new Student("王五", 25, 95.5);
            session.save(student2);
            //修改
            Student student3 = session.get(Student.class, 3);
            student3.setName("赵小六");
            session.update(student3);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
