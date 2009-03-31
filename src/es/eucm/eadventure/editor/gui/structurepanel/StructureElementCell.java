package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import es.eucm.eadventure.common.gui.TextConstants;
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
	
	private boolean renaming;
	
	private boolean isSelected;
	
	private JTextField name;
		
	public StructureElementCell(StructureElement value, JTable table, boolean isSelected) {
		setOpaque(true);
		setBackground(Color.white);
		this.value = value;
		this.table = table;
		this.isSelected = isSelected;
		setLayout(new GridLayout(0, 1));

		recreate();
		
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				        if (name != null && !name.hasFocus()) {
				            name.selectAll();
				        	name.requestFocusInWindow();
				        }
				    }
				});
			}
			public void focusLost(FocusEvent arg0) {
				
			}
		});

	}
		
	public void recreate() {
		removeAll();

		if (isSelected && !renaming && !((StructureElement) value).isJustCreated()) {
			JLabel label = new JLabel(((StructureElement) value).getName(), SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(Font.BOLD));
			add(label);
			setBorder(BorderFactory.createLineBorder(Color.blue, 2));
			
			boolean hasOptions = false;
			JPanel optionsPanel = new JPanel();
			optionsPanel.setBackground(Color.WHITE);
			optionsPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			if (((StructureElement) value).isCanRename()) {
				JButton rename = new JButton(TextConstants.getText("GeneralText.Rename"));
				
				rename.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						renaming = true;
						recreate();
						repaint();
						updateUI();
					}
				});
				
				rename.setFocusable(false);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 2.0f;
				optionsPanel.add(rename, c);
				c.gridx++;
				hasOptions = true;
			}
			if (((StructureElement) value).canBeRemoved()) {
				JButton remove = new JButton(new ImageIcon("img/icons/deleteNode.png"));
				remove.setContentAreaFilled( false );
				remove.setMargin( new Insets(0,0,0,0) );
				remove.setToolTipText(TextConstants.getText("GeneralText.Delete"));
				remove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Controller.getInstance().addTool(new RemoveElementTool(StructureElementCell.this.table, StructureElementCell.this.value));
					}
				});
				
				remove.setFocusable(false);
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0.0f;
				optionsPanel.add(remove, c);
				hasOptions = true;
			}
			if (hasOptions)
				add(optionsPanel);
			this.setMinimumSize(new Dimension(this.getWidth(), 60));
		} else if (isSelected && (renaming || ((StructureElement) value).isJustCreated())) {
//			JLabel label = new JLabel(((StructureElement) value).getName(), SwingConstants.CENTER);
			name = new JTextField(((StructureElement) value).getName());
			name.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					acceptRename();
				}
			});
			name.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				public void focusLost(FocusEvent arg0) {
					cancelRename();
				}
			});
//			label.setFont(label.getFont().deriveFont(Font.BOLD));
			add(name);
			name.requestFocusInWindow();
			setBorder(BorderFactory.createLineBorder(Color.blue, 2));
			
			
			
			JPanel optionsPanel = new JPanel();
			optionsPanel.setLayout(new GridLayout(1,0));
			optionsPanel.setBackground(Color.WHITE);

			JButton rename = new JButton(TextConstants.getText("GeneralText.OK"));
			
			rename.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					acceptRename();
				}
			});
			rename.setFocusable(false);
			optionsPanel.add(rename);

			JButton cancel = new JButton(TextConstants.getText("GeneralText.Cancel"));
				
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cancelRename();
				}
			});
		
			cancel.setFocusable(false);
				optionsPanel.add(cancel);
			add(optionsPanel);
			this.setMinimumSize(new Dimension(this.getWidth(), 60));
		} else {
			renaming = false;
			JLabel label;
			if (((StructureElement) value).getIcon() == null)
				label = new JLabel(" " + ((StructureElement) value).getName(), SwingConstants.LEFT);
			else
				label = new JLabel(((StructureElement) value).getName(), ((StructureElement) value).getIcon(), SwingConstants.LEFT);
			add(label);
			setBorder(BorderFactory.createEmptyBorder());
			this.setMinimumSize(new Dimension(this.getWidth(), 20));
		}

	}
	
	private void acceptRename() {
		Controller.getInstance().addTool(new RenameElementTool(StructureElementCell.this.value.getDataControl(), name.getText()));				
		((StructureElement) value).setJustCreated(false);		
		renaming = false;
		recreate();
		repaint();
		updateUI();
	}
	
	private void cancelRename() {
		renaming = false;
		recreate();
		repaint();
		updateUI();
		if (((StructureElement) value).isJustCreated()) {
			((StructureElement) value).setJustCreated(false);		
			((StructureElement) value).delete(false);
			table.clearSelection();
			Controller.getInstance().updateTree();
		}
	}
	
	public Object getValue() {
		return value;
	}
}
