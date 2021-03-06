package com.powernode.test;

import com.powernode.beans.NewsLabel;
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
            NewsLabel child = new NewsLabel("足球", "足球足球足球足球");
            NewsLabel child2 = new NewsLabel("篮球", "篮球篮球篮球篮球");

            NewsLabel sports = new NewsLabel("体育新闻", "体育新闻体育新闻体育新闻体育新闻");
            sports.getChildNewsLabels().add(child);
            sports.getChildNewsLabels().add(child2);

            session.save(sports);
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
            NewsLabel sports = new NewsLabel("体育新闻", "体育新闻体育新闻体育新闻体育新闻");

            NewsLabel child = new NewsLabel("足球", "足球足球足球足球");
            child.setParentNewsLabel(sports);

            session.save(child);
            //4.事务提交
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.事务回滚
            session.getTransaction().rollback();
        }
    }

}
