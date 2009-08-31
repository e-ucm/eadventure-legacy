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
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.MacroReferenceEffectDialog;

public class ActionPropertiesPanel extends JPanel implements ActionTypePanel,Updateable{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;
	
	/**
	 * Data Control
	 */
	private ActionDataControl actionDataControl;
	
	/**
	 * Panel with the associated effects
	 */
	private EffectsPanel effectsPanel;
	
	
	/**
	 * Constructor.
	 * 
	 * @param actionDataControl
	 *            Action controller
	 */
	public ActionPropertiesPanel( ActionDataControl actionDataControl ) {

		setLayout( new GridBagLayout( ) );
		
		this.actionDataControl = actionDataControl;
		
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		// Create the text area for the documentation
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( actionDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) actionDataControl.getContent() ));
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		// TODO Revisar problemas con el layout sin esta linea
		documentationPanel.setMinimumSize( new Dimension( 0, 108 ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Action.Documentation" ) ) );
		add( documentationPanel, c );

		// Create a effects panel and attach it
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		MacroReferenceEffectDialog.ID = null;
		effectsPanel = new EffectsPanel( actionDataControl.getEffects( ) );
		add( effectsPanel, c );
		
		c.gridy++;
		c.weighty = 0;
		c.ipady=-3;
		add( editNotEffects(),c);
		
	}
	
	private JPanel editNotEffects(){
	    GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		
	    // Edit not effects
		JPanel notEffContainer = new JPanel(new GridBagLayout());
		final JButton editNotEff = new JButton(TextConstants.getText("Exit.EditNotEffects"));
		editNotEff.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	new EffectsDialog( actionDataControl.getNotEffectsController() );
		    }
		});
		editNotEff.setEnabled(this.actionDataControl.isActivatedNotEffects());
		
		final JCheckBox enableNotEff = new JCheckBox(TextConstants.getText("Exit.ActiveWhenConditionsArent"));
		enableNotEff.setSelected(this.actionDataControl.isActivatedNotEffects());
		enableNotEff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//			    actionDataControl.setActivatedNotEffects(enableNotEff.isSelected());
			    Controller.getInstance().addTool(new ChangeBooleanValueTool(actionDataControl, enableNotEff.isSelected(), "isActivatedNotEffects","setActivatedNotEffects"));
			    editNotEff.setEnabled(enableNotEff.isSelected());
			}
		});

		notEffContainer.add(enableNotEff,c);
		c.gridy++;
		notEffContainer.add(editNotEff,c);
		return notEffContainer;
	}

	public int getType() {
	    return ActionTypePanel.ACTION_TYPE;
	}

	public boolean updateFields() {
	   
	    return effectsPanel.updateFields();
	}
}
