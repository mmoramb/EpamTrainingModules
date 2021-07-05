package com.deliver.model;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by maksym_govorischev on 14/03/14.
 */
//@Component
@XmlRootElement(name = "User")
public class User {
    /**
     * User Id. UNIQUE.
     * @return User Id.
     */
    private long id;
    private  String name;
    private String email;

    public User() {
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
    long getId();
    void setId(long id);
    String getName();
    void setName(String name);
*/
    /**
     * User email. UNIQUE.
     * @return User email.
     */
    /*
    String getEmail();
    void setEmail(String email);
    */
}
