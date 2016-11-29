package com.midtrans.mandiri.ui;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.HttpException;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.WriterConfig;

@ManagedBean
@ViewScoped
public class PointInquiry implements Serializable {

	private static final long serialVersionUID = 7640745019038978097L;

	private String endPoint = "https://papi-uat1.stg.veritrans.co.id:8080/v2/point_inquiry/%s";
	private String tokenId;
	private String output;
	
	@ManagedProperty(value="#{tokenCCPoint}")
	private TokenCCPoint tokenCCPoint;
	
	@PostConstruct
    private void init() {
		tokenId = tokenCCPoint.getTokenId();
		System.out.println("tokenId: " + tokenId);
    }
	
	public void command() {
		String url = String.format(endPoint, tokenId);
    	Get get = Http.get(url)
				.header("Accept", "application/json")
                .header("Content-Type", "application/json")
                //.header("Authorization", "Basic VlQtc2VydmVyLXRpdDNkQ3MyMzU4b082RGx3RjJEVmZ2bDo=");
                .header("Authorization", "Basic VlQtc2VydmVyLUpzX3hzaDg1SE5Dc05Pcjhyc3V2S0JYdTo=");
		try {
			output = Json.parse(get.text()).toString(WriterConfig.PRETTY_PRINT);
		} catch (HttpException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
	    	context.addMessage(null, fm);
		}
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

	public TokenCCPoint getTokenCCPoint() {
		return tokenCCPoint;
	}

	public void setTokenCCPoint(TokenCCPoint tokenCCPoint) {
		this.tokenCCPoint = tokenCCPoint;
	}

}
