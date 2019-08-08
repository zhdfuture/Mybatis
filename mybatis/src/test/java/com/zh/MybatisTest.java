package com.zh;

import com.zh.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory=null;

    @Before
    public void testInit(){
        String file = "sqlMapConfig.xml";
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(file);   //用流来读，流里面要一个文件
            //第一步：获取SqlSessionfactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);  //将流传入
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //执行相关的操作
    @Test
    public void testQueryUserById(){
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();  //线程非安全放在方法体内来操作
        try {
            /**
             * parameter 1: string : namespace+statementID
             * parameter 2: object : input_value
             */
            User user = sqlSession.selectOne("test.queryUserById",1);
            System.out.println(user);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            sqlSession.close();//similar Connection
        }
    }


    @Test
    public void testQueryUserByName(){
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();  //线程非安全放在方法体内来操作
        try {
            /**
             * parameter 1: string : namespace+statementID
             * parameter 2: object : input_value
             */
           List<User> userList = sqlSession.selectList("test.queryUserByName","%Tom%");
            System.out.println(userList);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            sqlSession.close();//similar Connection
        }
    }
    @Test
    public void testAddUser(){
        int flag=0;
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();  //线程非安全放在方法体内来操作
        try {
            /**
             * parameter 1: string : namespace+statementID
             * parameter 2: object : input_value
             */
            User user=new User();
            user.setUserName("Jerry");
            user.setUserAddress("America");
            flag = sqlSession.insert("test.addUser",user);

            sqlSession.commit();
            System.out.println("flag="+flag);
        }catch(Exception ex){
            sqlSession.rollback();
            ex.printStackTrace();
        }finally {
            sqlSession.close();//similar Connection
        }
    }

    @Test
    public void testModifUser(){
        int flag=0;
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();  //线程非安全放在方法体内来操作
        try {
            /**
             * parameter 1: string : namespace+statementID
             * parameter 2: object : input_value
             */
            User user=new User();
            user.setUserId(3);  //id是必须输入
            user.setUserName("Querry");
            user.setUserAddress("Astrila");
            flag = sqlSession.update("test.modifyUser",user);
            sqlSession.commit();  //提交事件
            System.out.println("flag="+flag);
        }catch(Exception ex){
            sqlSession.rollback();
            ex.printStackTrace();
        }finally {
            sqlSession.close();//similar Connection
        }
    }

    @Test
    public void testDelUser(){
        int flag=0;
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            flag=sqlSession.delete("test.delUser",2);
            sqlSession.commit();
            System.out.println("flag="+flag);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }


}



