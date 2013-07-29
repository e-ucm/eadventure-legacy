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

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.TransitionDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DuplicateFrameTool extends Tool {

    private AnimationDataControl animationDataControl;

    private int index;

    private Frame newFrame;

    private FrameDataControl newFrameDataControl;

    private Animation animation;

    private Transition newTransition;

    private TransitionDataControl newTransitionDataControl;

    private List<Frame> newFrameList;
    
    private List<FrameDataControl> newFrameDataControlList;
    
    private List<Transition> newTransitionList;
    
    private List<TransitionDataControl> newTransitionDataControlList;
    
    public DuplicateFrameTool( AnimationDataControl animationDataControl, int index, Frame newFrame ) {

        this.animationDataControl = animationDataControl;
        this.index = index;
        this.newFrame = newFrame;
        if( newFrame == null )
            newFrame = new Frame( animationDataControl.getImageLoaderFactory( ) );
        this.animation = (Animation) animationDataControl.getContent( );
        this.newFrameDataControl = new FrameDataControl( newFrame );
        this.newTransition = new Transition( );
        this.newTransitionDataControl = new TransitionDataControl( newTransition );
        
        this.newFrameList = new ArrayList<Frame>();
        this.newTransitionList = new ArrayList<Transition>();
        this.newFrameDataControlList = new ArrayList<FrameDataControl>();
        this.newTransitionDataControlList = new ArrayList<TransitionDataControl>();
        
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

        FrameDataControl newFrameDataControl = new FrameDataControl( newFrame );
        Transition newTransition = new Transition( );
        TransitionDataControl newTransitionDataControl = new TransitionDataControl( newTransition );

        if( index >= animationDataControl.getFrameCount( ) || index < 0 )
            index = animationDataControl.getFrameCount( ) - 1;
        animation.getFrames( ).add( index + 1, newFrame );
        animation.getTransitions( ).add( index + 2, newTransition );
        animationDataControl.getFrameDataControls( ).add( index + 1, newFrameDataControl );
        animationDataControl.getTransitionDataControls( ).add( index + 2, newTransitionDataControl );

        newFrameList.add(newFrame);
        newFrameDataControlList.add(newFrameDataControl);
        newTransitionList.add(newTransition);
        newTransitionDataControlList.add(newTransitionDataControl);

        return true;
    }

    @Override
    public boolean redoTool( ) {
        return doTool();
        
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
