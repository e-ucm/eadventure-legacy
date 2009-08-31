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
package es.eucm.eadventure.editor.control.tools.general.assets;


import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomArrow;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectArrowTool extends SelectResourceTool {

	protected AdventureData adventureData;
	
	protected String type;
		
	protected boolean removed;
	
	protected static AssetInformation[] createAssetInfoArray (String type){
		AssetInformation[] array = new AssetInformation[1];
		array[0] = new AssetInformation( "", type, true, AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_NONE );
		return array;
	}
	
	protected static Resources createResources( AdventureData adventureData, String type ){
		Resources resources = new Resources();
		boolean introduced = false;
		for (int i=0; i < adventureData.getArrows().size(); i++){
			CustomArrow customArrow = adventureData.getArrows( ).get( i );
			if (customArrow.getType( ).equals( type )){
				resources.addAsset(type, customArrow.getPath());
				introduced = true;break;
			}
		}
		
		if (!introduced){
			resources.addAsset(type, "NULL");
		}
		
		return resources;
	}
	
	public SelectArrowTool( AdventureData adventureData, String type )
			throws CloneNotSupportedException {
		super(createResources(adventureData, type), createAssetInfoArray( type ), Controller.RESOURCES, 0);
		this.adventureData = adventureData;
		this.type = type;
	}

	@Override
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getArrows().size(); i++){
				if (adventureData.getArrows().get(i).getType().equals(type)){
					if (removed)
						adventureData.getArrows().remove(i);
					else
						adventureData.getArrows().get(i).setPath(resources.getAssetPath(type));
					break;
					
				}
			}
			controller.updatePanel();
			return true;
		}
		
	}
	
	@Override
	public boolean redoTool(){
		if (removed)
			adventureData.addArrow(type, "");
		boolean done = super.redoTool();
		if (!done)
			return false;
		else {
			for (int i =0; i<adventureData.getArrows().size(); i++){
				if (adventureData.getArrows().get(i).getType().equals(type)){
					adventureData.getArrows().get(i).setPath(resources.getAssetPath(type));
				}
			}
			controller.updatePanel();
			return true;
		}		
	}
	
	@Override
	public boolean doTool(){
		if (resources.getAssetPath(type).equals("NULL")){
			removed = false;
		} else {
			for (int i =0; i<adventureData.getArrows().size(); i++){
				CustomArrow arrow =adventureData.getArrows().get(i); 
				if ( arrow.getType().equals(type)){
					adventureData.getArrows().remove(arrow);
					break;
				}
			}
			removed = true;
		}
		boolean done = super.doTool();
		if (!done)
			return false;
		else {
			setArrow(type, resources.getAssetPath(type));
			return true;
		}		
	}


	public void setArrow(String type, String path) {
		CustomArrow arrow = new CustomArrow(type, path);
		CustomArrow temp = null;
		for (CustomArrow cb : adventureData.getArrows()) {
			if (cb.equals(arrow))
				temp = cb;
		}
		if (temp != null)
			adventureData.getArrows().remove(temp);
		adventureData.addArrow(arrow);
	}
}
