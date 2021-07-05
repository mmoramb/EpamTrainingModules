package com.deliver.EpamTest;

public class Persona {
    protected String name;
    protected String lastName;
    protected int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void imprimirDatos(){
        System.out.println("nombe: "+ this.name + " className :"+this.lastName +
                "age :"+this.age);
    }
}
