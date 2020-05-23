package br.com.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import br.com.model.Patrimonio;

@ManagedBean(name = "MBPatrimonio")
@ViewScoped
public class PatrimonioController {

	private Patrimonio patrimonio;
	private List<Patrimonio> patrimonios;

	public PatrimonioController() {
		patrimonio = new Patrimonio();
	}
	@PostConstruct
	public void getPatrimoniosAll() {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/patrimonio");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		patrimonios = gson.fromJson(json, new TypeToken<List<Patrimonio>>() {
		}.getType());
	}
	
	public Patrimonio getPatrimonioById() {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/patrimonio/"+patrimonio.getId());
		String json = wr.get(String.class);
		Gson gson = new Gson();
		patrimonios.removeAll(patrimonios);
		patrimonios.add(gson.fromJson(json, new TypeToken<Patrimonio>() {
		}.getType()));
		return gson.fromJson(json, new TypeToken<Patrimonio>() {
		}.getType());
	}
		
	public void salvarPatrimonio() {
		
		if(patrimonio.getId() != null) {
			
			editarPatrimonio(patrimonio);
			
		}else {

		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/patrimonio");
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		try {
		  json = mapper.writeValueAsString(patrimonio);
		} catch (JsonProcessingException e) {
		  json = "{\"descricao\": \""+patrimonio.getDescricao()+"\",\r\n" + 
					"    \"marcaId\": "+patrimonio.getMarca().getId()+",\r\n" + 
					"    \"nome\": \""+patrimonio.getNome()+"\",\r\n" + "}";
			e.printStackTrace();
		}
		

		wr.accept("application/json")
                .type("application/json").post(ClientResponse.class, json);
		}
		limparPatrimonio();
	}
	
	public void limparPatrimonio() {
		patrimonio = new Patrimonio();
	}
	
	public void editarPatrimonio(Patrimonio patrimonio) {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/patrimonio/"+patrimonio.getId());
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		try {
			json = mapper.writeValueAsString(patrimonio);
		} catch (JsonProcessingException e) {
			  json = "{\"descricao\": \""+patrimonio.getDescricao()+"\",\r\n" + 
						"    \"marcaId\": "+patrimonio.getMarca().getId()+",\r\n" + 
						"    \"nome\": \""+patrimonio.getNome()+"\",\r\n" + "}";
				e.printStackTrace();
		}
		
		
		 wr.type("application/json").accept("application/json").put(ClientResponse.class,json);
		
	}
	
	public void excluirPatrimonio(Patrimonio patrimonio) {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/patrimonio/"+patrimonio.getId());
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		try {
			json = mapper.writeValueAsString(patrimonio);
		} catch (JsonProcessingException e) {
			  json = "{\"descricao\": \""+patrimonio.getDescricao()+"\",\r\n" + 
						"    \"marcaId\": "+patrimonio.getMarca().getId()+",\r\n" + 
						"    \"nome\": \""+patrimonio.getNome()+"\",\r\n" + "}";
				e.printStackTrace();
		}
		
		
		 wr.type("application/json").accept("application/json").delete(ClientResponse.class);
	}
	
	

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public List<Patrimonio> getPatrimonios() {
		return patrimonios;
	}

	public void setPatrimonios(List<Patrimonio> patrimonios) {
		this.patrimonios = patrimonios;
	}
	
	
}
