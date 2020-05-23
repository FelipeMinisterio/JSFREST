package br.com.config;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("/resources")
public class ApplicationConfig extends Application{

	@Override
	public Set<Class<?>> getClasses() {
	Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
	addRestResourceClasses(resources);
		return resources;
	}
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(br.com.service.PatrimonioService.class);
		resources.add(br.com.service.MarcaService.class);
		resources.add(br.com.service.FileUploadService.class);
		resources.add(MultiPartFeature.class);
	}
	
}