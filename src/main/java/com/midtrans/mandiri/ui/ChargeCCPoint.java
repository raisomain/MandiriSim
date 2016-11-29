package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.WriterConfig;

@ManagedBean
@ViewScoped
public class ChargeCCPoint implements Serializable {

	private static final long serialVersionUID = 4793170495874127423L;

	private String endPoint = "https://papi-uat1.stg.veritrans.co.id:8080/v2/charge";
	
	private String content = "{\"payment_type\":\"credit_card\",\"transaction_details\":{\"order_id\":\"%s\",\"gross_amount\":%s},\"credit_card\":{\"token_id\":\"%s\",\"point_redeem_amount\":%s,\"bank\":\"%s\"}}";

	private String orderId;
	
	private String grossAmount;
	
	private String tokenId;
	
	private String pointRedeemAmount;
	
	private String bank = "mandiri";
	
	private String output;

	@ManagedProperty(value="#{tokenCCPoint}")
	private TokenCCPoint tokenCCPoint;
	
	@PostConstruct
    private void init(){
		tokenId = tokenCCPoint.getTokenId();
		System.out.println("tokenId:" + tokenId);
    }
	
	public void command() {
		String msg = String.format(content, orderId, grossAmount, tokenId, pointRedeemAmount, bank);
		System.out.println("input >");
		System.out.println(Json.parse(msg).toString(WriterConfig.PRETTY_PRINT));
		Post post = Http.post(endPoint, msg)
				.header("Accept", "application/json")
                .header("Content-Type", "application/json")
                //.header("Authorization", "Basic VlQtc2VydmVyLXRpdDNkQ3MyMzU4b082RGx3RjJEVmZ2bDo=");
                .header("Authorization", "Basic VlQtc2VydmVyLUpzX3hzaDg1SE5Dc05Pcjhyc3V2S0JYdTo=");
		
		try {
			output = Json.parse(post.text()).toString(WriterConfig.PRETTY_PRINT);
		} catch (HttpException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(String grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getPointRedeemAmount() {
		return pointRedeemAmount;
	}

	public void setPointRedeemAmount(String pointRedeemAmount) {
		this.pointRedeemAmount = pointRedeemAmount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public TokenCCPoint getTokenCCPoint() {
		return tokenCCPoint;
	}

	public void setTokenCCPoint(TokenCCPoint tokenCCPoint) {
		this.tokenCCPoint = tokenCCPoint;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
