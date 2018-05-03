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
        if (!stuName.equals(student.stuName)) return false;
        if (!passWord.equals(student.passWord)) return false;
        if (!sex.equals(student.sex)) return false;
        if (!address.equals(student.address)) return false;
        return introduce.equals(student.introduce);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + stuName.hashCode();
        result = 31 * result + passWord.hashCode();
        result = 31 * result + sex.hashCode();
        result = 31 * result + age;
        result = 31 * result + address.hashCode();
        result = 31 * result + introduce.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "student{" +
                "id=" + id +
                ", stuName='" + stuName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
