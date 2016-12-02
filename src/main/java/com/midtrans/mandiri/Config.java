package com.midtrans.mandiri;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class Config {
	
	private String endpointPapi;
	private String endpointDragon;
	private String clientkey;
	private String basicAuthParam;
	
	@PostConstruct
	private void read() {
        String path = System.getProperty("user.dir") + "config.properties";
    	Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(path));

            endpointPapi = prop.getProperty("endpoint.papi");
            endpointDragon = prop.getProperty("endpoint.dragon");
            clientkey = prop.getProperty("clientkey");
            
            String serverkey = prop.getProperty("serverkey");
            String authParam = serverkey + ":";
    		String encoded = Base64.getEncoder().encodeToString((authParam.getBytes()));
    		basicAuthParam = "Basic " + encoded;
        } catch (IOException e) {
            e.printStackTrace();;
        }
	}

	public String getEndpointPapi() {
		return endpointPapi;
	}

	public String getEndpointDragon() {
		return endpointDragon;
	}

	public String getClientkey() {
		return clientkey;
	}

	public String getBasicAuthParam() {
		return basicAuthParam;
	}

}
