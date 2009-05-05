package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import es.eucm.eadventure.common.gui.TextConstants;

public class TableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 2423910731205436129L;
	
	private JTable table;
	
	private String textMessage;
	
	public TableScrollPane(JTable table, int verticalPolicy, int horizontalPolicy) {
		super(table, verticalPolicy, horizontalPolicy);
		this.table = table;
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent arg0) {
				repaint();
			}
		});
		this.textMessage = TextConstants.getText("GeneralText.EmptyTable");
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (table.getRowCount() == 0) {
			int x = this.getWidth() / 2;
			int y = this.getHeight() / 2;
			g.setColor(Color.BLACK);
			x -= g.getFontMetrics().stringWidth(textMessage) / 2;
			g.drawString(textMessage, x, y);
		}
	}

	
}
