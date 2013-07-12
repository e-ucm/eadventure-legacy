/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *
 *    Copyright 2005-2012 <e-UCM> research group.
 *
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 *
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.control.vignette;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Hex;

/**
 * This class holds the name of a character and an image of 48x48 pixels to serve as a preview of the character.
 * @author Javier Torrente
 *
 */
public class VignetteCharacterPreview {

    private String name;

    private BufferedImage image;

	private String imageName = null;

	private byte[] imageBytes = null;

    public String getName( ) {

        return name;
    }


    public void setName( String name ) {
        this.name = name;
    }


    public BufferedImage getImage( ) {

        return image;
    }


    public void setImage( BufferedImage image ) {
        this.image = image;
		updateBytes();
    }

	private void updateBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
			baos.close();
			imageBytes = baos.toByteArray();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(imageBytes);
			imageName = Hex.encodeHexString(digest) + ".png";
			System.err.println("Wrote " + imageBytes + " bytes to " + imageName);
		} catch (Exception e) {
			System.err.println("Error encoding & md5ing:");
			e.printStackTrace();
		}
	}

	public String getImageName() {
		return imageName;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}
}
