package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.WriterConfig;

@ManagedBean(name = "voided")
public class Void implements Serializable {

	private static final long serialVersionUID = 295608988590120660L;

	String endpoint = "https://dragon1.stg.veritrans.co.id:18080/rest/v1/mandiri/execute";
	
    private String voidContent = "{\"command\":\"void\",\"ref_nonce\":\"%s\"}";
	
	private String refNonce;
	
	private String output;
	
    public void command() {
    	String msg = String.format(voidContent, refNonce);
    	
    	Post post = Http.post(endpoint, msg)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic ZGV2OnJhaGFzaWE=");
    	
		try {
			output = Json.parse(post.text()).toString(WriterConfig.PRETTY_PRINT);
		} catch (HttpException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
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
