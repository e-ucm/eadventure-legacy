/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.config;

import es.eucm.eadventure.editor.control.Controller;

public class SceneLinksConfigData implements ProjectConfigDataConsumer{

	private static Controller controller = Controller.getInstance();
	
	private static String getXKey ( String sceneId ){
		int chapter = controller.getSelectedChapter( );
		return "Chapter"+chapter+"."+sceneId+".X";
	}
	
	private static String getYKey ( String sceneId ){
		int chapter = controller.getSelectedChapter( );
		return "Chapter"+chapter+"."+sceneId+".Y";
	}
	
	private static String getVisibleKey ( String sceneId ) {
		int chapter = controller.getSelectedChapter();
		return "Chapter"+chapter+"."+sceneId+".Visible";
	}
	
	public static boolean isSceneConfig ( String sceneId ){
		String keyX = getXKey ( sceneId );
		String keyY = getYKey ( sceneId );
		String keyVisible = getVisibleKey ( sceneId );
		// If both X and Y are in the config file 
		if (ProjectConfigData.existsKey( keyX ) && ProjectConfigData.existsKey( keyY ) && ProjectConfigData.existsKey(keyVisible)){
			return true;
		}
		return false;
	}

	public static int getSceneX ( String sceneId ){
		int X=Integer.MIN_VALUE;
		
		if (isSceneConfig (sceneId)){
			try {
				String keyX = getXKey ( sceneId );
				X = Integer.parseInt( ProjectConfigData.getProperty( keyX ) );
			}catch (Exception e){
				X = Integer.MIN_VALUE;
			}
		}
		return X;
	}

	
	public static int getSceneY ( String sceneId ){
		int Y=Integer.MIN_VALUE;
		
		if (isSceneConfig (sceneId)){
			try {
				String keyY = getYKey ( sceneId );
				Y = Integer.parseInt( ProjectConfigData.getProperty( keyY ) );
			}catch (Exception e){
				Y= Integer.MIN_VALUE;
			}
		}
		return Y;
	}
	
	public static boolean getSceneVisible ( String sceneId ) {
		boolean visible = true;
		
		if (isSceneConfig( sceneId)) {
			try {
				String keyVisible = getVisibleKey( sceneId );
				visible = !ProjectConfigData.getProperty(keyVisible).equals("false");
			} catch (Exception e) {
			}
		}
		return visible;
	}
	
	public static void setSceneX ( String sceneId, int X){
		if ( X!=Integer.MIN_VALUE ){
			String keyX = getXKey ( sceneId );
			String xValue = Integer.toString( X );
			ProjectConfigData.setProperty ( keyX, xValue);
		}
	}
	
	public static void setSceneY ( String sceneId , int Y){
		if ( Y!=Integer.MIN_VALUE ){ 
			String keyY = getYKey ( sceneId );
			String yValue = Integer.toString( Y );
			ProjectConfigData.setProperty ( keyY, yValue);
		}
	}
		
	public static void setSceneVisible (String sceneId, boolean visible) {
		String keyVisible = getVisibleKey (sceneId);
		String visibleValue = (visible ? "true" : "false");
		ProjectConfigData.setProperty( keyVisible, visibleValue);
	}
	
	public void updateData( ) {	}

}
