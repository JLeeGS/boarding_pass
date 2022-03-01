package domain;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID=1L;
    private String  name, email , gender;
    private int age;
    private long phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public Person(){
        super();
    }

    public Person(String name, String email, long phone, String gender, int age){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.gender=gender;
        this.age=age;
    }

    @Override
    public String toString(){
        return "Name: "+getName()+ " Email: "+getEmail()+" Phone: "+getPhone() +" Gender: "+getGender()+" Age: "+getAge();
    }

}
