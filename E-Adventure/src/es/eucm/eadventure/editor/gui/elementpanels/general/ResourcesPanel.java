package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.writer.AnimationWriter;
import es.eucm.eadventure.editor.gui.displaydialogs.AnimationDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.AudioDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.ImageDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.SlidesDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.VideoDialog;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog.AnimationEditDialog;

/**
 * This class is the panel which represents the information held in a resources structure. It can edit the data, and
 * display the elements selected
 * 
 * @author Bruno Torijano Bueno
 */
public class ResourcesPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_SPACE = 0;

	/**
	 * The data control of the resources.
	 */
	private ResourcesDataControl resourcesDataControl;

	/**
	 * The text fields with the asset paths.
	 */
	private JTextField[] assetFields;

	/**
	 * The buttons with the "View" option.
	 */
	private JButton[] viewButtons;

	private LooksPanel previewUpdater;

	/**
	 * Constructor.
	 * 
	 * @param dataControl
	 *            Resources data control
	 */
	public ResourcesPanel( ResourcesDataControl dataControl ) {
		//super( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		super( );
		this.resourcesDataControl = dataControl;

		// Load the image for the delete content button
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		// Remove the border of the scroll pane
		//setBorder( null );

		// Create the panel to set in the scroll pane
		//JPanel resourcesPanel = new JPanel( );

		// Set the properties of the panel
		//resourcesPanel.setLayout( new GridBagLayout( ) );
		setLayout( new GridBagLayout( ) );
		//resourcesPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Resources.Title" ) ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Resources.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 2, 4, 2, 4 );

		// Create and insert a text with information about this panel
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "Resources.Information" ) );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridy = 0;
		c.weighty = 0;
		//resourcesPanel.add( informationTextPane, c );
		add( informationTextPane, c );

		// Create the button for the conditions
		c.gridy = 1;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Resources.Conditions" ) ) );
		//resourcesPanel.add( conditionsPanel, c );
		add( conditionsPanel, c );

		// Create the fields
		int assetCount = dataControl.getAssetCount( );
		assetFields = new JTextField[assetCount];
		viewButtons = new JButton[assetCount];

		// For every asset type of the resources, create and add a subpanel
		for( int i = 0; i < assetCount; i++ ) {

			// Create the panel and set the border
			JPanel assetPanel = new JPanel( );
			assetPanel.setLayout( new GridBagLayout( ) );
			assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), dataControl.getAssetDescription( i ) ) );
			GridBagConstraints c2 = new GridBagConstraints( );
			c2.insets = new Insets( 2, 2, 2, 2 );
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			c2.weighty = 0;

			// Create the delete content button
			JButton deleteContentButton = new JButton( deleteContentIcon );
			deleteContentButton.addActionListener( new DeleteContentButtonListener( i ) );
			deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
			deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
			assetPanel.add( deleteContentButton, c2 );

			// Create the text field and insert it
			assetFields[i] = new JTextField( MAX_SPACE );
			assetFields[i].setText( dataControl.getAssetPath( i ) );
			assetFields[i].setEditable( false );
			c2.gridx = 1;
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1;
			assetPanel.add( assetFields[i], c2 );

			// Create the "Select" button and insert it
			JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
			selectButton.addActionListener( new ExamineButtonListener( i ) );
			c2.gridx = 2;
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			assetPanel.add( selectButton, c2 );
			
			// Create the "Create/Edit" button when necessary
			if (resourcesDataControl.getAssetCategory(i) == AssetsController.CATEGORY_ANIMATION) {
				JButton editButton = new JButton( TextConstants.getText("Resources.Create"));
				editButton.addActionListener( new EditButtonListener(i));
				c2.gridx++;
				assetPanel.add(editButton);
			}
			
			// Create the "View" button and insert it
			viewButtons[i] = new JButton( getPreviewText( i ) );
			viewButtons[i].setEnabled( dataControl.getAssetPath( i ) != null );
			viewButtons[i].addActionListener( new ViewButtonListener( i ) );
			c2.gridx++;
			assetPanel.add( viewButtons[i], c2 );

			// Add the panel
			c.gridy++;
			//resourcesPanel.add( assetPanel, c );
			add( assetPanel, c );
		}

		// Add a filler at the end
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		//add( new JFiller( ), c );
		//add( new JFiller( ), c );

		// TODO Parche, arreglar
		//resourcesPanel.setPreferredSize( new Dimension( 0, assetCount * 80 ) );
		//setPreferredSize( new Dimension( 0, assetCount * 80 ) );
		//setMaximumSize( new Dimension( 0, assetCount * 80 ) );

		// Insert the panel into the scroll
		//setViewportView( resourcesPanel );
	}

	/**
	 * Returns the text for the preview button.
	 * 
	 * @param index
	 *            Index of the asset
	 * @return Text for the preview button
	 */
	private String getPreviewText( int index ) {
		String previewText = null;

		switch( resourcesDataControl.getAssetCategory( index ) ) {
			case AssetsController.CATEGORY_BACKGROUND:
			case AssetsController.CATEGORY_ANIMATION:
			case AssetsController.CATEGORY_IMAGE:
			case AssetsController.CATEGORY_ICON:
				previewText = TextConstants.getText( "Resources.ViewAsset" );
				break;
			case AssetsController.CATEGORY_AUDIO:
				previewText = TextConstants.getText( "Resources.PlayAsset" );
				break;
			case AssetsController.CATEGORY_VIDEO:
				previewText = TextConstants.getText( "Resources.PlayVideoAsset" );
				break;

		}

		return previewText;
	}

	/**
	 * Listener for the edit conditions button.
	 */
	private class ConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( resourcesDataControl.getConditions( ) );
		}
	}

	/**
	 * This class is the listener for the "Delete content" buttons on the panels.
	 */
	private class DeleteContentButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public DeleteContentButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			resourcesDataControl.deleteAssetPath( assetIndex );
			assetFields[assetIndex].setText( null );
			viewButtons[assetIndex].setEnabled( false );
			if( previewUpdater != null )
				previewUpdater.updateResources( );
		}
	}

	/**
	 * This class is the listener for the "Examine" buttons on the panels.
	 */
	private class ExamineButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public ExamineButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			resourcesDataControl.editAssetPath( assetIndex );
			assetFields[assetIndex].setText( resourcesDataControl.getAssetPath( assetIndex ) );
			viewButtons[assetIndex].setEnabled( resourcesDataControl.getAssetPath( assetIndex ) != null );
			if( previewUpdater != null ) {
				previewUpdater.updateResources( );
			}
		}
	}

	/**
	 * This class is the listener for the "Edit" buttons on the panels.
	 */
	private class EditButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public EditButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {			
			if (resourcesDataControl.getAssetPath(assetIndex) != null && resourcesDataControl.getAssetPath(assetIndex).toLowerCase().endsWith(".eaa")) {
				// Ya es una animación
				new AnimationEditDialog(resourcesDataControl.getAssetPath(assetIndex), null);
			} else {
				// Crear la animación como el nuevo recurso
				String filename;
				if (resourcesDataControl.getAssetPath(assetIndex) != null) {
					String[] temp = resourcesDataControl.getAssetPath(assetIndex).split("/");
					filename = AssetsController.TempFileGenerator.generateTempFileAbsolutePath(temp[temp.length-1], "eaa");				
				} else {
					filename = AssetsController.TempFileGenerator.generateTempFileAbsolutePath("eaa");
				}
				File file = new File(filename);
				file.create();
				AnimationWriter.writeAnimation(filename, new Animation("id"));
				
				Animation animation = new Animation("anim" + (new Random()).nextInt(1000));
				animation.setDocumentation(resourcesDataControl.getAssetDescription(assetIndex));
				if (resourcesDataControl.getAssetPath(assetIndex) != null) {
					// Añadir las imagenes de la animación antigua
					animation.framesFromImages(resourcesDataControl.getAssetPath(assetIndex));
					AnimationWriter.writeAnimation(filename, animation);
				}
				// Poner la nueva animacion en el assetPath
				//AssetsController.addSingleAsset(AssetsController.CATEGORY_ANIMATION, filename);
				//String uri = AssetsController.categoryFolders()[AssetsController.CATEGORY_ANIMATION] + "/" + file.getName();

				resourcesDataControl.setAssetPath(filename, assetIndex);
				
				new AnimationEditDialog(resourcesDataControl.getAssetPath(assetIndex), animation);
				
			}
			
			assetFields[assetIndex].setText( resourcesDataControl.getAssetPath( assetIndex ) );
			viewButtons[assetIndex].setEnabled( resourcesDataControl.getAssetPath( assetIndex ) != null );
			
			if( previewUpdater != null ) {
				previewUpdater.updateResources( );
			}
		}
	}

	
	/**
	 * This class is the listener for the "View" buttons on the panels.
	 */
	private class ViewButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public ViewButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {

			int assetType = resourcesDataControl.getAssetCategory( assetIndex );
			String assetPath = resourcesDataControl.getAssetPath( assetIndex );

			switch( assetType ) {
				case AssetsController.CATEGORY_BACKGROUND:
				case AssetsController.CATEGORY_IMAGE:
				case AssetsController.CATEGORY_ICON:
					new ImageDialog( assetPath );
					break;
				case AssetsController.CATEGORY_ANIMATION:
					if( resourcesDataControl.getAssetFilter( assetIndex ) == AssetsController.FILTER_JPG  && !assetPath.endsWith(".eaa"))
						new SlidesDialog( assetPath );
					else
						new AnimationDialog( assetPath );
					break;
				case AssetsController.CATEGORY_AUDIO:
					new AudioDialog( assetPath );
					break;
				case AssetsController.CATEGORY_VIDEO:
					new VideoDialog( assetPath );
					break;
			}
		}
	}

	/**
	 * @return the previewUpdater
	 */
	public LooksPanel getPreviewUpdater( ) {
		return previewUpdater;
	}

	/**
	 * @param previewUpdater the previewUpdater to set
	 */
	public void setPreviewUpdater( LooksPanel previewUpdater ) {
		this.previewUpdater = previewUpdater;
	}
}
