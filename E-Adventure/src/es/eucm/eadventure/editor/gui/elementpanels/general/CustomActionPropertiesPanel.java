package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;

/**
 * The panel for the edition of a custom action
 * 
 * @author Eugenio Marchiori
 *
 */
public class CustomActionPropertiesPanel extends JPanel implements ActionTypePanel{

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The data control for the custom action
	 */
	private CustomActionDataControl customActionDataControl;
	
	/**
	 * The panel where the general action configuration is displayed
	 */
	private ActionPropertiesPanel actionPanel;
	
	/**
	 * The panel used to configure the resources
	 */
	private CustomActionLooksPanel looksPanel;
	
	/**
	 * The tab panel that allows to switch between the ActionPanel
	 * and the one with personalization elements
	 */
	private JTabbedPane tabPanel;
	
	/**
	 * The text field with the name of the action
	 */
	private JTextField nameTextField;
	
	
	/**
	 * Defaul constructor
	 * 
	 * @param customActionDataControl the dataControl for the customaction
	 */
	public CustomActionPropertiesPanel(CustomActionDataControl customActionDataControl) {
		this.customActionDataControl = customActionDataControl;
		
		tabPanel = new JTabbedPane( );
		
		actionPanel = new ActionPropertiesPanel(customActionDataControl);
		
		JPanel personalizationPanel = createPersonalizationPanel();
		
		tabPanel.insertTab( TextConstants.getText( "CustomAction.PersonalizationTitle" ), null, personalizationPanel, TextConstants.getText( "CustomAction.PersonalizationTip" ), 0 );
		tabPanel.insertTab( TextConstants.getText( "CustomAction.ConfigurationTitle" ), null, actionPanel, TextConstants.getText( "CustomAction.ConfigurationTip" ), 1 );

		setLayout( new BorderLayout( ) );
		add( tabPanel, BorderLayout.CENTER );

	}
	
	/**
	 * Creates the panel where the personalization elements of the action
	 * are placed.
	 * 
	 * @return the panel with the necessary elements
	 */
	private JPanel createPersonalizationPanel() {
		JPanel personalizationPanel = new JPanel();
		personalizationPanel.setLayout(new GridBagLayout());
		
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		c.weightx = 1.0;
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;

		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( customActionDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener( nameTextField, (Named) customActionDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CustomAction.Name" ) ) );
		personalizationPanel.add(namePanel, c);
		
		c.weighty = 0.3;
		c.gridy++;
		c.weighty = 1.0;
		looksPanel = new CustomActionLooksPanel( customActionDataControl );
		looksPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CustomAction.Appearance" ) ) );
		personalizationPanel.add(looksPanel, c);
		
		
		return personalizationPanel;
	}

	private class CustomActionLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CustomActionLooksPanel( DataControlWithResources control ) {
			super( control );
			// TODO Parche, arreglar
			lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

		}

		@Override
		protected void createPreview( ) {

		}

		@Override
		public void updatePreview( ) {
			getParent( ).getParent( ).repaint( );
		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).getParent( ).repaint( );
		}

	}

	@Override
	public int getType() {
	    return ActionTypePanel.CUSTOM_TYPE;
	}
	
}
