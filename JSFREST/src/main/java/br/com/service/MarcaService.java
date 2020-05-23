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
import br.com.model.Marca;
import br.com.model.Patrimonio;


@Stateless
@Path("/marca")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarcaService {
	
	@PersistenceContext(unitName = "PatrimoniosPU")
	EntityManager entityManager;
	
	List<Marca> marcas;
	
	@SuppressWarnings("unchecked")
	@GET
	public List<Marca> getMarcas() {
		Query query = entityManager.createQuery("select m from Marca m");
		return query.getResultList();
	}
	
	@GET
	@Path("{id}")
	public Marca getMarcaById(@PathParam("id") Integer id) {
		return entityManager.find(Marca.class, id);	
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("{id}/patrimonio")
	public List<Patrimonio> getPatrimonioByMarca(@PathParam("id") Integer id){
		Query query = entityManager.createQuery("SELECT p FROM Patrimonio p WHERE p.marca.id = "+id+"");
		return query.getResultList();
	}
	
	@POST
	public Marca setMarca(Marca marca) {
		entityManager.persist(marca);
		return marca;
	}
	
	@PUT
	@Path("{id}")
	public Marca editMarcaById(@PathParam("id") Integer id,Marca marca) {
		Marca marcaEncontrado = getMarcaById(id);
		marcaEncontrado.setNome(marca.getNome());
		entityManager.merge(marcaEncontrado);
		return marca;
	}
	
	@DELETE
	@Path("{id}")
	public Marca deleteMarcaById(@PathParam("id") Integer id) {
		Marca marcaEncontrado = getMarcaById(id);
		entityManager.remove(marcaEncontrado);
		return marcaEncontrado;
	}
}
