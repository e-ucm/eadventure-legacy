/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.assetchooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sun.swing.FilePane;
import sun.swing.WindowsPlacesBar;
import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public abstract class AssetChooser extends JFileChooser {

    public static final int DEFAULT_ASSET_CHOOSER = 0;

    public static final int WINDOWS_ASSET_CHOOSER = 1;

    public static final int PREVIEW_LOCATION_WEST = 1;

    public static final int PREVIEW_LOCATION_SOUTH = 0;

    public static final int ASSET_FROM_ZIP = 4;

    public static final int ASSET_FROM_OUTSIDE = 5;

    private JToolBar placesBar;

    private JToggleButton zipContentsButton;

    private ArrayList<JToggleButton> placeButtons;

    private JPanel mainPanel;

    private JPanel centralPanel;

    private Container explorerPanel;

    private JPanel filePanel;

    private JScrollPane projectContentsPanel;

    protected JList assetsList;

    private boolean showingZipContents;

    protected JPanel southPanel;

    private JPanel buttonsPanel;

    private JButton approveButton;

    private JButton cancelButton;

    private int type;

    private int assetCategory;

    protected int filter;

    private int previewLocation;

    private String selectedAsset;
    
    private List<String> selectedAssets;

    private String title;

    public AssetChooser( int assetCategory, int filter, int previewLocation, String title ) {

        super( );
        //Set the filter
        this.assetCategory = assetCategory;
        this.previewLocation = previewLocation;
        this.filter = filter;
        type = categorizeAssetChooser( );
        this.setCurrentDirectory( new File( Controller.getInstance( ).getLastDirectory( ) ) );
        super.setFileFilter( getFilter( ) );
        if( type == WINDOWS_ASSET_CHOOSER ) {
            customizeWindowsChooser( );
        }
        this.title = title;
        this.selectedAssets = new ArrayList<String>();
    }

    private int categorizeAssetChooser( ) {

        /*
         * WINDOWS_ASSET_CHOOSER: It is categorized due to its structure: a WindowsPlacesBar on the west panel 
         * (component 0), filled with JToggleButtons; a JToolBar (component 1) embedding the placesBar and the 
         * upper Bar; a central panel (JPanel) where the file selector is placed. 
         * In this case components 0 and 2 need to be customized 
         * (component 2)
         */
        if( isWindowsCompatible( ) )
            return WINDOWS_ASSET_CHOOSER;

        return DEFAULT_ASSET_CHOOSER;
    }

    private boolean isWindowsCompatible( ) {

        boolean isCompatible = true;
        try {

            // The chooser must have a WindowsPlacesBar(left) and a Central Panel (listPanel + file Panel + buttons Panel)
            if( !( getComponent( 0 ) instanceof WindowsPlacesBar ) || !( getComponent( 2 ) instanceof JPanel ) ) {
                isCompatible = false;
            }

            // The Central panel must have the filePanel (component 1) and the buttonsPanel (component2)
            JPanel centralPanel = (JPanel) getComponent( 2 );
            if( !( centralPanel.getComponent( 1 ) instanceof FilePane ) || !( centralPanel.getComponent( 2 ) instanceof JPanel ) ) {
                isCompatible = false;
            }

            // The buttonsPanel must have 5 panels. The last one is where buttons are located (two buttons)
            JPanel buttonsPanel = (JPanel) centralPanel.getComponent( 2 );
            if( !( buttonsPanel.getComponent( 4 ) instanceof JPanel ) ) {
                isCompatible = false;
            }

        }
        catch( Exception e ) {
            //If an exception is thrown while checking it will not be compatible (probably the instance types did not match)
            isCompatible = false;
        }
        return isCompatible;
    }

    private void customizeWindowsChooser( ) {

        //1)Firstly we need to update the bar, adding a new button for the contents of the zip
        placeButtons = new ArrayList<JToggleButton>( );
        placesBar = (WindowsPlacesBar) getComponent( 0 );

        //Add the buttons of the placesBar to the same ButtonGroup
        ButtonGroup group = new ButtonGroup( );
        for( Component comp : placesBar.getComponents( ) ) {
            if( comp instanceof JToggleButton ) {
                placeButtons.add( (JToggleButton) comp );
                group.add( (JToggleButton) comp );
            }
        }

        //Create the new button and add it to the group and the bar
        zipContentsButton = new JToggleButton( new ImageIcon( "img/file32.png" ) );
        zipContentsButton.setVerticalTextPosition( SwingConstants.BOTTOM );
        zipContentsButton.setHorizontalTextPosition( SwingConstants.CENTER );
        zipContentsButton.setAlignmentX( 0.5f );

        zipContentsButton.setText( TC.get( "GeneralText.Project" ) );
        group.add( zipContentsButton );
        placesBar.add( zipContentsButton );

        //2)Remove the buttons
        filePanel = (JPanel) getComponent( 2 );
        ( (JPanel) filePanel.getComponent( 2 ) ).remove( 4 );
        ( (JPanel) filePanel.getComponent( 2 ) ).remove( 3 );

        //3)Create the central panel
        remove( 2 );
        explorerPanel = filePanel;
        mainPanel = new JPanel( );
        mainPanel.setLayout( new BorderLayout( ) );

        createCentralPanel( );

        //4) Add the listeners: To the new button (zipFile) and to the JFileChooser
        this.addPropertyChangeListener( new ChangeDirectoryListener( ) );
        zipContentsButton.addActionListener( new ChangeDirectoryListener( ) );

    }

    private void customizeDefaultChooser( Container container ) {

        Component mainPanel = container.getComponent( 0 );
        container.removeAll( );

        JPanel newPanel = new JPanel( );
        newPanel.setLayout( this.getLayout( ) );
        /*for (Component component:this.getComponents( )){
        	newPanel.add( component );
        }*/

        //this.removeAll( );
        //this.setLayout( new BorderLayout() );
        //this.add( newPanel, BorderLayout.CENTER );
        this.createDefaultAssetsList( );
        this.assetsList.addListSelectionListener( new DefaultResourcesListListener( ) );

        JPanel assetsPanel = new JPanel( );
        assetsPanel.setLayout( new BorderLayout( ) );
        JPanel approveAssetButton = new JPanel( );
        JButton app = new JButton( "Select Asset" );
        app.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                if( assetsList.getSelectedValue( ) != null ) {
                    selectedAsset = assetsList.getSelectedValue( ).toString( );
                    approveSelection( );
                }
            }

        } );
        approveAssetButton.add( app );
        //assetsPanel.add( approveAssetButton, BorderLayout.SOUTH );
        assetsPanel.add( approveAssetButton, BorderLayout.SOUTH );
        assetsPanel.add( projectContentsPanel, BorderLayout.CENTER );

        JPanel centerPanel = new JPanel( );
        if( previewLocation == PREVIEW_LOCATION_SOUTH ) {
            container.setLayout( new BoxLayout( container, BoxLayout.LINE_AXIS ) );
            centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.PAGE_AXIS ) );
        }
        else if( previewLocation == PREVIEW_LOCATION_WEST ) {
            container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );
            centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.LINE_AXIS ) );
        }

        centerPanel.add( mainPanel );
        projectContentsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), "Assets in .ead file" ) );

        JPanel southPanel = new JPanel( );
        southPanel.setLayout( new BorderLayout( ) );
        this.createPreviewPanel( southPanel );
        centerPanel.add( southPanel );

        container.add( centerPanel );
        container.add( assetsPanel );

        this.addPropertyChangeListener( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new PropertyChangeListener( ) {

            public void propertyChange( PropertyChangeEvent evt ) {

                if( getSelectedFile( ) != null ) {
                    setSelectedAsset( null );
                }
                updatePreview( );
            }

        } );
        setSize( 600, 600 );
    }

    private void createAssetsList( ) {

        Dimension pref = ( (JPanel) filePanel.getComponent( 0 ) ).getPreferredSize( );
        Dimension min = ( (JPanel) filePanel.getComponent( 0 ) ).getMinimumSize( );

        assetsList = new JList( );
        assetsList.setLayoutOrientation( JList.VERTICAL_WRAP );
        String[] assets = AssetsController.getAssetFilenames( assetCategory, filter );
        if (this.isMultiSelectionEnabled( ))
            assetsList.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        else
            assetsList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        assetsList.setListData( assets );
        assetsList.addListSelectionListener( new ResourcesListListener( ) );
        //assetsList.setMinimumSize( new Dimension( filePanel.getMinimumSize( ).width + min.width, filePanel.getMinimumSize( ).height + min.height ) );
        //assetsList.setPreferredSize( new Dimension( filePanel.getPreferredSize( ).width + pref.width + 1, filePanel.getPreferredSize( ).height + pref.height + 1 ) );
        this.projectContentsPanel = new JScrollPane( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
        projectContentsPanel.setViewportView( assetsList );

    }

    private void createDefaultAssetsList( ) {

        assetsList = new JList( );
        if( previewLocation == PREVIEW_LOCATION_WEST ) {
            assetsList.setLayoutOrientation( JList.VERTICAL );

            this.projectContentsPanel = new JScrollPane( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        }
        else if( previewLocation == PREVIEW_LOCATION_SOUTH ) {
            assetsList.setLayoutOrientation( JList.VERTICAL );
            this.projectContentsPanel = new JScrollPane( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        }
        String[] assets = AssetsController.getAssetFilenames( assetCategory );
        // Order assets by filename
        
        
        if (this.isMultiSelectionEnabled( ))
            assetsList.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        else
            assetsList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        //assetsList.setListData( assets );
        assetsList.setListData( orderAssetList(assets) );
        assetsList.addListSelectionListener( new ResourcesListListener( ) );
        projectContentsPanel.setViewportView( assetsList );

    }

    /**
     * Orders the given assets array alphabetically using the Quicksort algorithm
     * @param assets    The asset list to be ordered
     * @since   v1.5
     */
    private String[] orderAssetList ( String[] assets){
        if (assets == null || assets.length == 0){
            return assets;
        }
        
        List<String> orderedAssets = new ArrayList<String>();
        for (String s: assets){
            orderedAssets.add( s );
        }
        
        orderAssetList ( orderedAssets, 0, assets.length-1 );
        
        for (String s: orderedAssets){
            System.out.println( s);
        }
        
        return orderedAssets.toArray( new String[]{} );
    }
    
    /**
     * Orders the given assets list alphabetically from position i to j (both included) using the Quicksort algorithm
     * @param assets    The asset list to be ordered
     * @param i     First position in the sublist that will be ordered
     * @param j     Last position in the sublist that will be ordered
     * @since   v1.5
     */
    private void orderAssetList ( List<String> assets, int i, int j){
        if (assets==null || assets.size( )==0 || j-i+1<=1){
            return;
        }
        
        //Pivot
        int pivotIndx = i+(j-i+1)/2;
        String pivot = assets.get( pivotIndx );
        
        int movedToEnd=0;
        for (int indx=i; indx<=j-movedToEnd; indx++){
            int compare = compareStrings (assets.get( indx ), pivot);
            if (compare==1 && indx>pivotIndx){
                assets.add( i, assets.remove( indx ) );
                pivotIndx++;
            }
            else if (compare==2 && indx<pivotIndx){
                assets.add( j, assets.remove( indx ) );
                movedToEnd++;
                pivotIndx--;
                indx--;
            }
        }
        
        orderAssetList(assets, i, pivotIndx-1);
        orderAssetList(assets, pivotIndx+1, j);
    }
    
    /**
     * Checks if s1 precedes s2 in alphabetical order. The algorithm takes into account the length of the strings, 
     * considering that if s1 is a substring of s2, then s1 < s2.
     * @param s1
     * @param s2
     * @return  1 if s1 is lower than s2;
     *          2 if s2 is lower than s1:
     *          0 if s1 is equals to s2. This only happens if s1=s2 and they have the same length. if s2 contains s1, 1 is returned, 
     *              If s1 contains s2, 2 is returned.
     * @since v1.5. Used to order the asset list.
     * 
     */
    private int compareStrings ( String s1, String s2 ){
        char[] chars1 = s1.toCharArray( );
        char[] chars2 = s2.toCharArray( );
        for (int i=0; i<Math.min( chars1.length, chars2.length ); i++){
            if (chars1[i]<chars2[i]){
                return 1;
            } else if (chars1[i]>chars2[i]){
                return 2;
            }
        }
        
        if (chars1.length<chars2.length){
            return 1;
        } else if (chars1.length>chars2.length){
            return 2;
        } else {
            return 0;
        }
    }
    
    private void createCentralPanel( ) {

        //3)Create the central panel and add the filePanel to it
        centralPanel = new JPanel( );
        centralPanel.setLayout( new BoxLayout( centralPanel, BoxLayout.PAGE_AXIS ) );
        mainPanel.add( centralPanel, BorderLayout.CENTER );
        add( mainPanel, BorderLayout.CENTER );

        showingZipContents = false;

        createAssetsList( );
        //5) Create the buttonsPanel and add it to the centralPanel
        buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new BoxLayout( buttonsPanel, BoxLayout.LINE_AXIS ) );

        approveButton = new JButton( TC.get( "GeneralText.OK" ) );
        approveButton.addActionListener( new ButtonsListener( ) );
        cancelButton = new JButton( TC.get( "GeneralText.Cancel" ) );
        cancelButton.addActionListener( new ButtonsListener( ) );
        buttonsPanel.add( approveButton );
        buttonsPanel.add( Box.createHorizontalStrut( 1 ) );
        buttonsPanel.add( cancelButton );

        centralPanel.add( explorerPanel );
        if( this.previewLocation == PREVIEW_LOCATION_SOUTH ) {
            createPreviewPanel( centralPanel );
        }
        else if( this.previewLocation == PREVIEW_LOCATION_WEST ) {
            mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.LINE_AXIS ) );
            createPreviewPanel( mainPanel );
        }
        centralPanel.add( buttonsPanel );

    }

    //TO BE REDEFINED
    protected abstract void updatePreview( );

    protected abstract void createPreviewPanel( Container parent );

    protected FileFilter getFilter( ) {

        return AssetsController.getAssetsFileFilter( assetCategory, filter );
    }

    /**
     * Listener for the list, everytime the selection changes, the image is
     * displayed in the preview panel.
     */
    private class ResourcesListListener implements ListSelectionListener {

        public void valueChanged( ListSelectionEvent e ) {

            // If there is an asset selected, show it

            if (AssetChooser.this.isMultiSelectionEnabled( )) {
                AssetChooser.this.selectedAssets.clear( );
                for (Object asset : ( (JList) e.getSource( ) ).getSelectedValues( ))
                //for (Object asset : AssetChooser.this.assetsList.getSelectedValues( ))
                    
                    AssetChooser.this.selectedAssets.add( asset.toString( ) );
            }
            if( ( (JList) e.getSource( ) ).getSelectedIndex( ) >= 0 )
                setSelectedAsset( ( (JList) e.getSource( ) ).getSelectedValue( ).toString( ) );
            else
                setSelectedAsset( null );

            updatePreview( );
            //if( assetsList.getSelectedIndex( ) >= 0 )
            //animationPanel.loadAnimation( assetPaths[resourcesList.getSelectedIndex( )] );

            // Else, delete the preview image
            //else
            //animationPanel.removeAnimation( );
        }

    }

    /**
     * Listener for the list, everytime the selection changes, the image is
     * displayed in the preview panel.
     */
    private class DefaultResourcesListListener implements ListSelectionListener {

        public void valueChanged( ListSelectionEvent e ) {

            // If there is an asset selected, show it

            if( ( (JList) e.getSource( ) ).getSelectedIndex( ) >= 0 )
                setSelectedFile( null );
        }

    }

    public class ChangeDirectoryListener implements ActionListener, PropertyChangeListener {

        public void actionPerformed( ActionEvent e ) {

            setCurrentDirectory( new File( Controller.getInstance( ).getProjectFolder( ) ) );
            //+"/"+AssetsController.getCategoryFolder(type)
            explorerPanel = projectContentsPanel;
            showingZipContents = true;
            setSelectedFile( null );

            mainPanel.removeAll( );
            createCentralPanel( );
            mainPanel.updateUI( );

        }

        public void propertyChange( PropertyChangeEvent evt ) {

            if( evt.getPropertyName( ).equals( JFileChooser.DIRECTORY_CHANGED_PROPERTY ) ) {
                explorerPanel = filePanel;
                showingZipContents = false;
                setSelectedAsset( null );

                mainPanel.removeAll( );
                createCentralPanel( );
                mainPanel.updateUI( );

            }
            else if( evt.getPropertyName( ).equals( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY ) ) {
                updatePreview( );
            }

        }

    }

    public class ButtonsListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            if( e.getSource( ) == approveButton ) {
                if( showingZipContents ) {
                    if (!AssetChooser.this.isMultiSelectionEnabled( )) {
                        if( assetsList.getSelectedValue( ) == null ) {
                            selectedAsset = null;
                            selectedAssets.clear( );
                        }
                        else {
                            selectedAsset = assetsList.getSelectedValue( ).toString( );
                        }
                    } else {
                        selectedAssets.clear( );
                        for (Object asset : assetsList.getSelectedValues( )) {
                            selectedAssets.add( asset.toString( ) );
                        }
                    }
                }
                else if( getSelectedFile( ) != null && getSelectedFile( ).getParentFile( ) != null ) {
                    Controller.getInstance( ).setLastDirectory( getSelectedFile( ).getParent( ) );
                }

                approveSelection( );
            }
            else if( e.getSource( ) == cancelButton ) {
                selectedAsset = null;
                selectedAssets.clear( );
                cancelSelection( );
            }
        }

    }

    /**
     * @return the selectedAsset
     */
    public String getSelectedAsset( ) {

        return selectedAsset;
    }

    /**
     * @param selectedAsset
     *            the selectedAsset to set
     */
    public void setSelectedAsset( String selectedAsset ) {

        this.selectedAsset = selectedAsset;
    }
    
    public Object[] getSelectedAssets() {
        return selectedAssets.toArray( );
    }

    @Override
    protected JDialog createDialog( Component parent ) throws HeadlessException {

        JDialog dialog = super.createDialog( parent );
        dialog.setModalityType( ModalityType.TOOLKIT_MODAL );
        Controller.getInstance( ).pushWindow( dialog );
        if( type == DEFAULT_ASSET_CHOOSER ) {
            Container container = dialog.getContentPane( );
            this.customizeDefaultChooser( container );
            dialog.setSize( 800, 800 );
        }
        dialog.addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosed( WindowEvent e ) {

                Controller.getInstance( ).popWindow( );

            }

        } );
        return dialog;

    }
    
    /*public Object[] prepareAndGetSelectedAssets(){
        selectedAssets.clear( );
        for (Object asset : assetsList.getSelectedValues( ))
            selectedAssets.add( asset.toString( ) );
        
        return selectedAssets.toArray( );
   
    }*/

    public int showAssetChooser( Component parent ) {

        int value = showDialog( parent, title );
        if( value == JFileChooser.APPROVE_OPTION ) {
            if( selectedAsset == null && ((!this.isMultiSelectionEnabled( ) && getSelectedFile( ) != null) || (this.isMultiSelectionEnabled( ) && getSelectedFiles().length > 0)) ) {
                // Check that selectedFile is not exactly in project folder
                if( !this.isMultiSelectionEnabled( ) && getSelectedFile( ).getAbsolutePath( ).startsWith( AssetsController.getCategoryAbsoluteFolder( assetCategory ) ) ) {
                    selectedAsset = getSelectedFile( ).getName( );
                    return ASSET_FROM_ZIP;
                }
                else if( this.isMultiSelectionEnabled( ) && getSelectedFiles( )[0].getAbsolutePath( ).startsWith( AssetsController.getCategoryAbsoluteFolder( assetCategory ) ) ) {
                    selectedAsset = getSelectedFile( ).getName( );
                    return ASSET_FROM_ZIP;
                }
                else
                    return ASSET_FROM_OUTSIDE;
            }
            else {
                if( selectedAsset != null && getSelectedFile( ) == null ){
                    //prepareAndGetSelectedAssets();
                    return ASSET_FROM_ZIP;
                }
                else
                    return JFileChooser.CANCEL_OPTION;
            }
        }
        else {
            return value;
        }
    }

}
