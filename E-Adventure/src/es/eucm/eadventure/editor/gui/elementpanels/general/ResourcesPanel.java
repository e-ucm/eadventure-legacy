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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	private GridBagConstraints c;
	
	List<JPanel> assetPanels;
	
	private int selectedIndex = 0;
	
	/**
	 * Constructor.
	 * 
	 * @p
	 * aram dataControl
	 *            Resources data control
	 */
	public ResourcesPanel( ResourcesDataControl dataControl ) {
		super( );
		this.resourcesDataControl = dataControl;
		assetPanels = new ArrayList<JPanel>();
		
		int assetGroups = dataControl.getAssetGroupCount();
		if (assetGroups == 1) {
			createSingleGroupPanel();
		} else {
			createMultiGroupPanel();
		}
	}

	public ResourcesPanel(ResourcesDataControl dataControl,
			int selectedIndex2) {
		super( );
		selectedIndex = selectedIndex2;
		this.resourcesDataControl = dataControl;
		assetPanels = new ArrayList<JPanel>();
		
		int assetGroups = dataControl.getAssetGroupCount();
		if (assetGroups == 1) {
			createSingleGroupPanel();
		} else {
			createMultiGroupPanel();
		}

	}

	private void createMultiGroupPanel() {
		setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 2, 4, 2, 4 );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridy = 0;
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), TextConstants.getText( "Resources.ResourcesGroup")));
		final JComboBox groupCombo = new JComboBox();
		for (int i = 0; i < resourcesDataControl.getAssetGroupCount(); i++) {
			groupCombo.addItem(resourcesDataControl.getGroupInfo(i));
		}
		groupCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSelectedGroup(groupCombo.getSelectedIndex());
			}
		});
		panel.add(groupCombo, BorderLayout.CENTER);
		add(panel, c);
		
		groupCombo.setSelectedIndex(selectedIndex);
		setSelectedGroup(selectedIndex);
		
	}

	protected void setSelectedGroup(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );
		
		while (!assetPanels.isEmpty()) {
			remove(assetPanels.get(0));
			assetPanels.remove(0);
		}
		
		int assetCount = resourcesDataControl.getGroupAssetCount( selectedIndex );
		assetFields = new JTextField[assetCount];
		viewButtons = new JButton[assetCount];

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridy = 2;
		c.weighty = 0;
		
		for( int j = 0; j < assetCount; j++ ) {
			int i = resourcesDataControl.getAssetIndex(selectedIndex, j);
			
			JPanel assetPanel = new JPanel( );
			assetPanel.setLayout( new GridBagLayout( ) );
			assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), resourcesDataControl.getAssetDescription( i ) ) );
			GridBagConstraints c2 = new GridBagConstraints( );
			c2.insets = new Insets( 2, 2, 2, 2 );
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			c2.weighty = 0;

			JButton deleteContentButton = new JButton( deleteContentIcon );
			deleteContentButton.addActionListener( new DeleteContentButtonListener( i , j) );
			deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
			deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
			assetPanel.add( deleteContentButton, c2 );

			assetFields[j] = new JTextField( MAX_SPACE );
			assetFields[j].setText( resourcesDataControl.getAssetPath( i ) );
			assetFields[j].setEditable( false );
			c2.gridx = 1;
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1;
			assetPanel.add( assetFields[j], c2 );

			JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
			selectButton.addActionListener( new ExamineButtonListener( i , j) );
			c2.gridx = 2;
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			assetPanel.add( selectButton, c2 );
			
			if (resourcesDataControl.getAssetCategory(i) == AssetsController.CATEGORY_ANIMATION) {
				JButton editButton = new JButton( TextConstants.getText("Resources.Create") + "/" + TextConstants.getText("Resources.Edit"));
				editButton.addActionListener( new EditButtonListener( i, j ));
				c2.gridx++;
				assetPanel.add(editButton);
			}
			
			viewButtons[j] = new JButton( getPreviewText( i ) );
			viewButtons[j].setEnabled( resourcesDataControl.getAssetPath( i ) != null );
			viewButtons[j].addActionListener( new ViewButtonListener( i ) );
			c2.gridx++;
			assetPanel.add( viewButtons[j], c2 );

			c.gridy++;
			add( assetPanel, c );
			assetPanels.add(assetPanel);
		}
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		if (previewUpdater != null)
			this.previewUpdater.updateResources(selectedIndex);
		
		this.updateUI();
	}

	private void createSingleGroupPanel() {
		// Load the image for the delete content button
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		setLayout( new GridBagLayout( ) );

		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 2, 4, 2, 4 );

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridy = 0;
		c.weighty = 0;

		// Create the fields
		int assetCount = resourcesDataControl.getAssetCount( );
		assetFields = new JTextField[assetCount];
		viewButtons = new JButton[assetCount];

		// For every asset type of the resources, create and add a subpanel
		for( int i = 0; i < assetCount; i++ ) {

			// Create the panel and set the border
			JPanel assetPanel = new JPanel( );
			assetPanel.setLayout( new GridBagLayout( ) );
			assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), resourcesDataControl.getAssetDescription( i ) ) );
			GridBagConstraints c2 = new GridBagConstraints( );
			c2.insets = new Insets( 2, 2, 2, 2 );
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			c2.weighty = 0;

			// Create the delete content button
			JButton deleteContentButton = new JButton( deleteContentIcon );
			deleteContentButton.addActionListener( new DeleteContentButtonListener( i, i ) );
			deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
			deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
			assetPanel.add( deleteContentButton, c2 );

			// Create the text field and insert it
			assetFields[i] = new JTextField( MAX_SPACE );
			assetFields[i].setText( resourcesDataControl.getAssetPath( i ) );
			assetFields[i].setEditable( false );
			c2.gridx = 1;
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1;
			assetPanel.add( assetFields[i], c2 );

			// Create the "Select" button and insert it
			JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
			selectButton.addActionListener( new ExamineButtonListener( i , i) );
			c2.gridx = 2;
			c2.fill = GridBagConstraints.NONE;
			c2.weightx = 0;
			assetPanel.add( selectButton, c2 );
			
			// Create the "Create/Edit" button when necessary
			if (resourcesDataControl.getAssetCategory(i) == AssetsController.CATEGORY_ANIMATION) {
				JButton editButton = new JButton( TextConstants.getText("Resources.Create") + "/" + TextConstants.getText("Resources.Edit"));
				editButton.addActionListener( new EditButtonListener(i, i));
				c2.gridx++;
				assetPanel.add(editButton, c2);
			} else if (resourcesDataControl.isIconFromImage(i)) {
				JButton createIconButton = new JButton( TextConstants.getText("Resources.CreateIcon"));
				createIconButton.addActionListener( new CreateIconButtonListener(i));
				c2.gridx++;
				assetPanel.add(createIconButton, c2);
			}
			
			
			
			viewButtons[i] = new JButton( getPreviewText( i ) );
			viewButtons[i].setEnabled( resourcesDataControl.getAssetPath( i ) != null );
			viewButtons[i].addActionListener( new ViewButtonListener( i ) );
			c2.gridx++;
			assetPanel.add( viewButtons[i], c2 );

			c.gridy++;
			add( assetPanel, c );
		}
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
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
			case AssetsController.CATEGORY_BUTTON:
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
	 * This class is the listener for the "Delete content" buttons on the panels.
	 */
	private class DeleteContentButtonListener implements ActionListener {

		/**
		 * Index of the asset.
		 */
		private int assetIndex;
		
		private int fieldIndex;

		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public DeleteContentButtonListener( int assetIndex , int fieldIndex) {
			this.assetIndex = assetIndex;
			this.fieldIndex = fieldIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			resourcesDataControl.deleteAssetPath( assetIndex );
			assetFields[fieldIndex].setText( null );
			viewButtons[fieldIndex].setEnabled( false );
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

		
		private int fieldIndex;
		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public ExamineButtonListener( int assetIndex , int fieldIndex) {
			this.assetIndex = assetIndex;
			this.fieldIndex = fieldIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e )  {
			resourcesDataControl.editAssetPath( assetIndex );
			assetFields[fieldIndex].setText( resourcesDataControl.getAssetPath( assetIndex ) );
			viewButtons[fieldIndex].setEnabled( resourcesDataControl.getAssetPath( assetIndex ) != null );
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

		private int fieldIndex;
		
		/**
		 * Constructor.
		 * 
		 * @param assetIndex
		 *            Index of the asset
		 */
		public EditButtonListener( int assetIndex , int fieldIndex) {
			this.assetIndex = assetIndex;
			this.fieldIndex = fieldIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {			
			if (resourcesDataControl.getAssetPath(assetIndex) != null && resourcesDataControl.getAssetPath(assetIndex).toLowerCase().endsWith(".eaa") && !resourcesDataControl.getAssetPath(assetIndex).equals(AssetsController.ASSET_EMPTY_ANIMATION)) {
				new AnimationEditDialog(resourcesDataControl.getAssetPath(assetIndex), null);
			} else {
				String filename= null;
				String animationName = "anim" + (new Random()).nextInt(1000);
				if (resourcesDataControl.getAssetPath(assetIndex) != null) {
					String[] temp = resourcesDataControl.getAssetPath(assetIndex).split("/");
					animationName = temp[temp.length-1];
					filename = AssetsController.TempFileGenerator.generateTempFileOverwriteExisting(animationName, "eaa");				
				} else {
					animationName = JOptionPane.showInputDialog(null, TextConstants.getText("Animation.AskFilename"), TextConstants.getText("Animation.AskFilenameTitle"), JOptionPane.QUESTION_MESSAGE);
					if (animationName!=null && animationName.length() > 0) {
						filename = AssetsController.TempFileGenerator.generateTempFileOverwriteExisting(animationName, "eaa");
					//} else {
					//	filename = AssetsController.TempFileGenerator.generateTempFileAbsolutePath(animationName, "eaa");
					//}
					}
				}
				if (filename!=null){
				File file = new File(filename);
				file.create();
				AnimationWriter.writeAnimation(filename, new Animation(animationName));
				
				Animation animation = new Animation(animationName);
				animation.setDocumentation(resourcesDataControl.getAssetDescription(assetIndex));
				if (resourcesDataControl.getAssetPath(assetIndex) != null) {
					// Añadir las imagenes de la animación antigua
					//animation.framesFromImages(resourcesDataControl.getAssetPath(assetIndex));
					resourcesDataControl.framesFromImages(animation, resourcesDataControl.getAssetPath(assetIndex));
					AnimationWriter.writeAnimation(filename, animation);
				}
				// Poner la nueva animacion en el assetPath
				//AssetsController.addSingleAsset(AssetsController.CATEGORY_ANIMATION, filename);
				//String uri = AssetsController.categoryFolders()[AssetsController.CATEGORY_ANIMATION] + "/" + file.getName();

				resourcesDataControl.setAssetPath(filename, assetIndex);
				
				new AnimationEditDialog(resourcesDataControl.getAssetPath(assetIndex), animation);
				}
				
			}
			
			assetFields[fieldIndex].setText( resourcesDataControl.getAssetPath( assetIndex ) );
			viewButtons[fieldIndex].setEnabled( resourcesDataControl.getAssetPath( assetIndex ) != null );
			
			if( previewUpdater != null ) {
				previewUpdater.updateResources( );
			}
		}
	}

	/**
	 * This class is the listener for the "Create icon" buttons on the panels.
	 */
	private class CreateIconButtonListener implements ActionListener {

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
		public CreateIconButtonListener( int assetIndex ) {
			this.assetIndex = assetIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			String imagePath = resourcesDataControl.getAssetPath(resourcesDataControl.getOriginalImage(assetIndex));
			if (imagePath != null && !imagePath.equals("")) {
				String[] temp = imagePath.split("/");
				String newName = temp[temp.length-1];
				newName = "icon_" + newName.substring(0, newName.length() - 4);
				String filename = AssetsController.TempFileGenerator.generateTempFileOverwriteExisting(newName, "png");				
				Image original = AssetsController.getImage( imagePath );
				BufferedImage bufferdOriginal = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
				bufferdOriginal.getGraphics().drawImage(original, 0, 0, original.getWidth(null), original.getHeight(null), null);
				BufferedImage newImage = new BufferedImage(80, 48, BufferedImage.TYPE_4BYTE_ABGR);
				
				int width = original.getWidth(null);
				int height = original.getHeight(null);
				
				int tempWidth = 80;
				int tempHeight = (int) ((float) height / (float) width * 80);
				if (tempHeight > 48) {
					tempHeight = 48;
					tempWidth = (int) ((float) width / (float) height * 48);
				}
				
				Graphics2D g = (Graphics2D) newImage.getGraphics();
				System.out.println("" + tempWidth + " x " + tempHeight);
				int dx1 = (80 - tempWidth) / 2;
				int dy1 = (48 - tempHeight) / 2;
				
				float scale = (float) tempWidth / (float) width;
	            AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
	            AffineTransformOp aop = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
				
	            g.drawImage(bufferdOriginal, aop, dx1, dy1);
				g.finalize();

				File file = new File(filename);
				try {
					ImageIO.write(newImage, "png", file);
				} catch (IOException e1) {
					return;
				}
				
				resourcesDataControl.setAssetPath(filename, assetIndex);
				assetFields[assetIndex].setText( resourcesDataControl.getAssetPath( assetIndex ) );
				
				
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
				case AssetsController.CATEGORY_BUTTON:
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

	public int getSelectedIndex() {
		return selectedIndex;
	}
}
