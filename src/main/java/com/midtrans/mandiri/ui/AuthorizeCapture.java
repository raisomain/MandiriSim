package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;

@ManagedBean
public class AuthorizeCapture implements Serializable {

	private static final long serialVersionUID = 5595630499186828054L;

	String endpoint = "https://dragon1.stg.veritrans.co.id:18080/rest/v1/mandiri/execute";
	
	private String mid;
	
	private String tid;
	
	private Integer grossAmount;
	
	private String ccNumber;
	
	private String ccExpire;
	
	private String ccCVV;
	
	private String output;
	
	public void command() {
		JsonObject cc = Json.object()
						.add("number", ccNumber)
						.add("expire", ccExpire)
						.add("cvv", ccCVV);
		
		JsonObject json = Json.object()
				.add("command", "authorize_capture")
				.add("mid", mid)
				.add("tid", tid)
				.add("credit_card", cc)
				.add("gross_amount", grossAmount);
		
		Post post = Http.post(endpoint, json.toString())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic ZGV2OnJhaGFzaWE=");
		
		try {
			output = post.text();
			output = Json.parse(output).toString(WriterConfig.PRETTY_PRINT);
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

	public Integer getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpire() {
		return ccExpire;
	}

	public void setCcExpire(String ccExpire) {
		this.ccExpire = ccExpire;
	}

	public String getCcCVV() {
		return ccCVV;
	}

	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	

}
