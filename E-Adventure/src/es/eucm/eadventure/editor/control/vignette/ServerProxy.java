/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *
 *    Copyright 2005-2012 e-UCM research group.
 *
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 *
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.control.vignette;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class ServerProxy {

	private static boolean requestedProxy = false;

	private static HttpHost currentProxy = null;

	private static String mac;

    private String serviceURL;

    private String userPath = null;

    public ServerProxy(String serviceURL, String userPath){
        this.serviceURL = serviceURL;
        this.userPath = userPath;
    }

	public String getMac() {
		if (mac == null) {
			try {
				byte[] macBytes = NetworkInterface
						.getNetworkInterfaces().nextElement().getHardwareAddress();
				mac = new String(Hex.encodeHex(macBytes));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mac;
	}

	public boolean exportAndShowJson(List<VignetteCharacterPreview> vcps, String json) {
		for (VignetteCharacterPreview vcp : vcps) {
			System.err.println("Sending " + vcp.getName());
			if ( ! sendBytes(
					vcp.getImageBytes(),
					"image/png",
					vcp.getImageName())) {
				return false;
			}
		}

		if ( ! sendBytes(
				json.getBytes(Charset.forName("UTF-8")),
				"text/json",
				"json.json")) {
			return false;
		}

		boolean error = false;
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(serviceURL
						+ "/talk.php?"
						+ "m=" + getMac() + "&f=" + userPath));
			} catch (IOException e) {
				error = true;
				e.printStackTrace();
			} catch (URISyntaxException e) {
				error = true;
				e.printStackTrace();
			}
		}
		return error;
	}

	private HttpClient getProxiedHttpClient() {

		HttpClient hc = new DefaultHttpClient();
		HttpHost hh = null;
		if ( ! requestedProxy) {
			requestedProxy = true;
			hh = ProxySetup.buildHttpProxy(null);
		} else {
			hh = currentProxy;
		}
        if (hh != null) {
			hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, hh);
		}
		return hc;
	}

	private boolean sendBytes(final byte[] bytes, String mime, String name) {
		boolean sent = false;
		try {
            HttpClient httpclient = getProxiedHttpClient();
            String url = serviceURL
					+ "/save.php?"
					+ "m=" + getMac() + "&f=" + userPath + "&n=" + name;

            HttpPost post = new HttpPost(url);
            post.setEntity( new ByteArrayEntity(bytes));
			post.setHeader("Content-Type", mime);
            HttpResponse response;
            response = httpclient.execute(post);
            if (response!=null &&
                    Integer.toString( response.getStatusLine( ).getStatusCode( ) ).startsWith( "2" )){
                    sent=true;
            }
        }
        catch( ClientProtocolException e ) {
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        return sent;
	}

    public String getJson(){
		String json = null;
        try {
            HttpClient httpclient = getProxiedHttpClient();
            String url = serviceURL
					+ "/load.php?"
					+ "m=" +getMac() + "&f=" + userPath;

			HttpGet hg = new HttpGet(url);
            HttpResponse response;
            response = httpclient.execute(hg);
            if (Integer.toString( response.getStatusLine( ).getStatusCode( ) ).startsWith( "2" )){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();

                    StringWriter strWriter = new StringWriter();

                    char[] buffer = new char[1024];
                    try {
                        Reader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
                        int n;
                        while ((n = reader.read(buffer)) != -1) {
                            strWriter.write(buffer, 0, n);
                        }
                    } finally {
                        instream.close();
                    }
					json = strWriter.toString();
                }
            }
        }
        catch( ClientProtocolException e ) {
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        return json;
    }
}
