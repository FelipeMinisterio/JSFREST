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

import br.com.model.Marca;
import br.com.model.Patrimonio;

@ManagedBean(name = "MBMarca")
@ViewScoped
public class MarcaController {

	private Marca marca;
	private List<Patrimonio> patrimonioMarca;
	private List<Marca> marcas;

	public MarcaController() {
		marca = new Marca();
	}
	
	public List<Patrimonio> getPatrimonioByMarca(Integer id){
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/marca/"+id+"/patrimonio");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		patrimonioMarca = gson.fromJson(json, new TypeToken<List<Patrimonio>>() {
		}.getType());
		return gson.fromJson(json, new TypeToken<List<Patrimonio>>() {
		}.getType());
		
	}
	@PostConstruct
	public void getMarcasAll() {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/marca");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		marcas = gson.fromJson(json, new TypeToken<List<Marca>>() {
		}.getType());
		limparMarca();
	}
	
	public Marca getMarcaById() {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/marca/"+marca.getId());
		String json = wr.get(String.class);
		Gson gson = new Gson();
		marcas.removeAll(marcas);
		marcas.add(gson.fromJson(json, new TypeToken<Marca>() {
		}.getType()));
		return gson.fromJson(json, new TypeToken<Marca>() {
		}.getType());
	}
	

	public void buscarPatrimonio(Integer id) {
		getPatrimonioByMarca(id);
	}
	
	public void salvarMarca() {

		if (marca.getId() != null) {

			editarMarca(marca);

		} else {

			Client c = Client.create();
			WebResource wr = c.resource("http://localhost:8080/api/resources/marca");

			ObjectMapper mapper = new ObjectMapper();
			String json = null;

			try {
				json = mapper.writeValueAsString(marca);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			wr.accept("application/json").type("application/json").post(ClientResponse.class, json);
		}
		limparMarca();
	}

	public void limparMarca() {
		marca = new Marca();
		patrimonioMarca = null;
	}

	public void editarMarca(Marca marca) {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/marca/" + marca.getId());

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		try {
			json = mapper.writeValueAsString(marca);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		wr.type("application/json").accept("application/json").put(ClientResponse.class, json);

	}

	public void excluirMarca(Marca marca) {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/marca/" + marca.getId());

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		try {
			json = mapper.writeValueAsString(marca);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		wr.type("application/json").accept("application/json").delete(ClientResponse.class);
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Patrimonio> getPatrimonioMarca() {
		return patrimonioMarca;
	}

	public void setPatrimonioMarca(List<Patrimonio> patrimonioMarca) {
		this.patrimonioMarca = patrimonioMarca;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}	

	
}
