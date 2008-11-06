/**
 * E-Adventure3D project.
 * E-UCM group, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J.
 * @year 2007
 */
package es.eucm.eadventure.engine.resourcehandler.zipurl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.schlichtherle.io.FileInputStream;

/**
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J.
 * 
 */
public class ZipURLConnection extends URLConnection {

	private String assetPath;
	private String zipFile;

	/**
	 * @param url
	 * @throws MalformedURLException
	 */
	public ZipURLConnection(URL assetURL, String zipFile, String assetPath) {
		super(assetURL);
		this.assetPath = assetPath;
		this.zipFile = zipFile;
	}

	/**
	 * @param url
	 * @throws MalformedURLException
	 */
	public ZipURLConnection(URL assetURL, String assetPath) {
		super(assetURL);
		this.assetPath = assetPath;
		zipFile = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
	}

	@Override
	public InputStream getInputStream() {
		if (assetPath != null) {
			return buildInputStream();
		} else {
			try {
				return url.openStream();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			// return AssetsController.getInputStream(assetPath);
		}
	}

	private InputStream buildInputStream() {
		try {
			if (zipFile != null) {
				return new FileInputStream(zipFile + "/" + assetPath);
			} else {
				return new FileInputStream(assetPath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
