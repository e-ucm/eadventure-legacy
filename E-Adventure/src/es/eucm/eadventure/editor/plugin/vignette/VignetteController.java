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
package es.eucm.eadventure.editor.plugin.vignette;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.swing.JOptionPane;

import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;

public class VignetteController {

	private static Random r = new Random();
	private static String serviceURL;

	public static boolean isInit() {
		return serviceURL != null;
	}

	public static void init() {
		if (!isInit()) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream("vignette.config"));
				serviceURL = properties.getProperty("service-url");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void exportConversation(VignetteConversationWrapper cw) {
		if (!isInit()) {
			init();
		}

		if (cw.getId() == null) {
			cw.setId("" + r.nextInt(Integer.MAX_VALUE));
		}

		List<VignetteCharacterPreview> vcps = cw.getCharacters(48, 48);
		VignetteConversation vc = new VignetteConversation();
		vc.build(vcps, cw.getConversation());

		ServerProxy sp = new ServerProxy(serviceURL, cw.getId());
		sp.exportAndShowJson(vcps, vc.toJson());
	}

	public static void importConversation(VignetteConversationWrapper cw) {
	    if (!isInit()) {
            init();
        }
	    
		ServerProxy sp = new ServerProxy(serviceURL, cw.getId());
		String json = sp.getJson();

		VignetteConversation vc = new VignetteConversation();
		vc.build(json);
		try {
			GraphConversation graphConversation = vc.toConversation(
					cw.getConversation().getId(), cw.getId());
			cw.updateConversation(graphConversation);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"<html>There is some kind of problem with the conversation you"
					+ " are trying to import. We are sorry - there is currently"
					+ " no way to fix it. <br> Vignette and eAdventure are not "
					+ " entirely one-to-one compatible. <br>"
					+ "Either try to fix the conversation in Vignette, or "
					+ " try to edit your current conversation in eAdventure.</html>",
					"We are sorry", JOptionPane.ERROR_MESSAGE);
		}
	}
}
