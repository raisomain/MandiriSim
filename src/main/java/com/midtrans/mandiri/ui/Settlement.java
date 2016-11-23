package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;

@ManagedBean
public class Settlement implements Serializable {

	private static final long serialVersionUID = -3464168532389439429L;

	String endpoint = "https://dragon1.stg.veritrans.co.id:18080/rest/v1/mandiri/execute";

	private String mid;
	
	private String tid;
	
	private Integer timeout;

	private String nonces;
	
	private String output;
	
	public void command() {
		JsonArray noncesArray = Json.array(nonces.split(","));
		JsonObject json = Json.object()
							.add("command", "settlement")
							.add("mid", mid)
							.add("tid", tid)
							.add("timeout", timeout)
							.add("nonces", noncesArray);
    	
    	Post post = Http.post(endpoint, json.toString())
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
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getNonces() {
		return nonces;
	}

	public void setNonces(String nonces) {
		this.nonces = nonces;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
