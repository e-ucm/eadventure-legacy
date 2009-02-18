package es.eucm.eadventure.editor.gui.elementpanels.macro;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.MacroReferenceEffectDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.EffectsPanel;

/**
 * Panel for a Macro
 * @author Javier
 *
 */
public class MacroPanel extends JPanel{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1356438513519568096L;
		
	/**
	 * Effects panel
	 */
	private EffectsPanel effectsPanel;
	
	
	private JTextArea documentationTextArea;
	/**
	 * Constructor
	 * @param dataControl
	 */
	public MacroPanel ( MacroDataControl dataControl ){
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		setLayout ( new GridBagLayout( ) );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.weightx = 1;
		
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( dataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) dataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Macro.Documentation" ) ) );
		
		add ( documentationPanel, c );
		
		c.weighty = 0.7;
		c.gridy = 1;
		
		MacroReferenceEffectDialog.ID = dataControl.getId();
		effectsPanel = new EffectsPanel ( dataControl.getController( ) );
		effectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Macro.Effects" ) ) );
		add ( effectsPanel, c );
	}
}
