package com.midtrans.mandiri.ui;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
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
import com.midtrans.mandiri.entity.Item;

@ManagedBean(name = "token")
@SessionScoped
public class Token implements Serializable {
	
	private static final long serialVersionUID = -9053898340504883138L;

	// hardcoded
	private String endPoint = "https://papi-uat1.stg.veritrans.co.id:8080/v2/token?"
								+ "card_number=%s&"
								+ "card_cvv=%s&"
								+ "card_exp_month=%s&"
								+ "card_exp_year=%s&"
								+ "client_key=%s&"
								+ "vtkey=%s&"
								+ "ip_address=%s";
	//private String clientKey = "VT-client-Jhr3nDDa56LP6SDu";
	private String clientKey = "VT-client-2wxBkZy5g18XHT3y";
	private String vtKey = "v3r1tr4n5-15-n0-1";
	private String ipAddress = "127.0.0.1";
	
	private String cardNumber;
	private String cvv;
	private String cardExpMonth;
	private String cardExpYear;
	private String tokenId;
	private String output;
	
	@PostConstruct
    private void init(){
		output = null;
    }
	
	public String command() {
		String url = String.format(endPoint, cardNumber, cvv, cardExpMonth, cardExpYear, clientKey, vtKey, ipAddress);
		FacesContext context = FacesContext.getCurrentInstance();
		
		Get get = Http.get(url);
    	
		try {
			output = Json.parse(get.text()).toString(WriterConfig.PRETTY_PRINT);
			System.out.println(output);
			
	    	JsonObject outputObject = Json.parse(output).asObject();
	    	JsonValue statusCode = outputObject.get("status_code");
	    	if ("200".equals(statusCode.asString())) {
	    		tokenId = outputObject.get("token_id").asString();
	    		return "charge";
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
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
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
