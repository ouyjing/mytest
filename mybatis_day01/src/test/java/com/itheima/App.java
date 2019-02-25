package com.itheima;

import com.itheima.dao.IUserDao;
import com.itheima.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class App {
    @Test
    public void findAll() throws IOException {
        //1. 加载SqlMapConfig.xml配置文件，获取文件流
        // Resources 加载类路径下的配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2. 创建SqlSessionFactoryBuild对象，工厂构造器
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //3. 通过工厂构造器，创建SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(in);
        //4. 通过SqlSessionFactory，创建SqlSession对象 （最关键）
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //5. 通过SqlSession对象，对指定的dao接口生成代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        System.out.println("查看代理:" + userDao.getClass());
        //6. 执行接口方法（调用代理方法）
        List<User> userList = userDao.findAll();
        for (int i = 0; i < userList.size(); i++ ){
            User user = userList.get(i);
            System.out.println("id =" + user.getId() +",username =" + user.getUsername() + ",birthday =" + user.getBirthday() + ",sex =" + user.getSex() + ",address =" + user.getAddress());
        }
        //7. 关闭释放资源
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }
}
