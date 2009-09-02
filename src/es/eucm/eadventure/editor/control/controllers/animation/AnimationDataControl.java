/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.controllers.animation;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.ImageLoaderFactory;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.animation.AddNewFrameTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeSlidesTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeUseTransitionsTool;
import es.eucm.eadventure.editor.control.tools.animation.DeleteFrameTool;
import es.eucm.eadventure.editor.control.tools.animation.MoveFrameLeftTool;
import es.eucm.eadventure.editor.control.tools.animation.MoveFrameRightTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeIdTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

public class AnimationDataControl extends DataControl {

    private Animation animation;

    private String filename;

    private List<FrameDataControl> frameDataControls;

    private List<TransitionDataControl> transitionDataControls;

    public String getFilename( ) {

        return filename;
    }

    public void setFilename( String filename ) {

        this.filename = filename;
    }

    public AnimationDataControl( Animation animation ) {

        this.animation = animation;
        createDataControls( );
    }

    public void setAnimation( Animation animation ) {

        this.animation = animation;
        createDataControls( );
    }

    public String getDocumentation( ) {

        return animation.getDocumentation( );
    }

    public void setDocumentation( String documentation ) {

        Controller.getInstance( ).addTool( new ChangeDocumentationTool( animation, documentation ) );
    }

    public String getId( ) {

        return animation.getId( );
    }

    public void setId( String id ) {

        Controller.getInstance( ).addTool( new ChangeIdTool( animation, id ) );
    }

    public boolean isUseTransitions( ) {

        return animation.isUseTransitions( );
    }

    public boolean isSlides( ) {

        return animation.isSlides( );
    }

    public void setUseTransitions( boolean useTransitions ) {

        Controller.getInstance( ).addTool( new ChangeUseTransitionsTool( animation, useTransitions ) );
    }

    public void setSlides( boolean slides ) {

        Controller.getInstance( ).addTool( new ChangeSlidesTool( animation, slides ) );
    }

    public FrameDataControl getFrameDataControl( int i ) {

        if( i >= 0 && i < frameDataControls.size( ) )
            return frameDataControls.get( i );
        return null;
    }

    public TransitionDataControl getTransitionDataControl( int i ) {

        if( i >= 0 && i < transitionDataControls.size( ) )
            return transitionDataControls.get( i );
        return null;
    }

    public List<TransitionDataControl> getTransitionDataControls( ) {

        return transitionDataControls;
    }

    public List<FrameDataControl> getFrameDataControls( ) {

        return frameDataControls;
    }

    public int indexOfFrame( Frame newFrame ) {

        return animation.getFrames( ).indexOf( newFrame );
    }

    public int getFrameCount( ) {

        return frameDataControls.size( );
    }

    public void addFrame( int index, Frame newFrame ) {

        Controller.getInstance( ).addTool( new AddNewFrameTool( this, index, newFrame ) );
    }

    public void moveFrameLeft( FrameDataControl temp ) {

        Controller.getInstance( ).addTool( new MoveFrameLeftTool( this, temp ) );
    }

    public void moveFrameRight( FrameDataControl temp2 ) {

        Controller.getInstance( ).addTool( new MoveFrameRightTool( this, temp2 ) );
    }

    public void deleteFrame( FrameDataControl selectedValue ) {

        Controller.getInstance( ).addTool( new DeleteFrameTool( this, selectedValue ) );
    }

    private void createDataControls( ) {

        frameDataControls = new ArrayList<FrameDataControl>( );
        transitionDataControls = new ArrayList<TransitionDataControl>( );
        for( Frame frame : animation.getFrames( ) )
            frameDataControls.add( new FrameDataControl( frame ) );
        for( Transition transition : animation.getTransitions( ) )
            transitionDataControls.add( new TransitionDataControl( transition ) );
    }

    public String getImagePath( Component window ) {

        String selectedAsset = null;

        AssetChooser chooser = AssetsController.getAssetChooser( AssetsConstants.CATEGORY_IMAGE, AssetsController.FILTER_NONE );
        int option = chooser.showAssetChooser( window );
        //In case the asset was selected from the zip file
        if( option == AssetChooser.ASSET_FROM_ZIP ) {
            selectedAsset = chooser.getSelectedAsset( );
            selectedAsset = AssetsController.getCategoryFolder( AssetsConstants.CATEGORY_IMAGE ) + "/" + selectedAsset;
        }

        //In case the asset was not in the zip file: first add it
        else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
            boolean added = AssetsController.addSingleAsset( AssetsConstants.CATEGORY_ANIMATION_IMAGE, chooser.getSelectedFile( ).getAbsolutePath( ) );

            if( added ) {
                selectedAsset = chooser.getSelectedFile( ).getName( );
                selectedAsset = AssetsController.getCategoryFolder( AssetsConstants.CATEGORY_ANIMATION_IMAGE ) + "/" + selectedAsset;
            }
        }

        return selectedAsset;
    }

    public String getSoundPath( Component window ) {

        String selectedAsset = null;

        AssetChooser chooser = AssetsController.getAssetChooser( AssetsConstants.CATEGORY_AUDIO, AssetsController.FILTER_NONE );
        int option = chooser.showAssetChooser( window );
        //In case the asset was selected from the zip file
        if( option == AssetChooser.ASSET_FROM_ZIP ) {
            selectedAsset = chooser.getSelectedAsset( );
            selectedAsset = AssetsController.getCategoryFolder( AssetsConstants.CATEGORY_AUDIO ) + "/" + selectedAsset;
        }

        //In case the asset was not in the zip file: first add it
        else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
            boolean added = AssetsController.addSingleAsset( AssetsConstants.CATEGORY_ANIMATION_AUDIO, chooser.getSelectedFile( ).getAbsolutePath( ) );

            if( added ) {
                selectedAsset = chooser.getSelectedFile( ).getName( );
                selectedAsset = AssetsController.getCategoryFolder( AssetsConstants.CATEGORY_ANIMATION_AUDIO ) + "/" + selectedAsset;
            }
        }

        return selectedAsset;
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return false;
    }

    @Override
    public boolean canBeDuplicated( ) {

        return false;
    }

    @Override
    public boolean canBeMoved( ) {

        return false;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        return 0;
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return 0;
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

    }

    @Override
    public int[] getAddableElements( ) {

        return null;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

    }

    @Override
    public Object getContent( ) {

        return animation;
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return false;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public void recursiveSearch( ) {

    }

    @Override
    public String renameElement( String newName ) {

        return null;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, frameDataControls );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, transitionDataControls );
        return path;
    }
    
    public ImageLoaderFactory getImageLoaderFactory() {
        return animation.getImageLoaderFactory();
    }
}
