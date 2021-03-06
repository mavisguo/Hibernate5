package com.powernode.test;

import com.powernode.beans.Student;
import com.powernode.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    public void test01_SQL() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String sql = "select * from t_student";
            List<Student> list = session.createNativeQuery(sql).addEntity(Student.class).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test01_HQL() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student";//这里的Student是类名
            List<Student> list = session.createQuery(hql).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test02_SQL() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String sql = "select * from t_student order by tage desc";
            List<Student> list = session.createNativeQuery(sql).addEntity(Student.class).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test02_HQL() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student s order by s.age desc";
            List<Student> list = session.createQuery(hql).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test03_1() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student where age>? and score<?";
            List<Student> list = session.createQuery(hql).setParameter(0, Integer.valueOf(20)).setParameter(1, Double.valueOf(94)).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test03_2() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //别名：要求必须以冒号开头
            String hql = "from Student where age>:myage and score<:myscore";
            List<Student> list = session.createQuery(hql).setParameter("myage", Integer.valueOf(20)).setParameter("myscore", Double.valueOf(94)).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test04() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            //select * from t_student limit startIndex,pageSize
            String hql = "from Student";
            int pageNo = 2;
            int pageSize = 3;
            int startIndex = (pageNo - 1) * pageSize;
            List<Student> list = session.createQuery(hql).setFirstResult(startIndex).setMaxResults(pageSize).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test05() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student where name like :myname";
            List<Student> list = session.createQuery(hql).setParameter("myname", "%张%").list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test06() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student where id=:myid";
            Student student = (Student) session.createQuery(hql).setParameter("myid", 2).uniqueResult();
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
    public void test07() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "select count(id) from Student";
            Long count = (Long) session.createQuery(hql).uniqueResult();
            System.out.println(count);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    @Test
    public void test08() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "select new Student(name, age) from Student";
            List<Student> list = session.createQuery(hql).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test09_1() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student group by age";
            List<Student> list = session.createQuery(hql).list();
            for (Student student : list) {
                System.out.println(student);
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
    public void test09_2() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "select age from Student group by age having count(age) > 1";
            List<Integer> list = session.createQuery(hql).list();
            System.out.println(list);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

    //Query接口的list()方法不会从session缓存中读取数据
    @Test
    public void test10() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            String hql = "from Student";
            //第一次查询
            List<Student> list = session.createQuery(hql).list();
            for (Student student : list) {
                System.out.println(student);
            }
            System.out.println("--------------------");
            //第二次查询
            List<Student> list2 = session.createQuery(hql).list();
            for (Student student : list2) {
                System.out.println(student);
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
    public void test11() {
        //1.获取Session对象
        Session session = HibernateUtils.getSession();
        try {
            //2.开启事务
            session.beginTransaction();
            //3.执行操作
            Student student = (Student) session.getNamedQuery("selectById").setParameter("myid", 2).uniqueResult();
            System.out.println(student);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
