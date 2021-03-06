package com.powernode.test;

import com.powernode.beans.Student;
import com.powernode.utils.HibernateUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class MyTest {

    //准备测试数据
    @Test
    public void test00() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            for (int i = 1; i < 11; i++) {
                Student student = new Student("n_" + i, 15 + i, 85 + i);
                session.save(student);
            }
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //证明一级缓存是存在的
    @Test
    public void test01() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //第一次查询
            Student student = session.get(Student.class, 2);
            System.out.println("student = " + student);
            System.out.println("--------------------");
            //第二次查询
            Student student2 = session.get(Student.class, 2);
            System.out.println("student = " + student2);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //说明快照的作用
    @Test
    public void test02() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            /*
             * session.get()都做了些什么工作：
             * 1)将数据从数据库中读取出来；
             * 2)将数据转变为对象，存放到堆内存；
             * 3)将对象的id放入session缓存map的key中，将对象的引用放入session缓存map的value中；
             * 4)将对象的详情放入到“快照”中。
             */
            Student student = session.get(Student.class, 2);
            //下面的修改语句修改的是堆内存中的对象数据
            student.setName("张三");
            //4.事务提交
            //事务提交时做的工作：将堆内存中的数据与快照中的数据进行对比：若比较结果不同，则执行update；若比较结果相同，则不执行update。
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //删除操作到达刷新点后的执行情况
    @Test
    public void test03() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = session.get(Student.class, 2);
            System.out.println("第一次查询student = " + student);
            /*
             * 当执行删除操作时，并不会直接执行delete的SQL语句，但会将Session缓存Map中该对象的key所对应的value置为null，不过这个key所在的Entry并没有被删除，即该对象的key仍存在于Map中。
             * 为什么要将value删除而保留key？
             * 因为到达同步点后，要将Session缓存中的数据与数据库中的数据进行同步，就要删除数据库中的该对象，而要删除数据库中的该对象，需要根据id进行，所以不删除Map中的id，而在到达刷新点后，才会执行delete的SQL语句。
             * 这就是对Session缓存数据的刷新，实际就是对堆内存中数据的更新。
             */
            session.delete(student);
            Student student2 = session.get(Student.class, 2);
            System.out.println("第二次查询student2 = " + student2);
            //刷新点
            session.createQuery("from Student").list();
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //修改操作到达刷新点后的执行情况
    @Test
    public void test04() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = session.get(Student.class, 2);
            //当执行修改操作时，即使到达刷新点，是否执行update的SQL语句，还要取决于修改的数据与快照中数据的对比结果：相同则不执行，不同则执行。
            student.setName("李四");
            session.update(student);
            //刷新点
            session.createQuery("from Student").list();
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //插入操作到达刷新点后的执行情况
    @Test
    public void test05() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = new Student("王五", 25, 95.5);
            //插入操作无需到达刷新点
            session.save(student);
            //刷新点
            session.createQuery("from Student").list();
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //修改刷新点
    @Test
    public void test06() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //修改刷新点：Query查询将不作为刷新点
            session.setHibernateFlushMode(FlushMode.COMMIT);
            Student student = session.get(Student.class, 2);
            session.delete(student);
            //原来的刷新点
            session.createQuery("from Student").list();
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
