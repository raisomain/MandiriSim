package com.midtrans.mandiri.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;
import com.midtrans.mandiri.entity.Item;

@ManagedBean
@ViewScoped
public class Charge3DSecure implements Serializable {

	private static final long serialVersionUID = -7452904784970338648L;

	private String endPoint = "http://papi-uat1.stg.veritrans.co.id:8080/v2/charge"; 
	
	private String output; 
	private Item item;
	private List<Item> items;
	// transaction details
	private String orderId;
	private Integer grossAmount = 0;
	// customer detail
	private String firstName;
	private String phone;
	private String eMail;
	// billing address
	private String lastName;
	private String address;
	private String city;
	private String postalCode;
	// credit card
	private String tokenId;
	
	@ManagedProperty(value="#{token3DSecure}")
	private Token3DSecure token3DSecure;
	
	public void newItem() {
		item = new Item();
	}
	
	public void addItem() {
		items.add(item);
		item = new Item();
		for (Item item : items) {
			grossAmount += item.getPrice();
		}
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	@PostConstruct
    private void init(){
		tokenId = token3DSecure.getTokenId();
		System.out.println("tokenId: " + tokenId);
		
        item = new Item();
        items = new ArrayList<>();
    }
	
	private JsonValue getItemArray() {
		JsonValue itemArray = Json.array();
				
		for (Item item : items) {
			JsonObject itemObject = Json.object()
									.add("id", item.getId())
									.add("price", item.getPrice())
									.add("quantity", item.getQuantity())
									.add("name", item.getQuantity());
			itemArray.asArray().add(itemObject);
		}
		return itemArray;
	}
	
	public void command() {
		JsonObject cc = Json.object()
							.add("token_id", tokenId);
		
		JsonObject billingAddress = Json.object()
										.add("email", eMail)
										.add("phone", phone)
										.add("first_name", firstName)
										.add("last_name", lastName)
										.add("address", address)
										.add("city", city)
										.add("country_code", "IDN")
										.add("postal_code", postalCode);
		
		JsonObject transactionDetails = Json.object()
											.add("order_id", orderId)
											.add("gross_amount", grossAmount);
		
		JsonObject customerDetails = Json.object()
										.add("first_name", firstName)
										.add("phone", firstName)
										.add("email", eMail)
										.add("billing_address", billingAddress);
		
		JsonObject chargeObject = Json.object()
									.add("payment_type", "credit_card")
									.add("secure", true )
									.add("credit_card", cc)
									.add("customer_details", customerDetails)
									.add("item_details", getItemArray())
									.add("transaction_details", transactionDetails);
		
		String input = chargeObject.toString();
		System.out.println("input >>");
		System.out.println(Json.parse(input).toString(WriterConfig.PRETTY_PRINT));
		
		Post post = Http.post(endPoint, input)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                //.header("Authorization", "Basic VlQtc2VydmVyLXRpdDNkQ3MyMzU4b082RGx3RjJEVmZ2bDo=");
                .header("Authorization", "Basic VlQtc2VydmVyLUpzX3hzaDg1SE5Dc05Pcjhyc3V2S0JYdTo=");
		
		try {
			output = Json.parse(post.text()).toString(WriterConfig.PRETTY_PRINT);
			System.out.println("output >>");
			System.out.println(output);
		} catch (HttpException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "can't connect", "");
			context.addMessage(null, fm);
		}
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public Token3DSecure getToken3DSecure() {
		return token3DSecure;
	}

	public void setToken3DSecure(Token3DSecure token3dSecure) {
		token3DSecure = token3dSecure;
	}

}