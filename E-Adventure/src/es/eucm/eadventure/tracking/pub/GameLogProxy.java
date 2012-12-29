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

package es.eucm.eadventure.tracking.pub;



public class GameLogProxy implements _TrackingController{

    private _TrackingController glController;
    private GameLogConfig glConfig;
    
    public GameLogProxy ( String trackingConfigFile ){
        // Get config
        glConfig = new GameLogConfig( trackingConfigFile );
        // Instantiate controller
        if (glConfig.getControllerClass( )!=null && 
                !glConfig.getControllerClass( ).equals( "" ) &&
                !glConfig.getControllerClass( ).equals( "UNKNOWN" )){
            try {
                glController = (_TrackingController) Class.forName( glConfig.getControllerClass( ) ).getConstructor( GameLogConfig.class ).newInstance( glConfig );
            }
            catch( Exception e ) {
                System.out.println( "[GAMELOG] Controller class name undefined or not fully qualified. GameLog will be disabled");
                glController = null;
            }
        } else
            glController = null;
    }
    
    public void start(){
        if (glController!=null)
            glController.start( );
    }
    
    public void terminate(){
        if (glController!=null)
            glController.terminate( );        
    }
    
    public _GameLog getGameLog( ) {
        if (glController!=null)
            return glController.getGameLog( );
        else
            return EmptyGameLog.get( ); 
    }
}
