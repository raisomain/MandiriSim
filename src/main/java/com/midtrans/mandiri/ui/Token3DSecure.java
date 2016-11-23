package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.primefaces.component.lightbox.LightBox;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;

@ManagedBean
@SessionScoped
public class Token3DSecure implements Serializable {

	private static final long serialVersionUID = 1902423758800372002L;
	// hardcoded
	private String endPoint = "https://api.stg.veritrans.co.id/v2/token?"
			+ "card_number=%s&"
			+ "card_cvv=%s&"
			+ "card_exp_month=%s&"
			+ "card_exp_year=%s&"
			+ "ip_address=%s&"
			+ "client_key=%s&"
			+ "vtkey=%s&"
			+ "secure=true&"
			+ "bank=%s&"
			+ "gross_amount=%s";
	private String clientKey = "VT-client-Jhr3nDDa56LP6SDu";
	private String vtKey = "v3r1tr4n5-15-n0-1";
	private String ipAddress = "127.0.0.1";
	private String bank = "mandiri";
	
	private String cardNumber;
	private String cvv;
	private String cardExpMonth;
	private String cardExpYear;
	private String grossAmount;
	private String output;
	private String tokenId;
	private String redirectUrl;
	
	public String command() {
		String url = String.format(endPoint, cardNumber, cvv, cardExpMonth, cardExpYear, ipAddress, clientKey, vtKey, bank, grossAmount);
		FacesContext context = FacesContext.getCurrentInstance();
		
		Get get = Http.get(url);
    	
		try {
			output = get.text();
			output = Json.parse(output).toString(WriterConfig.PRETTY_PRINT);
			System.out.println(output);
			
	    	JsonObject outputObject = Json.parse(output).asObject();
	    	JsonValue statusCode = outputObject.get("status_code");
	    	if ("200".equals(statusCode.asString())) {
	    		tokenId = outputObject.get("token_id").asString();
	    		redirectUrl = outputObject.get("redirect_url").asString();
	    		return "approval";
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
	
	public String process() {
		return "charge3DSecure";
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
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
