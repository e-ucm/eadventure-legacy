/**
 * E-Adventure3D project.
 * E-UCM group, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J.
 * @year 2007
 */
package es.eucm.eadventure.editor.control.auxiliar.zipurl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J.
 * 
 */
public class ZipURLStreamHandler extends URLStreamHandler {

	private String assetPath;
	private String zipFile;

	public ZipURLStreamHandler(String zipFile, String assetPath) {
		this.assetPath = assetPath;
		this.zipFile = zipFile;
	}

	public ZipURLStreamHandler(String assetPath) {
		this.assetPath = assetPath;
		zipFile = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		if (zipFile != null)
            return new ZipURLConnection(u, zipFile, assetPath);
        return new ZipURLConnection(u, assetPath);
	}

}
