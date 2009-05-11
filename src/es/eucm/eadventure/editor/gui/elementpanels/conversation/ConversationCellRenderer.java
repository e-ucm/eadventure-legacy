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
