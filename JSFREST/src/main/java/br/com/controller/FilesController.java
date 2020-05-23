package br.com.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import br.com.model.Files;

import com.sun.jersey.api.client.config.ClientConfig;

@ManagedBean(name = "MBFiles")
@ViewScoped
public class FilesController {

	private Part files;
	private List<Files> arquivos;
	private Files arquivo;

	public void importarFile() throws IOException {

		@SuppressWarnings("resource")
		MultiPart multiPart = new MultiPart().bodyPart(new FormDataBodyPart(
				FormDataContentDisposition.name("file").fileName(files.getSubmittedFileName()).build(),
				files.getInputStream(), MediaType.APPLICATION_OCTET_STREAM_TYPE));

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(MultiPartWriter.class);
		Client client = Client.create(config);

		WebResource wr = client.resource("http://localhost:8080/api/resources/files/upload");

		 wr.type(MediaType.MULTIPART_FORM_DATA).post(multiPart);
		 
		 getFilesAll();

	}
	
	public void baixarArquivo() {
		Client c = new Client();
		WebResource wr = c.resource("http://localhost:8080/api/resources/files/download/"+arquivo.getId());
		wr.get(Files.class);
	}
	
	@PostConstruct
	public void getFilesAll() {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/api/resources/files");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		arquivos = gson.fromJson(json, new TypeToken<List<Files>>() {
		}.getType());
	}

	public Part getFiles() {
		return files;
	}

	public void setFiles(Part files) {
		this.files = files;
	}

	public List<Files> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Files> arquivos) {
		this.arquivos = arquivos;
	}

	public Files getArquivo() {
		return arquivo;
	}

	public void setArquivo(Files arquivo) {
		this.arquivo = arquivo;
	}
	

}
