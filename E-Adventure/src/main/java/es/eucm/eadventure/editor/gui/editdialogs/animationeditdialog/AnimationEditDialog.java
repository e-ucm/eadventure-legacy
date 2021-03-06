/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.EditorImageLoader;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.writer.AnimationWriter;
import es.eucm.eadventure.editor.gui.displaydialogs.AnimationDialog;
import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;
import es.eucm.eadventure.editor.gui.editdialogs.ToolManagableDialog;

/**
 * This class shows an dialog to edit an animation
 * 
 * @author Eugenio Marchiori
 * 
 */
public class AnimationEditDialog extends ToolManagableDialog {

    /**
     * Default generated serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The list that shows the frames in the animation
     */
    private JList frameList;

    /**
     * The panel where the frame list is displayed along with the buttons to
     * order said frames and delete them
     */
    private JPanel frameListPanel;

    /**
     * The panel where the description and general information of the animation
     * is displayed
     */
    private JPanel descriptionPanel;

    /**
     * The panel where the configuration of the current frame or transition is
     * displayed
     */
    private JPanel configurationPanel;

    /**
     * The button to move a frame to the left
     */
    private JButton moveLeftButton;

    /**
     * The button to move a frame to the right
     */
    private JButton moveRightButton;

    /**
     * The button to delete a frame
     */
    private JButton deleteButton;

    /**
     * The button to add a frame
     */
    private JButton addButton;
    
    /**
     * The button to duplicate a frame
     */
    private JButton duplicateButton;

    /**
     * JCheckBox to set the useTransitions property of the animation
     */
    private JCheckBox useTransitions;

    /**
     * JCheckBox to set the slides property of the animation
     */
    private JCheckBox slides;

    /**
     * The text field that displays the documentation of the animation
     */
    private JTextField documentationTextField;

    /**
     * The data control for the animation
     */
    private AnimationDataControl animationDataControl;

    /**
     * FrameConfigPanel for the selected frame
     */
    private FrameConfigPanel frameConfigPanel;

    /**
     * Empty constructor. Creates a new animation.
     */
    public AnimationEditDialog( ) {

        super( Controller.getInstance( ).peekWindow( ), "", true );
        animationDataControl = new AnimationDataControl( new Animation( "id", new Frame(new EditorImageLoader()), new EditorImageLoader() ) );

        buildInterface( );
    }

    /**
     * Constructor with a filename and optional animation. If the animation is
     * null, it tries to open it form the given file.
     * 
     * @param filename
     *            Path to the eaa(xml) with the animation
     * @param animation
     *            The animation to edit (can be null)
     */
    public AnimationEditDialog( String filename, Animation animation ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "Animation.DialogTitle", filename ), true );
        if( animation == null ) {
            animationDataControl = new AnimationDataControl( Loader.loadAnimation( AssetsController.getInputStreamCreator( ), filename, new EditorImageLoader()  ) );
        }
        else {
            animationDataControl = new AnimationDataControl( animation );
        }
        animationDataControl.setFilename( filename );

        buildInterface( );
    }

    /**
     * Build the edition interface with all of it's components
     */
    private void buildInterface( ) {

        this.setLayout( new GridBagLayout( ) );
        this.setModalExclusionType( ModalExclusionType.APPLICATION_EXCLUDE );
        this.setModalityType( ModalityType.APPLICATION_MODAL );
        this.setModal( true );
        this.setResizable( false );
        this.setTitle( TC.get( "Animation.DialogTitle", animationDataControl.getFilename( ) ) );

        createDescriptionPanel( );

        GridBagConstraints gbc = new GridBagConstraints( );
        gbc.gridy = 0;
        gbc.weighty = 0.5;
        this.add( descriptionPanel, gbc );

        AnimationListModel listModel = new AnimationListModel( animationDataControl );

        frameList = new JList( listModel );
        frameList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        frameList.getSelectionModel( ).addListSelectionListener( new AnimationListSelectionListener( this ) );
        frameList.setLayoutOrientation( JList.HORIZONTAL_WRAP );
        frameList.setCellRenderer( new AnimationListRenderer( ) );
        frameList.setVisibleRowCount( 1 );
        JScrollPane listScroller = new JScrollPane( frameList );
        listScroller.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
        listScroller.setPreferredSize( new Dimension( 520, 160 ) );

        frameListPanel = new JPanel( );
        frameListPanel.add( listScroller, BorderLayout.CENTER );

        frameListPanel.add( createButtonPanel( ), BorderLayout.SOUTH );

        frameListPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Animation.Timeline" ) ) );
        frameListPanel.setMinimumSize( new Dimension( 600, 240 ) );
        gbc.gridy = 1;
        gbc.weighty = 1;
        this.add( frameListPanel, gbc );

        configurationPanel = new JPanel( );
        configurationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Animation.Details" ) ) );
        configurationPanel.setMinimumSize( new Dimension( 600, 200 ) );
        gbc.gridy = 2;
        gbc.weighty = 0.7;
        this.add( configurationPanel, gbc );//, BorderLayout.SOUTH);

        JPanel acceptCancelPanel = new JPanel( );

        JButton preview = new JButton( TC.get( "Animation.Preview" ) );
        preview.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new AnimationDialog( (Animation) animationDataControl.getContent( ) );
            }
        } );
        acceptCancelPanel.add( preview );

        acceptCancelPanel.setMinimumSize( new Dimension( 600, 50 ) );
        JButton accept = new JButton( TC.get( "GeneralText.OK" ) );
        accept.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                saveAndClose( );
            }
        } );
        acceptCancelPanel.add( accept );

        JButton cancel = new JButton( TC.get( "GeneralText.Cancel" ) );
        cancel.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                AnimationEditDialog.this.setVisible( false );
            }
        } );
        acceptCancelPanel.add( cancel );

        JButton helpButton = new JButton( new ImageIcon( "img/icons/information.png" ) );
        helpButton.setContentAreaFilled( false );
        helpButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        helpButton.setFocusable( false );
        helpButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new HelpDialog( "general/Animation_Editor.html" );
            }
        } );
        acceptCancelPanel.add(helpButton);
        
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        this.add( acceptCancelPanel, gbc );

        this.setSize( 600, 650 );

        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );

    }

    /**
     * Creates the description panel, where all the elements that describe the
     * animation are placed
     */
    private void createDescriptionPanel( ) {

        descriptionPanel = new JPanel( );
        descriptionPanel.setLayout( new GridLayout( 2, 1 ) );

        JPanel temp = new JPanel( );
        temp.setLayout( new GridBagLayout( ) );

        GridBagConstraints gbc = new GridBagConstraints( );

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        temp.add( new JLabel( TC.get( "Animation.Documentation" ) ), gbc );

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        documentationTextField = new JTextField( animationDataControl.getDocumentation( ) );
        documentationTextField.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextField, (Documented) animationDataControl.getContent( ) ) );
        temp.add( documentationTextField, gbc );

        descriptionPanel.add( temp );

        temp = new JPanel( );
        temp.setLayout( new GridBagLayout( ) );

        gbc = new GridBagConstraints( );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        useTransitions = new JCheckBox( TC.get( "Animation.UseTransitions" ) );
        if( animationDataControl.isUseTransitions( ) )
            useTransitions.setSelected( true );
        else
            useTransitions.setSelected( false );
        useTransitions.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                changeUseTransitions( );
            }
        } );
        temp.add( useTransitions, gbc );

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        slides = new JCheckBox( TC.get( "Animation.Slides" ) );
        if( animationDataControl.isSlides( ) )
            slides.setSelected( true );
        else
            slides.setSelected( false );
        slides.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                changeSlides( );
            }
        } );
        temp.add( slides, gbc );

        descriptionPanel.add( temp );
        descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Animation.GeneralInfo" ) ) );
        descriptionPanel.setMinimumSize( new Dimension( 600, 100 ) );

    }

    protected void changeSlides( ) {

        if( slides.isSelected( ) != animationDataControl.isSlides( ) ) {
            animationDataControl.setSlides( slides.isSelected( ) );
            frameList.getSelectionModel( ).clearSelection( );
            configurationPanel.removeAll( );
            this.validate( );
            this.repaint( );
        }
    }

    protected void changeUseTransitions( ) {

        if( useTransitions.isSelected( ) != animationDataControl.isUseTransitions( ) ) {
            animationDataControl.setUseTransitions( useTransitions.isSelected( ) );
            frameList.getSelectionModel( ).clearSelection( );
            frameList.updateUI( );
            configurationPanel.removeAll( );
            this.validate( );
            this.repaint( );
        }
    }

    /**
     * Creates the button panel, where the buttons used for the manipulation of
     * the timeline are placed
     * 
     * @return A JPanel with the needed elements
     */
    private JPanel createButtonPanel( ) {

        JPanel buttons = new JPanel( );
        moveLeftButton = new JButton( new ImageIcon( "img/icons/moveNodeLeft.png" ) );
        moveLeftButton.setContentAreaFilled( false );
        moveLeftButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveLeftButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveLeftButton.setToolTipText( TC.get( "Animation.MoveFrameLeft" ) );
        moveLeftButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                moveFrameLeft( );
            }
        } );
        moveLeftButton.setEnabled( false );
        buttons.add( moveLeftButton );

        deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteButton.setToolTipText( TC.get( "Animation.DeleteFrame" ) );
        deleteButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                deleteFrame( );
            }
        } );
        deleteButton.setEnabled( false );
        buttons.add( deleteButton );

        addButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        addButton.setContentAreaFilled( false );
        addButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        addButton.setBorder( BorderFactory.createEmptyBorder( ) );
        addButton.setToolTipText( TC.get( "Animation.AddFrame" ) );
        addButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                addFrame( );
            }
        } );
        buttons.add( addButton );


        duplicateButton = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
        duplicateButton.setContentAreaFilled( false );
        duplicateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        duplicateButton.setBorder( BorderFactory.createEmptyBorder( ) );
        duplicateButton.setToolTipText( TC.get( "Animation.DuplicateFrame" ) );
        duplicateButton.setEnabled( false );
        duplicateButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                duplicateFrame( );
            }
        } );
        buttons.add( duplicateButton );

        
        moveRightButton = new JButton( new ImageIcon( "img/icons/moveNodeRight.png" ) );
        moveRightButton.setContentAreaFilled( false );
        moveRightButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveRightButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveRightButton.setToolTipText( TC.get( "Animation.MoveFrameRight" ) );
        moveRightButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                moveFrameRight( );
            }
        } );
        moveRightButton.setEnabled( false );
        buttons.add( moveRightButton );

        return buttons;
    }

    /**
     * Save changes to the eaa file and close the edition panel
     */
    protected void saveAndClose( ) {

        if( animationDataControl.getFilename( ) != null ) {
            AnimationWriter.writeAnimation( animationDataControl.getFilename( ), (Animation) animationDataControl.getContent( ) );
        }
        this.setVisible( false );
    }

    /**
     * Method to add a new frame to the animation. If a frame is selected it
     * adds it right after it.
     */
    protected void addFrame( ) {

        Frame newFrame = new Frame( animationDataControl.getImageLoaderFactory( )  );

        int index = frameList.getSelectedIndex( ) / 2;
        if( !animationDataControl.isUseTransitions( ) ) {
            index = frameList.getSelectedIndex( );
        }
        if( frameList.getSelectedIndex( ) == -1 )
            index = animationDataControl.getFrameCount( ) - 1;

        animationDataControl.addFrame( index, newFrame );

        frameList.updateUI( );
        int newFrameIndex = animationDataControl.indexOfFrame( newFrame );
        frameList.setSelectedIndex( newFrameIndex );
    }

    /**
     * Method to add a duplicate the selected frame to the animation. The new frame will be added
     * just after the original one.
     */
    protected void duplicateFrame( ) {

        Frame newFrame = new Frame( animationDataControl.getImageLoaderFactory( )  );
        
        int index = frameList.getSelectedIndex( ) / 2;
        if( !animationDataControl.isUseTransitions( ) ) {
            index = frameList.getSelectedIndex( );
        }
        if( frameList.getSelectedIndex( ) == -1 )
            index = animationDataControl.getFrameCount( ) - 1;

        FrameDataControl frameToDuplicate = animationDataControl.getFrameDataControl( index );
        newFrame.setUri( frameToDuplicate.getImageURI( ) );
        newFrame.setSoundUri( frameToDuplicate.getSoundUri( ) );
        newFrame.setTime( frameToDuplicate.getTime( ) );
        
        animationDataControl.duplicateFrame( index, newFrame );

        frameList.updateUI( );
        int newFrameIndex = animationDataControl.indexOfFrame( newFrame );
        frameList.setSelectedIndex( newFrameIndex );
    }

    
    /**
     * Delete the selected frame from the list
     */
    protected void deleteFrame( ) {

        if( frameList.getSelectedValue( ) instanceof FrameDataControl ) {
            animationDataControl.deleteFrame( (FrameDataControl) frameList.getSelectedValue( ) );
            frameList.getSelectionModel( ).clearSelection( );
            configurationPanel.removeAll( );
            this.validate( );
            this.repaint( );
        }
    }

    /**
     * Move the selected frame in the list to the left
     */
    protected void moveFrameLeft( ) {

        FrameDataControl temp = (FrameDataControl) frameList.getSelectedValue( );
        animationDataControl.moveFrameLeft( temp );
        frameList.setSelectedValue( temp, true );
        frameList.updateUI( );
    }

    /**
     * Move the selected frame in the list to the right
     */
    protected void moveFrameRight( ) {

        FrameDataControl temp2 = (FrameDataControl) frameList.getSelectedValue( );
        animationDataControl.moveFrameRight( temp2 );
        frameList.setSelectedValue( temp2, true );
        frameList.updateUI( );
    }

    /**
     * Class that holds the internal representation of the list of frames and
     * transitions
     * 
     */
    private class AnimationListModel extends AbstractListModel {

        private static final long serialVersionUID = -2832912217451105062L;

        private AnimationDataControl animation;

        public AnimationListModel( AnimationDataControl animation ) {

            super( );
            this.animation = animation;
        }

        public Object getElementAt( int index ) {

            if( animation.isUseTransitions( ) ) {
                if( index % 2 == 0 ) {
                    return animation.getFrameDataControl( index / 2 );
                }
                else {
                    return animation.getTransitionDataControls( ).get( ( index - 1 ) / 2 + 1 );
                }
            }
            else {
                return animation.getFrameDataControl( index );
            }
        }

        public int getSize( ) {

            if( animation.isUseTransitions( ) )
                return ( animation.getFrameCount( ) * 2 ) - 1;
            else
                return animation.getFrameCount( );
        }
    }

    /**
     * This class responds to the changes of selection in the list of frames
     * 
     */
    private class AnimationListSelectionListener implements ListSelectionListener {

        private AnimationEditDialog aed;

        public AnimationListSelectionListener( AnimationEditDialog aed ) {

            this.aed = aed;
        }

        public void valueChanged( ListSelectionEvent e ) {

            ListSelectionModel lsm = (ListSelectionModel) e.getSource( );
            if( lsm.isSelectionEmpty( ) ) {
            }
            else {
                int index = lsm.getMinSelectionIndex( );
                aed.selectionChanged( index );
            }
        }

    }

    /**
     * Method called when the selection changes with the index of the selected
     * item
     * 
     * @param index
     *            the index of the selection
     */
    public void selectionChanged( int index ) {

        if( animationDataControl.isUseTransitions( ) ) {
            if( index < 0 || index >= ( animationDataControl.getFrameCount( ) * 2 ) + 1 ) {
                selectedNothing( );
                return;
            }
            if( index % 2 == 0 ) {
                selectedFrame( index / 2 );
            }
            else {
                selectedTransition( ( index - 1 ) / 2 + 1 );
            }
        }
        else {
            if( index < 0 || index >= animationDataControl.getFrameCount( ) ) {
                selectedNothing( );
                return;
            }
            selectedFrame( index );
        }
    }

    /**
     * Method called when a frame is selected
     * 
     * @param i
     *            index of the selected frame
     */
    public void selectedFrame( int i ) {

        if( i < animationDataControl.getFrameCount( ) - 1 )
            moveRightButton.setEnabled( true );
        else
            moveRightButton.setEnabled( false );
        if( i > 0 )
            moveLeftButton.setEnabled( true );
        else
            moveLeftButton.setEnabled( false );
        deleteButton.setEnabled( true );
        duplicateButton.setEnabled( true );

        configurationPanel.removeAll( );
        frameConfigPanel = new FrameConfigPanel( animationDataControl.getFrameDataControl( i ), frameList, this );
        configurationPanel.add( frameConfigPanel );
        this.validate( );
        this.repaint( );
    }

    /**
     * Method called when the selection is changed to nothing
     */
    public void selectedNothing( ) {

        moveRightButton.setEnabled( false );
        moveLeftButton.setEnabled( false );
        deleteButton.setEnabled( false );
        duplicateButton.setEnabled( false );
        configurationPanel.removeAll( );
        this.validate( );
        this.repaint( );

    }

    /**
     * Method called when a transition is selected
     * 
     * @param i
     *            index of the selected transition
     */
    public void selectedTransition( int i ) {

        moveRightButton.setEnabled( false );
        moveLeftButton.setEnabled( false );
        deleteButton.setEnabled( false );
        duplicateButton.setEnabled( false );

        configurationPanel.removeAll( );
        configurationPanel.add( new TransitionConfigPanel( animationDataControl.getTransitionDataControls( ).get( i ), frameList ) );
        this.validate( );
        this.repaint( );
    }

    public AnimationDataControl getAnimationDataControl( ) {

        return animationDataControl;
    }

    @Override
    public boolean updateFields( ) {

        if( descriptionPanel != null ) {
            this.documentationTextField.setText( animationDataControl.getDocumentation( ) );
            this.useTransitions.setSelected( animationDataControl.isUseTransitions( ) );
            this.slides.setSelected( animationDataControl.isSlides( ) );
            descriptionPanel.updateUI( );
        }
        if( configurationPanel != null ) {
            this.selectionChanged( this.frameList.getSelectedIndex( ) );
        }
        if( frameListPanel != null ) {
            frameListPanel.updateUI( );
            frameList.updateUI( );
        }
        this.validate( );
        this.repaint( );
        return true;
    }

}
