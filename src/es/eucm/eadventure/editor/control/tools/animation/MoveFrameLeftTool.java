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

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveFrameLeftTool extends Tool {

    private AnimationDataControl animationDataControl;

    private FrameDataControl frameDataControl;

    private Animation animation;

    private Frame frame;

    private int index;

    public MoveFrameLeftTool( AnimationDataControl animationDataControl, FrameDataControl frameDataControl ) {

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

        if( index == 0 )
            return false;
        animation.getFrames( ).remove( frame );
        animationDataControl.getFrameDataControls( ).remove( frameDataControl );

        animation.getFrames( ).add( index - 1, frame );
        animationDataControl.getFrameDataControls( ).add( index - 1, frameDataControl );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        animation.getFrames( ).remove( frame );
        animationDataControl.getFrameDataControls( ).remove( frameDataControl );

        animation.getFrames( ).add( index - 1, frame );
        animationDataControl.getFrameDataControls( ).add( index - 1, frameDataControl );
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
