package es.eucm.eadventure.engine.resourcehandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.media.MediaLocator;

import es.eucm.eadventure.common.loader.InputStreamCreator;

public class ResourceHandlerExternalSource extends ResourceHandler{

	private InputStreamCreator isCreator;
	
	private static ResourceHandlerExternalSource instance;
	
	public static ResourceHandlerExternalSource getInstance(){
		return instance;
	}
	
	public static void create( InputStreamCreator isCreator ){
		instance = new ResourceHandlerExternalSource(isCreator);
	}
	
	private ResourceHandlerExternalSource ( InputStreamCreator isCreator ){
		this.isCreator = isCreator;
	}
	
	@Override
	public OutputStream getOutputStream(String path) {
		return null;
	}

	@Override
	public MediaLocator getResourceAsMediaLocator(String path) {
		return isCreator.buildMediaLocator(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return buildInputStream(path);
	}

	@Override
	public URL getResourceAsURLFromZip(String path) {
		return isCreator.buildURL(path);
	}

	@Override
	public void setZipFile(String zipFilename) {
		// Do nothing (isCreator already knows the zip file)
	}

	public InputStream buildInputStream(String filePath) {
		return isCreator.buildInputStream(filePath);
	}

	public String[] listNames(String filePath) {
		return isCreator.listNames(filePath);
	}

	@Override
	public MediaLocator buildMediaLocator(String file) {
		return isCreator.buildMediaLocator(file);
	}

	@Override
	public URL buildURL(String path) {
		return isCreator.buildURL(path);
	}

	@Override
	public URL getResourceAsURL(String path) {
		return buildURL (path);
	}

}
