package com.powernode.test;

import com.powernode.beans.Course;
import com.powernode.beans.Student;
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
    public void test02() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student1 = new Student("张三");
            Student student2 = new Student("李四");

            Course course1 = new Course("JavaSE");
            course1.getStudents().add(student1);
            course1.getStudents().add(student2);

            Course course2 = new Course("JavaEE");
            course2.getStudents().add(student1);

            Course course3 = new Course("Andriod");
            course3.getStudents().add(student2);

            session.save(course1);
            session.save(course2);
            session.save(course3);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
