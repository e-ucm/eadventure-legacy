/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.GraphicRepresentation;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ConversationCellRenderer extends ResizeableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (ConversationDataControl) value2;
		name = ((ConversationDataControl) value2).getId();
		
		GraphicRepresentation graphicRepresentation = new GraphicRepresentation((GraphConversationDataControl) value2, new Dimension(200,250));
		float scale = 1.0f;
		scale -= 0.1f * ((GraphConversationDataControl) value2).getAllNodesViews().size();
		if (scale < 0.1f)
			scale = 0.1f;
		graphicRepresentation.setScale(scale);
		image = new BufferedImage(200, 250, BufferedImage.TYPE_4BYTE_ABGR);
		graphicRepresentation.drawConversation(image.getGraphics());
		
		return createPanel();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (ConversationDataControl) value2;
		name = ((ConversationDataControl) value2).getId();
		
		GraphicRepresentation graphicRepresentation = new GraphicRepresentation((GraphConversationDataControl) value2, new Dimension(200,200));
		float scale = 1.0f;
		scale -= 0.1f * ((GraphConversationDataControl) value2).getAllNodesViews().size();
		if (scale < 0.1f)
			scale = 0.1f;
		graphicRepresentation.setScale(scale);
		image = new BufferedImage(200, 200, BufferedImage.TYPE_4BYTE_ABGR);
		graphicRepresentation.drawConversation(image.getGraphics());

		return createPanel();
	}
}
