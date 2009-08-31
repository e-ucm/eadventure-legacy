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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;

public class SynthesizeCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private ConversationNodeView value;
	
	private String voiceName;
	
	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		if (value2 == null)
			return null;
		this.value = (ConversationNodeView) value2;
		return createPanel(row, isSelected, table);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		this.value = (ConversationNodeView) value;
		if (!isSelected) {
			JCheckBox checkBox = new JCheckBox(TextConstants.getText("Conversations.Synthesize"));
			checkBox.setSelected(this.value.getConversationLine(row).getSynthesizerVoice());
			checkBox.setEnabled(isSelected && canSynthesize(row));
			checkBox.setOpaque(false);
			return checkBox;
		} else
			return createPanel(row, isSelected, table);

	}
	
	private JPanel createPanel(int row, boolean isSelected, JTable table) {
		final int line = row;
		JPanel panel = new JPanel();
		if (!isSelected)
			panel.setBackground(table.getBackground());
		else
			panel.setBackground(table.getSelectionBackground());
		panel.setLayout(new GridLayout(2,1));
		
		JCheckBox checkBox = new JCheckBox(TextConstants.getText("Conversations.Synthesize"));
		checkBox.setSelected(this.value.getConversationLine(row).getSynthesizerVoice());
		checkBox.setEnabled(isSelected && canSynthesize(row));
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().addTool(new ChangeBooleanValueTool(value.getConversationLine(line), ((JCheckBox) e.getSource()).isSelected(), "getSynthesizerVoice", "setSynthesizerVoice"));
			}
		});
		checkBox.setOpaque(false);
		panel.add(checkBox);
		

		JButton button = new JButton(TextConstants.getText("Conversations.Listen" ) );
		button.setFocusable(false);
		button.setEnabled(isSelected && canSynthesize(row));
		button.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				VoiceManager voiceManager = VoiceManager.getInstance();
				Voice voice = voiceManager.getVoice(voiceName);
				voice.allocate();
				voice.speak(value.getLineText(line));
			}
		} );
		button.setOpaque(false);
		panel.add(button);
		return panel;
	}

	
	private boolean canSynthesize(int row) {
		Controller controller = Controller.getInstance();
		boolean player = false;
		DataControlWithResources control = null;
		String name = value.getLineName(row);
		if (!name.equals("")){
			if (name.equals("Player")){
				control = controller.getSelectedChapterDataControl().getPlayer();
				player = true;
			}
			else{
				for (NPCDataControl npc : controller.getSelectedChapterDataControl().getNPCsList().getNPCs())
					if (name.equals(npc.getId())){
						control = npc;
						break;
					}
			}
		} 
		if (control!=null){
			if (player){
				if ( ((PlayerDataControl)control).getVoice()==null || ((PlayerDataControl)control).getVoice().equals(""))
					return false;
				else {
					voiceName = ((PlayerDataControl)control).getVoice();
					return true;
				}
			} else if ( ((NPCDataControl)control).getVoice()==null || ((NPCDataControl)control).getVoice().equals("")) {
				return false;
			} else {
				voiceName = ((NPCDataControl)control).getVoice();
				return true;
			}
		}
		return false;
		
	}
}
