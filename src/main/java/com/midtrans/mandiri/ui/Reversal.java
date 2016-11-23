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

@ManagedBean
public class Reversal implements Serializable {	

	private static final long serialVersionUID = -6048330284329903968L;

	String endpoint = "https://dragon1.stg.veritrans.co.id:18080/rest/v1/mandiri/execute";
	
    private String content = "{\"command\":\"reversal\",\"ref_nonce\":\"%s\"}";
	
	private String refNonce;
	
	private String output;
	
    public void command() {
    	String msg = String.format(content, refNonce);
    	
    	Post post = Http.post(endpoint, msg)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic ZGV2OnJhaGFzaWE=");
		try {
			output = Json.parse(post.text()).toString(WriterConfig.PRETTY_PRINT);;
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
