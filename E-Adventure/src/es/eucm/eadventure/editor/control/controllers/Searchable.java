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
	
	

}

