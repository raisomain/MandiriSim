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
public class Cancel implements Serializable {

	private static final long serialVersionUID = -2472899860838320412L;
	
	private String endPoint = "https://papi-uat1.stg.veritrans.co.id:8080/v2/%s/cancel";
	
	private String param;
	private String output;
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void command() {
		String url = String.format(endPoint, param);
		
		Post post = Http.post(url, "")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                //.header("Authorization", "Basic VlQtc2VydmVyLTAwMDAwMDAwMDAwMDAwMDFBMTAwMDAwMDAwMDAwMDAxMDAwMDE4Og==");
				.header("Authorization", "Basic VlQtc2VydmVyLUpzX3hzaDg1SE5Dc05Pcjhyc3V2S0JYdTo=");
		
		try {
			output = Json.parse(post.text()).toString(WriterConfig.PRETTY_PRINT);
		} catch (HttpException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
