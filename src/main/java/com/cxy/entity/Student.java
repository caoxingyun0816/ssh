package com.cxy.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/5/3.
 */
@Entity
@Table(name = "student")
public class Student {
    private static final long serialVersionUID = 1L;

    private int id;

    private String stuName;

    private String passWord;

    private String sex;

    private int age;

    private int telphone;

    private String address;

    private String introduce;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "C_STU_SEQ")
    @SequenceGenerator(name = "C_STU_SEQ",allocationSize = 1,sequenceName = "C_STU_SEQ")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "stu_name")
    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Column(name = "passWord")
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "telphone")
    public int getTelphone() {
        return telphone;
    }

    public void setTelphone(int telphone) {
        this.telphone = telphone;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "introduce")
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (age != student.age) return false;
        if (telphone != student.telphone) return false;
        if (stuName != null ? !stuName.equals(student.stuName) : student.stuName != null) return false;
        if (passWord != null ? !passWord.equals(student.passWord) : student.passWord != null) return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;
        if (address != null ? !address.equals(student.address) : student.address != null) return false;
        return introduce != null ? introduce.equals(student.introduce) : student.introduce == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (stuName != null ? stuName.hashCode() : 0);
        result = 31 * result + (passWord != null ? passWord.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + telphone;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuName='" + stuName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", telphone=" + telphone +
                ", address='" + address + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
