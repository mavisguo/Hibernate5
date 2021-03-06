package com.powernode.test;

import com.powernode.beans.Course;
import com.powernode.beans.Student;
import com.powernode.utils.HibernateUtils;
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
            Course course1 = new Course("JavaSE");
            Course course2 = new Course("JavaEE");
            Course course3 = new Course("Andriod");

            Student student1 = new Student("张三");
            student1.getCourses().add(course1);
            student1.getCourses().add(course2);

            Student student2 = new Student("李四");
            student2.getCourses().add(course1);
            student2.getCourses().add(course3);

            session.save(student1);
            session.save(student2);
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
            Student student = session.get(Student.class, 2);
            session.delete(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
