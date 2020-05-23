package br.com.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import br.com.model.Patrimonio;



@Stateless
@Path("/patrimonio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatrimonioService{

	@PersistenceContext(unitName = "PatrimoniosPU")
	EntityManager entityManager;
	
	public PatrimonioService() {
	}
	
	@SuppressWarnings("unchecked")
	@GET
	public List<Patrimonio> getPatrimonios() {
		Query query = entityManager.createQuery("select p from Patrimonio p");
		 return query.getResultList();			 
		 
	}
		
	@GET
	@Path("{id}")
	public Patrimonio getPatrimonioById(@PathParam("id") Integer id) {
		return entityManager.find(Patrimonio.class, id); 
		 
	}
	
	@POST
	public Patrimonio setPatrimonio(Patrimonio patrimonio) {
		entityManager.persist(patrimonio);
		return patrimonio;
	}
	
	@PUT
	@Path("{id}")
	public Patrimonio editPatrimonioById(@PathParam("id") Integer id,Patrimonio patrimonio) {
		Patrimonio patrimonioEncontrado = getPatrimonioById(id);
		patrimonioEncontrado.setNome(patrimonio.getNome());
		patrimonioEncontrado.getMarca().setId(patrimonio.getMarca().getId());
		patrimonioEncontrado.setDescricao(patrimonio.getDescricao());
		entityManager.merge(patrimonioEncontrado);
		return patrimonio;
	}
	
	@DELETE
	@Path("{id}")
	public Patrimonio deletePatrimonioById(@PathParam("id") Integer id) {
		Patrimonio patrimonioEncontrado = getPatrimonioById(id);
		entityManager.remove(patrimonioEncontrado);
		return patrimonioEncontrado;
	}
	
}
