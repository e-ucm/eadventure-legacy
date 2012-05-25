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

package es.eucm.eadventure.common.auxiliar.runsettings;



/**
 * This class stores user-defined options related to game execution in debug mode:
 *      PaintGrid: Enables/Disables the grid, which is useful to improve elements' location on screen
 *      
 *      PaintHotspots: Enables/disables rendering of the "hot spots", which are the points used by the
 *      keyboard navigation algorithm to navigate throughout interactive elements. Hot spots usually 
 *      correspond to the center of the interactive element, although it may vary to avoid overlapping.
 *      
 *      PaintBoundingAreas: Enables/disables rendering the bounding rectangles of the interactive elements.
 *      
 * @author Javier Torrente
 */

public class DebugSettings {

        protected boolean paintGrid;
        
        protected boolean paintHotSpots;
        
        protected boolean paintBoundingAreas;
        
        /**
         * Default constructor. Sets all parameters to false.
         */
        public DebugSettings( ){
            this(false,false,false);
        }
        
        /**
         * Constructor for "debug" mode. Sets debugMode automatically to true.
         */
        public DebugSettings( boolean paintGrid, boolean paintHotSpots, boolean paintBoundingAreas) {

            super( );
            this.paintGrid = paintGrid;
            this.paintHotSpots = paintHotSpots;
            this.paintBoundingAreas = paintBoundingAreas;
        }

        
        public boolean isPaintGrid( ) {
        
            return paintGrid;
        }

        
        public void setPaintGrid( boolean paintGrid ) {
        
            this.paintGrid = paintGrid;
        }

        
        public boolean isPaintHotSpots( ) {
        
            return paintHotSpots;
        }

        
        public void setPaintHotSpots( boolean paintHotSpots ) {
        
            this.paintHotSpots = paintHotSpots;
        }

        
        public boolean isPaintBoundingAreas( ) {
        
            return paintBoundingAreas;
        }

        
        public void setPaintBoundingAreas( boolean paintBoundingAreas ) {
        
            this.paintBoundingAreas = paintBoundingAreas;
        }

}
