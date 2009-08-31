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
package es.eucm.eadventure.editor.control.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.gui.TextConstants;

public abstract class Searchable {
    
    protected static HashMap<Searchable, List<String>> resultSet = new HashMap<Searchable, List<String>>();
	
	protected static String searchedText;

	private static boolean caseSensitive;

	private static boolean fullMatch;
	
	
	public HashMap<Searchable, List<String>> search(String text, boolean caseSensitive, boolean fullMatch) {
		resultSet.clear();
		if (caseSensitive)
			DataControl.searchedText = text;
		else
			DataControl.searchedText = text.toLowerCase();
		Searchable.caseSensitive = caseSensitive;
		Searchable.fullMatch = fullMatch;
		this.recursiveSearch();
		return Searchable.resultSet;
	}
	
	public abstract void recursiveSearch ();
	
	
	protected void addResult (String where) {
		List<String> places = resultSet.get(this);
		if (places == null) {
			places = new ArrayList<String>();
			resultSet.put(this, places);
		}
		if (!places.contains(where))
			places.add(where);
	}
	
	protected void check(String value, String desc) {
		if (value != null) {
			if (!fullMatch) {
				if (!caseSensitive && value.toLowerCase().contains(searchedText))
					addResult(desc);
				else if (caseSensitive && value.contains(searchedText))
					addResult(desc);
			} else {
				if (!caseSensitive && value.toLowerCase().equals(searchedText))
					addResult(desc);
				else if (caseSensitive && value.equals(searchedText));
			}
		}
	}
	
	protected void check(ConditionsController conditions, String desc) {
		
		for (int i = 0; i < conditions.getBlocksCount(); i++) {
			for (int j = 0; j < conditions.getConditionCount(i); j++) {
				HashMap<String,String> properties = conditions.getCondition(i, j);
				if (properties.containsKey(ConditionsController.CONDITION_ID))
					check(properties.get(ConditionsController.CONDITION_ID), desc + " (ID)");
				if (properties.containsKey(ConditionsController.CONDITION_STATE))
					check(properties.get(ConditionsController.CONDITION_STATE), desc + " (" + TextConstants.getText("Search.State") + ")");
				if (properties.containsKey(ConditionsController.CONDITION_VALUE))
					check(properties.get(ConditionsController.CONDITION_VALUE), desc + " (" + TextConstants.getText("Search.Value") + ")");
			}
		}
	}
	
	protected abstract List<Searchable> getPath(Searchable dataControl);
	
	protected List<Searchable> getPathFromChild(Searchable dataControl, DataControl child) {
		if (child != null) {
			List<Searchable> path = child.getPath(dataControl);
			if (path != null) {
				path.add(this);
				return path;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Searchable> getPathFromChild(Searchable dataControl, List list) {
		for (Object temp : list) {
			List<Searchable> path = ((Searchable) temp).getPath(dataControl);
			if (path != null) {
				path.add(this);
				return path;
			}
		}
		return null;
	}
	
	protected List<Searchable> getPathFromSearchableChild(Searchable dataControl, Searchable child){
	    if (child != null) {
		List<Searchable> path = child.getPath(dataControl);
		if (path != null) {
			path.add(this);
			return path;
		}
	}
	return null;
	}


}

