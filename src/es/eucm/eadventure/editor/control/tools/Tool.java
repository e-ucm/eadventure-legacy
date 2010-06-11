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
package es.eucm.eadventure.editor.control.tools;

import es.eucm.eadventure.common.gui.TC;

public abstract class Tool {

    /**
     * Stores the time when the tool was created
     */
    protected long timeStamp = System.currentTimeMillis( );

    protected boolean doesClone = false;

    public boolean doesClone( ) {

        return doesClone;
    }

    public void doesClone( boolean doesClone ) {

        this.doesClone = doesClone;
    }

    /**
     * Get the time when the tool was created
     * 
     * @return The time when the tool was created
     */
    public long getTimeStamp( ) {

        return timeStamp;
    }

    /**
     * Returns the tool name
     * 
     * @return the tool name
     */
    public String getToolName( ) {

        return TC.getToolName( getClass( ) );
    }

    /**
     * Do the actual work. Returns true if it could be done, false in other
     * case.
     * 
     * @return True if the tool was applied correctly
     */
    public abstract boolean doTool( );

    /**
     * Returns true if the tool can be undone
     * 
     * @return True if the tool can be undone
     */
    public abstract boolean canUndo( );

    /**
     * Undo the work done by the tool. Returns true if it could be undone, false
     * in other case.
     * 
     * @return True if the tool was undone correctly
     */
    public abstract boolean undoTool( );

    /**
     * Returns true if the tool can be redone
     * 
     * @return True if the tool can be redone
     */
    public abstract boolean canRedo( );

    /**
     * Re-do the work done by the tool before it was undone.
     * 
     * @return True if the tool was re-done correctly
     */
    public abstract boolean redoTool( );

    /**
     * Combines this tool with other similar tool (if possible). Useful for
     * combining simple changes as characters typed in the same field
     * 
     * @param other
     * @return
     */
    public abstract boolean combine( Tool other );

}
