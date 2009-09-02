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
package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.TransitionDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

public class AddNewFrameTool extends Tool {

    private AnimationDataControl animationDataControl;

    private int index;

    private Frame newFrame;

    private FrameDataControl newFrameDataControl;

    private Animation animation;

    private Transition newTransition;

    private TransitionDataControl newTransitionDataControl;

    public AddNewFrameTool( AnimationDataControl animationDataControl, int index, Frame newFrame ) {

        this.animationDataControl = animationDataControl;
        this.index = index;
        this.newFrame = newFrame;
        if( newFrame == null )
            newFrame = new Frame( animationDataControl.getImageLoaderFactory( ) );
        this.animation = (Animation) animationDataControl.getContent( );
        this.newFrameDataControl = new FrameDataControl( newFrame );
        this.newTransition = new Transition( );
        this.newTransitionDataControl = new TransitionDataControl( newTransition );
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        String selectedAsset = null;
        AssetChooser chooser = AssetsController.getAssetChooser( AssetsConstants.CATEGORY_IMAGE, AssetsController.FILTER_NONE );
        int option = chooser.showAssetChooser( Controller.getInstance( ).peekWindow( ) );
        if( option == AssetChooser.ASSET_FROM_ZIP ) {
            selectedAsset = chooser.getSelectedAsset( );
            selectedAsset = AssetsController.getCategoryFolder( AssetsConstants.CATEGORY_IMAGE ) + "/" + selectedAsset;
        }
        else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
            boolean added = AssetsController.addSingleAsset( AssetsConstants.CATEGORY_ANIMATION_IMAGE, chooser.getSelectedFile( ).getAbsolutePath( ) );
            if( added ) {
                selectedAsset = chooser.getSelectedFile( ).getName( );
                selectedAsset = AssetsController.getCategoryFolder( AssetsConstants.CATEGORY_ANIMATION_IMAGE ) + "/" + selectedAsset;
            }
        }

        newFrame.setUri( ( selectedAsset == null ? "" : selectedAsset ) );

        if( index >= animationDataControl.getFrameCount( ) || index < 0 )
            index = animationDataControl.getFrameCount( ) - 1;
        animation.getFrames( ).add( index + 1, newFrame );
        animation.getTransitions( ).add( index + 2, newTransition );
        animationDataControl.getFrameDataControls( ).add( index + 1, newFrameDataControl );
        animationDataControl.getTransitionDataControls( ).add( index + 2, newTransitionDataControl );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        animation.getFrames( ).add( index + 1, newFrame );
        animation.getTransitions( ).add( index + 2, newTransition );
        animationDataControl.getFrameDataControls( ).add( index + 1, newFrameDataControl );
        animationDataControl.getTransitionDataControls( ).add( index + 2, newTransitionDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        animation.getFrames( ).remove( newFrame );
        animation.getTransitions( ).remove( newTransition );
        animationDataControl.getFrameDataControls( ).remove( newFrameDataControl );
        animationDataControl.getTransitionDataControls( ).remove( newTransitionDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
