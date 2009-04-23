package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.conversation.DeleteLineAudioPathTool;
import es.eucm.eadventure.editor.control.tools.conversation.SelectLineAudioPathTool;

public class AudioCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private ConversationNodeView value;
	
	private JButton deleteButton;
	
	private JLabel label;
	
	private JPanel panel;
	
	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		if (value2 == null)
			return null;
		this.value = (ConversationNodeView) value2;
		return createPanel(row, isSelected, table);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		this.value = (ConversationNodeView) value;
		if (!isSelected) {
			JLabel label;
			if (this.value.hasAudioPath(row)) {
				ImageIcon icon = new ImageIcon("img/icons/audio.png");
				String[] temp = this.value.getAudioPath(row).split("/");
				label = new JLabel(temp[temp.length - 1], icon, SwingConstants.LEFT);
			} else {
				ImageIcon icon = new ImageIcon("img/icons/noAudio.png");
				label = new JLabel(TextConstants.getText("Conversations.NoAudio"), icon, SwingConstants.LEFT);
			}
			
			return label;
		} else
			return createPanel(row, isSelected, table);

	}
	
	private JPanel createPanel(int row, boolean isSelected, JTable table) {
		final int line = row;
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		if (this.value.hasAudioPath(row)) {
			ImageIcon icon = new ImageIcon("img/icons/audio.png");
			String[] temp = this.value.getAudioPath(row).split("/");
			label = new JLabel(temp[temp.length - 1], icon, SwingConstants.LEFT);
		} else {
			ImageIcon icon = new ImageIcon("img/icons/noAudio.png");
			label = new JLabel(TextConstants.getText("Conversations.NoAudio"), icon, SwingConstants.LEFT);
		}
		panel.add(label);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 2.0;
		
		JButton selectButton = new JButton(TextConstants.getText("Conversations.Select" ) );
		selectButton.setFocusable(false);
		selectButton.setEnabled(isSelected);
		selectButton.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				try {
					Controller.getInstance().addTool(new SelectLineAudioPathTool( value.getConversationLine( line ) ) );
					updateTable(line);
				} catch (CloneNotSupportedException e1) {
					ReportDialog.GenerateErrorReport(new Exception ("Could not clone resources"), false, TextConstants.getText("Error.Title"));
				}
			}
		} );
		buttonPanel.add(selectButton, c);
		
		c.gridx = 1;
		c.weightx = 0.2;
		deleteButton = new JButton(new ImageIcon("img/icons/deleteContent.png"));
		deleteButton.setToolTipText(TextConstants.getText("Conversations.DeleteAudio" ) );
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setFocusable(false);
		deleteButton.setEnabled(isSelected && value.hasAudioPath(line));
		deleteButton.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				Controller.getInstance().addTool(new DeleteLineAudioPathTool(value.getConversationLine(line)));
				updateTable(line);
			}
		} );
		buttonPanel.add(deleteButton, c);
		
		panel.add(buttonPanel);
		return panel;
	}
	
	private void updateTable(int row) {
		deleteButton.setEnabled(value.hasAudioPath(row));
		if (this.value.hasAudioPath(row)) {
			ImageIcon icon = new ImageIcon("img/icons/audio.png");
			String[] temp = this.value.getAudioPath(row).split("/");
			label.setText(temp[temp.length - 1]);
			label.setIcon(icon);
		} else {
			ImageIcon icon = new ImageIcon("img/icons/noAudio.png");
			label.setText(TextConstants.getText("Conversations.NoAudio"));
			label.setIcon(icon);
		}
		panel.updateUI();
	}
}
