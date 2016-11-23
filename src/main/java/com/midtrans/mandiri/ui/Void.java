package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;

@ManagedBean(name = "voided")
public class Void implements Serializable {

	private static final long serialVersionUID = 295608988590120660L;

	String endpoint = "https://dragon1.stg.veritrans.co.id:18080/rest/v1/mandiri/execute";
	
    private String voidContent = "{\"command\":\"%s\",\"ref_nonce\":\"%s\"}";
	
	private String command;
	
	private String refNonce;
	
	private String output;
	
    public void command() {
    	// String[] params = {command, refNonce};
    	String msg = String.format(voidContent, command, refNonce);
    	
    	Post post = Http.post(endpoint, msg)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic ZGV2OnJhaGFzaWE=");
    	
		try {
			output = post.text();
		} catch (HttpException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
    }

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getRefNonce() {
		return refNonce;
	}

	public void setRefNonce(String refNonce) {
		this.refNonce = refNonce;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
