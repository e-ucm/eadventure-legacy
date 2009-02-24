package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;

public abstract class LooksPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DataControlWithResources dataControl;

	protected JPanel lookPanel;

	protected GridBagConstraints cLook;

	/**
	 * Combo box for the selected resources block.
	 */
	protected JComboBox resourcesComboBox;

	protected JButton newResourcesBlock;

	protected JButton deleteResourcesBlock;

	protected ResourcesPanel resourcesPanel;

	private String[] getResourceNames(){
		String[] resourcesArray = new String[dataControl.getResourcesCount( )];
		for( int i = 0; i < dataControl.getResourcesCount( ); i++ ) {
			resourcesArray[i] = TextConstants.getText( "ResourcesList.ResourcesBlockNumber" ) + ( i + 1 );
		}
		return resourcesArray;
	}
	
	public LooksPanel( DataControlWithResources dControl ) {
		super( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		this.dataControl = dControl;
		lookPanel = new JPanel( );
		lookPanel.setLayout( new GridBagLayout( ) );
		cLook = new GridBagConstraints( );

		// Create the combo resources blocks
		cLook.insets = new Insets( 5, 5, 5, 5 );
		cLook.fill = GridBagConstraints.HORIZONTAL;
		cLook.weightx = 1;
		cLook.gridy = 0;
		cLook.weighty = 0;
		cLook.anchor = GridBagConstraints.LINE_START;
		JPanel resourcesComboPanel = new JPanel( );
		resourcesComboPanel.setLayout( new GridLayout( ) );
		// Create the list of resources

		resourcesComboBox = new JComboBox( getResourceNames() );
		resourcesComboBox.setSelectedIndex( dataControl.getSelectedResources( ) );

		resourcesComboBox.addActionListener( new ResourcesComboBoxListener( ) );

		resourcesComboPanel.add( resourcesComboBox );
		resourcesComboPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ResourcesList.SelectResourcesBlock" ) ) );
		lookPanel.add( resourcesComboPanel, cLook );

		//Create the add button
		newResourcesBlock = new JButton( TextConstants.getText( "Resources.NewBlock" ) );
		newResourcesBlock.addActionListener( new ActionListener( ) {

			// If new Resources block is pressed create a new resources block (dataControl), rebuild the comboBox and the resources Panel
			public void actionPerformed( ActionEvent e ) {
				if( dataControl.addElement( Controller.RESOURCES ) ) {
					dataControl.setSelectedResources( dataControl.getResourcesCount( ) - 1 );
					resourcesComboBox.addItem( TextConstants.getText( "ResourcesList.ResourcesBlockNumber" ) + dataControl.getResourcesCount( ) );
					resourcesComboBox.setSelectedIndex( resourcesComboBox.getItemCount( ) - 1 );
					updateResources( );
				}
			}

		} );
		//Create the delete Block button
		Icon deleteBlock = new ImageIcon( "img/icons/deleteContent.png" );
		this.deleteResourcesBlock = new JButton( deleteBlock );
		deleteResourcesBlock.addActionListener( new ActionListener( ) {

			public void actionPerformed( ActionEvent e ) {
				if ( resourcesComboBox.getSelectedIndex( )>=0 ){
					dataControl.setSelectedResources( resourcesComboBox.getSelectedIndex( ) );
					int selectedBlock = dataControl.getSelectedResources( );
					DataControl resourcesToDelete = dataControl.getResources( ).get( selectedBlock );
					if( dataControl.deleteElement( resourcesToDelete , true ) ) {
						resourcesComboBox.setModel( new DefaultComboBoxModel(getResourceNames()));
						//resourcesComboBox.removeItemAt( selectedBlock );
						dataControl.setSelectedResources( 0 );
						resourcesComboBox.setSelectedIndex( 0 );
						updateResources( );
						resourcesComboBox.updateUI( );
					}
				}
			}

		} );
		JPanel blockControls = new JPanel( );
		blockControls.setLayout( new BoxLayout( blockControls, BoxLayout.LINE_AXIS ) );
		blockControls.add( newResourcesBlock );
		blockControls.add( Box.createHorizontalStrut( 1 ) );
		blockControls.add( deleteResourcesBlock );
		cLook.gridx = 1;
		cLook.weightx = 0.2;
		cLook.anchor = GridBagConstraints.CENTER;
		cLook.weighty = 0;
		//cLook.anchor=GridBagConstraints.BASELINE;
		lookPanel.add( blockControls, cLook );

		//Create the resources Panel
		resourcesPanel = new ResourcesPanel( dataControl.getResources( ).get( dataControl.getSelectedResources( ) ) );
		resourcesPanel.setPreviewUpdater( this );
		cLook.gridy = 1;
		cLook.gridx = 0;
		cLook.gridwidth = 2;
		cLook.fill = GridBagConstraints.BOTH;
		cLook.weightx = 1;
		cLook.weighty = 0.0;
		lookPanel.add( resourcesPanel, cLook );

		// Create and set the preview panel
		cLook.gridy = 2;
		cLook.fill = GridBagConstraints.BOTH;
		cLook.weighty = 1;
		cLook.weightx = 1;
		cLook.gridwidth = 2;
		createPreview( );

		//Add the lookPanel to the ScrollbarPane
		setViewportView( lookPanel );
	}

	/**
	 * Listener for resources combo box.
	 */
	private class ResourcesComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			updateResources( );
		}
	}

	public abstract void updatePreview( );

	protected abstract void createPreview( );

	public void updateResources( ) {
		dataControl.setSelectedResources( resourcesComboBox.getSelectedIndex( ) );
		//multipleImagePanel.loadImage( sceneDataControl.getPreviewBackground( ) );
		//multipleImagePanel.repaint( );
		updatePreview( );
		lookPanel.remove( resourcesPanel );
		resourcesPanel = new ResourcesPanel( dataControl.getResources( ).get( dataControl.getSelectedResources( ) ) );
		resourcesPanel.setPreviewUpdater( this );
		GridBagConstraints cLook = new GridBagConstraints( );
		cLook.gridy = 1;
		cLook.gridx = 0;
		cLook.gridwidth = 2;
		cLook.fill = GridBagConstraints.BOTH;
		cLook.weightx = 1;
		cLook.weighty = 0;
		lookPanel.add( resourcesPanel, cLook );

		//repaint();
		//tabPanel.repaint( );
		//lookPanel.repaint( );
		resourcesPanel.repaint( );
		resourcesPanel.updateUI( );

	}

}
