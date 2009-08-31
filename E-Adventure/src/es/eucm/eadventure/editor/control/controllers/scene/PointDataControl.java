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
package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Point;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.areaedition.ChangePointValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class PointDataControl extends DataControl {

	/**
	 * Contained node.
	 */
	private Point point;

	/**
	 * Constructor.
	 * 
	 * @param activeArea
	 *            Exit of the data control structure
	 */
	public PointDataControl( Point point ) {
		this.point = point;
	}
	
	/**
	 * Returns the X coordinate of the upper left position of the exit.
	 * 
	 * @return X coordinate of the upper left point
	 */
	public int getX( ) {
		return (int) point.getX( );
	}

	/**
	 * Returns the Y coordinate of the upper left position of the exit.
	 * 
	 * @return Y coordinate of the upper left point
	 */
	public int getY( ) {
		return (int) point.getY( );
	}


	/**
	 * Sets the new values for the exit.
	 * 
	 * @param x
	 *            X coordinate of the upper left point
	 * @param y
	 *            Y coordinate of the upper left point
	 */
	public void setPoint( int x, int y ) {
		controller.addTool(new ChangePointValueTool(point, x, y));
	}

	@Override
	public Object getContent( ) {
		return point;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { };
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type, String id ) {
		boolean elementAdded = false;
		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		boolean elementDeleted = false;
		return elementDeleted;
	}
	
	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		return elementMoved;
	}

	@Override
	public String renameElement( String name) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// DO nothing
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Delete the references from the actions
		// Do nothing
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		//actionsListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		//actionsListDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}	

	@Override
	public void recursiveSearch() {
	}	
	
	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return null;
	}

}
