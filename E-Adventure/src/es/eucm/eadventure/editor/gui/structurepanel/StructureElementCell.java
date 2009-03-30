package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.structurepanel.RemoveElementTool;
import es.eucm.eadventure.editor.control.tools.structurepanel.RenameElementTool;

public class StructureElementCell extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2096132139472242178L;

	private StructureElement value;
	
	private JTable table;
		
	public StructureElementCell(StructureElement value, JTable table, boolean isSelected) {
		setOpaque(true);
		setBackground(Color.white);
		this.value = value;
		this.table = table;
		
		setLayout(new GridLayout(0, 1));

		if (isSelected) {
			JLabel label = new JLabel(((StructureElement) value).getName(), SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			add(label);
			setBorder(BorderFactory.createLineBorder(Color.blue, 2));
			if (((StructureElement) value).isCanRename()) {
				JButton rename = new JButton("rename");
				rename.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Controller.getInstance().addTool(new RenameElementTool(StructureElementCell.this.table, StructureElementCell.this.value.getDataControl()));
					}
				});
				rename.setFocusable(false);
				add(rename);
			}
			if (((StructureElement) value).canBeRemoved()) {
				JButton remove = new JButton("remove");
				remove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Controller.getInstance().addTool(new RemoveElementTool(StructureElementCell.this.table, StructureElementCell.this.value));
					}
				});
				remove.setFocusable(false);
				add(remove);
			}
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
		
	}

	public Object getValue() {
		return value;
	}

}
