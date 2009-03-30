package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class StructureElementRenderer extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = -2371497952304186775L;

	public StructureElementRenderer() {
		setOpaque(true);
		setBackground(Color.white);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean isFocus, int row, int col) {
		removeAll();
		setLayout(new GridLayout(0, 1));

		if (isSelected) {
			JLabel label = new JLabel(((StructureElement) value).getName(), SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			add(label);
			setBorder(BorderFactory.createLineBorder(Color.blue, 2));
			if (((StructureElement) value).isCanRename())
				add(new JButton("rename"));
			if (((StructureElement) value).canBeRemoved())
				add(new JButton("remove"));
			this.setMinimumSize(new Dimension(this.getWidth(), 60));
		} else {
			JLabel label;
			if (((StructureElement) value).getIcon() == null)
				label = new JLabel(((StructureElement) value).getName(), SwingConstants.LEFT);
			else
				label = new JLabel(((StructureElement) value).getName(), ((StructureElement) value).getIcon(), SwingConstants.LEFT);
			add(label);
			setBorder(BorderFactory.createEmptyBorder());
			this.setMinimumSize(new Dimension(this.getWidth(), 20));
		}
		return this;
	}

}
