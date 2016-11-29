package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.HttpException;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;

@ManagedBean
@SessionScoped
public class TokenCCPoint implements Serializable {

	private static final long serialVersionUID = -8270817002171475216L;
	
	// hardcoded
	private String endPoint = "https://papi-uat1.stg.veritrans.co.id:8080/v2/token?"
			+ "card_number=%s&"
			+ "card_exp_month=%s&"
			+ "card_exp_year=%s&"
			+ "card_cvv=%s&"
			+ "gross_amount=%s&"
		//	+ "secure=true&"
			+ "client_key=%s&"
			+ "vtkey=%s&"
			+ "bank=%s&"
			+ "point=true";
	//private String clientKey = "VT-client-Jhr3nDDa56LP6SDu";
	private String clientKey = "VT-client-2wxBkZy5g18XHT3y";
	private String vtKey = "v3r1tr4n5-15-n0-1";
	private String bank = "mandiri";
	
	private String cardNumber;
	private String cardExpMonth;
	private String cardExpYear;
	private String cvv;
	private String grossAmount;
	private String tokenId;
	private String output;

	public String command() {
    	String url = String.format(endPoint, cardNumber, cardExpMonth, cardExpYear, cvv, grossAmount, clientKey, vtKey, bank);
		FacesContext context = FacesContext.getCurrentInstance();
		
		Get get = Http.get(url);
		try {
			output = Json.parse(get.text()).toString(WriterConfig.PRETTY_PRINT);
			System.out.println(output);
			
	    	JsonObject outputObject = Json.parse(output).asObject();
	    	JsonValue statusCode = outputObject.get("status_code");
	    	if ("200".equals(statusCode.asString())) {
	    		tokenId = outputObject.get("token_id").asString();
	    		return "chargeCCPoint";
			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't get token", "");
				context.addMessage(null, fm);
			}
		} catch (HttpException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
    	return null ;
	}
	
	public String command2() {
    	String url = String.format(endPoint, cardNumber, cardExpMonth, cardExpYear, cvv, grossAmount, clientKey, vtKey, bank);
		FacesContext context = FacesContext.getCurrentInstance();
		
		Get get = Http.get(url);
		try {
			output = Json.parse(get.text()).toString(WriterConfig.PRETTY_PRINT);
			System.out.println(output);
			
	    	JsonObject outputObject = Json.parse(output).asObject();
	    	JsonValue statusCode = outputObject.get("status_code");
	    	if ("200".equals(statusCode.asString())) {
	    		tokenId = outputObject.get("token_id").asString();
	    		return "pointInquiry";
			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't get token", "");
				context.addMessage(null, fm);
			}
		} catch (HttpException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
    	return null ;
	}
	
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardExpMonth() {
		return cardExpMonth;
	}
	public void setCardExpMonth(String cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}
	public String getCardExpYear() {
		return cardExpYear;
	}
	public void setCardExpYear(String cardExpYear) {
		this.cardExpYear = cardExpYear;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(String grossAmount) {
		this.grossAmount = grossAmount;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getVtKey() {
		return vtKey;
	}
	public void setVtKey(String vtKey) {
		this.vtKey = vtKey;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getTokenId() {
		return tokenId;
	}


	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}


	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}

	
}
