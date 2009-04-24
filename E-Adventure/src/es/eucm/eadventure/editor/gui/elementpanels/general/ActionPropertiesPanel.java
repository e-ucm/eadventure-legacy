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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.MacroReferenceEffectDialog;

public class ActionPropertiesPanel extends JPanel implements ActionTypePanel{

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
		add( new EffectsPanel( actionDataControl.getEffects( ) ), c );
		
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

		    @Override
		    public void actionPerformed(ActionEvent e) {
			new EffectsDialog( actionDataControl.getNotEffectsController() );
			
		    }
		    
		});
		editNotEff.setEnabled(this.actionDataControl.isActivatedNotEffects());
		
		final JCheckBox enableNotEff = new JCheckBox(TextConstants.getText("Exit.ActiveWhenConditionsArent"));
		enableNotEff.setSelected(this.actionDataControl.isActivatedNotEffects());
		enableNotEff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    actionDataControl.setActivatedNotEffects(enableNotEff.isSelected());
			    editNotEff.setEnabled(enableNotEff.isSelected());
			}
		});

		notEffContainer.add(enableNotEff,c);
		c.gridy++;
		notEffContainer.add(editNotEff,c);
		return notEffContainer;
	}

	@Override
	public int getType() {
	    return ActionTypePanel.ACTION_TYPE;
	}
}
