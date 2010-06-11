/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveFrameRightTool extends Tool {

    private AnimationDataControl animationDataControl;

    private FrameDataControl frameDataControl;

    private Animation animation;

    private Frame frame;

    private int index;

    public MoveFrameRightTool( AnimationDataControl animationDataControl, FrameDataControl frameDataControl ) {

        this.animationDataControl = animationDataControl;
        this.frameDataControl = frameDataControl;
        this.animation = (Animation) animationDataControl.getContent( );
        this.frame = (Frame) frameDataControl.getContent( );
        this.index = animationDataControl.getFrameDataControls( ).indexOf( frameDataControl );

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

        if( index == animationDataControl.getFrameCount( ) - 1 )
            return false;
        animation.getFrames( ).remove( frame );
        animationDataControl.getFrameDataControls( ).remove( frameDataControl );

        animation.getFrames( ).add( index + 1, frame );
        animationDataControl.getFrameDataControls( ).add( index + 1, frameDataControl );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        animation.getFrames( ).remove( frame );
        animationDataControl.getFrameDataControls( ).remove( frameDataControl );

        animation.getFrames( ).add( index + 1, frame );
        animationDataControl.getFrameDataControls( ).add( index + 1, frameDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        animation.getFrames( ).remove( frame );
        animationDataControl.getFrameDataControls( ).remove( frameDataControl );

        animation.getFrames( ).add( index, frame );
        animationDataControl.getFrameDataControls( ).add( index, frameDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
