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
package es.eucm.eadventure.editor.control.config;

import es.eucm.eadventure.editor.control.Controller;

public class SceneLinksConfigData implements ProjectConfigDataConsumer {

    private static Controller controller = Controller.getInstance( );

    private static String getXKey( String sceneId ) {

        int chapter = controller.getSelectedChapter( );
        return "Chapter" + chapter + "." + sceneId + ".X";
    }

    private static String getYKey( String sceneId ) {

        int chapter = controller.getSelectedChapter( );
        return "Chapter" + chapter + "." + sceneId + ".Y";
    }

    private static String getVisibleKey( String sceneId ) {

        int chapter = controller.getSelectedChapter( );
        return "Chapter" + chapter + "." + sceneId + ".Visible";
    }

    public static boolean isSceneConfig( String sceneId ) {

        String keyX = getXKey( sceneId );
        String keyY = getYKey( sceneId );
        String keyVisible = getVisibleKey( sceneId );
        // If both X and Y are in the config file 
        if( ProjectConfigData.existsKey( keyX ) && ProjectConfigData.existsKey( keyY ) && ProjectConfigData.existsKey( keyVisible ) ) {
            return true;
        }
        return false;
    }

    public static int getSceneX( String sceneId ) {

        int X = Integer.MIN_VALUE;

        if( isSceneConfig( sceneId ) ) {
            try {
                String keyX = getXKey( sceneId );
                X = Integer.parseInt( ProjectConfigData.getProperty( keyX ) );
            }
            catch( Exception e ) {
                X = Integer.MIN_VALUE;
            }
        }
        return X;
    }

    public static int getSceneY( String sceneId ) {

        int Y = Integer.MIN_VALUE;

        if( isSceneConfig( sceneId ) ) {
            try {
                String keyY = getYKey( sceneId );
                Y = Integer.parseInt( ProjectConfigData.getProperty( keyY ) );
            }
            catch( Exception e ) {
                Y = Integer.MIN_VALUE;
            }
        }
        return Y;
    }

    public static boolean getSceneVisible( String sceneId ) {

        boolean visible = true;

        if( isSceneConfig( sceneId ) ) {
            try {
                String keyVisible = getVisibleKey( sceneId );
                visible = !ProjectConfigData.getProperty( keyVisible ).equals( "false" );
            }
            catch( Exception e ) {
            }
        }
        return visible;
    }

    public static void setSceneX( String sceneId, int X ) {

        if( X != Integer.MIN_VALUE ) {
            String keyX = getXKey( sceneId );
            String xValue = Integer.toString( X );
            ProjectConfigData.setProperty( keyX, xValue );
        }
    }

    public static void setSceneY( String sceneId, int Y ) {

        if( Y != Integer.MIN_VALUE ) {
            String keyY = getYKey( sceneId );
            String yValue = Integer.toString( Y );
            ProjectConfigData.setProperty( keyY, yValue );
        }
    }

    public static void setSceneVisible( String sceneId, boolean visible ) {

        String keyVisible = getVisibleKey( sceneId );
        String visibleValue = ( visible ? "true" : "false" );
        ProjectConfigData.setProperty( keyVisible, visibleValue );
    }

    public void updateData( ) {

    }

}
