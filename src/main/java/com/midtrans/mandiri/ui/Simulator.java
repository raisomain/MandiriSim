	package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.javalite.http.Http;
import org.javalite.http.Post;

@ManagedBean
public class Simulator implements Serializable {

	private static final long serialVersionUID = -2656219069273651693L;
	
	private String firstName = "John";
    private String lastName = "Doe";
    
    String endpoint = "https://dragon1.stg.veritrans.co.id:18080/rest/v1/mandiri/execute";
    private String voidContent = "{\"command\":\"void\",\"ref_nonce\":\"2353a3fedecb4e338fa7f0bf0591f065\"}";
    private String output;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String showGreeting() {
        return "Hello" + " " + firstName + " " + lastName + "!";
    }
    

    
    
}
