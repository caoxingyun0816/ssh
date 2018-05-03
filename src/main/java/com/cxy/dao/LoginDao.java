package com.cxy.dao;

import com.cxy.entity.Student;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;



/**
 * Created by Administrator on 2018/5/3.
 */
public class LoginDao extends HibernateDaoSupport{

    public String login(String stuName , String password){
        int id = 1;
        Student student;
        //String sql = "from Student where stu_name = ? and password = ?";
        //List<Student> students = (List<Student>) this.getHibernateTemplate().find(sql,stuName,password);
        student = this.getHibernateTemplate().get(Student.class,id);
        return student.getStuName();
    }
}
