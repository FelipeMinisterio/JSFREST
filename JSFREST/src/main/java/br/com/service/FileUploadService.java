package br.com.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;

import br.com.model.Files;

@Stateless
@Path("/files")
public class FileUploadService {

	@PersistenceContext(unitName = "PatrimoniosPU")
	EntityManager entityManager;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Files uploadFile(MultiPart multipart) throws IOException {
		
		InputStream in = null;
		
		for(BodyPart part : multipart.getBodyParts()) {
			 in = part.getEntityAs(InputStream.class);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];
		int len = 0;
		while ((len = in.read(buffer, 0, 10240)) != -1) {
			bos.write(buffer, 0, len);
		}
		byte[] fileBytes = bos.toByteArray();
		Files fileNew = new Files();
		fileNew.setFile_name(multipart.getBodyParts().get(0).getContentDisposition().getFileName());
		fileNew.setData(fileBytes);
		fileNew.setFile_type("application/pdf");

		entityManager.persist(fileNew);

		return fileNew;
	}
	
	@GET
	@Path("/download/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response downloadFile(@PathParam("id") Integer id) throws IOException {
		 Files fileD = entityManager.find(Files.class, id); 
		return Response.ok().type("application/pdf").entity(fileD.getData()).build();
	}
	
	
	@SuppressWarnings("unchecked")
	@GET
	public List<Files> getFiles() {
		Query query = entityManager.createQuery("select f from Files f");
		return query.getResultList();
	}

}