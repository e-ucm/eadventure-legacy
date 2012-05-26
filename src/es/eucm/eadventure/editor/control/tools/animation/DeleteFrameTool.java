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
package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.TransitionDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteFrameTool extends Tool {

    private FrameDataControl frameDataControl;

    private AnimationDataControl animationDataControl;

    private Transition transition;

    private TransitionDataControl transitionDataControl;

    private Frame frame;

    private Animation animation;

    private int index;

    public DeleteFrameTool( AnimationDataControl animationDataControl, FrameDataControl frameDataControl ) {

        this.frameDataControl = frameDataControl;
        this.animationDataControl = animationDataControl;
        this.animation = (Animation) animationDataControl.getContent( );
        this.frame = (Frame) frameDataControl.getContent( );
        index = animation.getFrames( ).indexOf( frameDataControl.getContent( ) );
        this.transitionDataControl = this.animationDataControl.getTransitionDataControls( ).get( index + 1 );
        this.transition = (Transition) this.transitionDataControl.getContent( );
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

        if( animationDataControl.getFrameCount( ) == 1 )
            return false;
        animationDataControl.getFrameDataControls( ).remove( frameDataControl );
        animationDataControl.getTransitionDataControls( ).remove( transitionDataControl );
        animation.getFrames( ).remove( frame );
        animation.getTransitions( ).remove( transition );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        animationDataControl.getFrameDataControls( ).remove( frameDataControl );
        animationDataControl.getTransitionDataControls( ).remove( transitionDataControl );
        animation.getFrames( ).remove( frame );
        animation.getTransitions( ).remove( transition );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        animationDataControl.getFrameDataControls( ).add( index, frameDataControl );
        animationDataControl.getTransitionDataControls( ).add( index + 1, transitionDataControl );
        animation.getFrames( ).add( index, frame );
        animation.getTransitions( ).add( index + 1, transition );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
