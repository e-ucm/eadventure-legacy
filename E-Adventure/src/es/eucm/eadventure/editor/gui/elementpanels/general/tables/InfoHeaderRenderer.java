package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

public class InfoHeaderRenderer extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = 3777807533524838812L;

	private JButton infoButton;
	
	private MouseListener old = null;
	
	private String helpPath;
	
	public InfoHeaderRenderer() {
		this(null);
	}
	
	public InfoHeaderRenderer(String helpPath) {
		this.helpPath = helpPath;
		setLayout(new GridBagLayout());
	}
	
	public Component getTableCellRendererComponent(final JTable table, final Object value,
		boolean isSelected, boolean hasFocus, int row, final int column) {
		removeAll();
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.DARK_GRAY));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		JLabel label = new JLabel(value.toString());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		add(label, c);
		if (helpPath != null) {
			infoButton = new JButton(new ImageIcon("img/icons/information.png"));
			infoButton.setContentAreaFilled( false );
			infoButton.setMargin( new Insets(0,0,0,0) );
			infoButton.setBorder(BorderFactory.createEmptyBorder());
			c.gridx = 1;
			add(infoButton, c);
			JTableHeader header = table.getTableHeader(); 
			if (old != null)
				header.removeMouseListener(old);
			old = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mouseX = e.getX();
					int mouseY = e.getY();
					mouseX -= infoButton.getX();
					mouseY -= infoButton.getY();
					for (int i = 0; i < column; i++) {
						mouseX -= table.getColumnModel().getColumn(i).getWidth();
					}
					if(infoButton.contains(mouseX, mouseY)) {
						new HelpDialog(helpPath);
					}
				}
			};
	        header.addMouseListener(old);
		}
		return this;
	}

}
