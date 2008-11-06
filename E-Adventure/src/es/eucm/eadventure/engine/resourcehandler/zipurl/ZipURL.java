/**
 * E-Adventure3D project.
 * E-UCM group, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J.
 * @year 2007
 */
package es.eucm.eadventure.engine.resourcehandler.zipurl;

import java.net.MalformedURLException;
import java.net.URL;

import de.schlichtherle.io.File;

/**
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J.
 * 
 */
public class ZipURL {

	public ZipURL() {

	}

	public static URL createAssetURL(String zipFile, String assetPath)
			throws MalformedURLException {
		URL url = null;

		// File parentFile = new File(Controller.getInstance().getZipFile());
		File parentFile = new File(zipFile);
		File file = new File(parentFile, assetPath);

		url = file.toURI().toURL();
		url = new URL(url.getProtocol(), url.getHost(), url.getPort(), url
				.getFile(), new ZipURLStreamHandler(zipFile, assetPath));

		return url;
	}

	public static URL createAssetURL(File file) throws MalformedURLException {
		URL url = null;

		url = file.toURI().toURL();
		url = new URL(url.getProtocol(), url.getHost(), url.getPort(), url
				.getFile(), new ZipURLStreamHandler(file.getAbsolutePath()));

		return url;
	}

}
