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
package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for setting an eval function
 * @author Javier Torrente
 *
 */
public class SetEvalFunctionTool extends Tool{

	private Conditions conditions;
	private int index1;
	private int index2;
	
	private int value;
	
	private boolean merged;
	private List<Condition> la1;
	private List<Condition> la2;
	private List<Condition> lb;
	
	public SetEvalFunctionTool (Conditions conditions, int index1, int index2, int value){
		this.conditions = conditions;
		this.index1 = index1;
		this.index2 = index2;
		this.value = value;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		// Get upper and lower block
		List<Condition> upper = conditions.get(index1);
		List<Condition> lower = conditions.get(index1+1);
		
		// Check if the algorithm must search deeper (index2==-1 means no search inside blocks must be carried out)
		if (index2>=0){

			// Check index2
			if (upper.size()==1 || index2 >=upper.size()-1 || value!= ConditionsController.EVAL_FUNCTION_AND)
				return false;
			
			// Either block must be split
			List<List<Condition>> newBlocks = splitBlock(upper, index2);
			List<Condition> firstBlock = newBlocks.get(0);
			List<Condition> secondBlock = newBlocks.get(1);
			
			merged = false;
			lb=conditions.delete(index1);
			la1 = firstBlock;
			la2 = secondBlock;
			if (firstBlock.size() == 1)
				conditions.add(index1, firstBlock.get(0));
			else
				conditions.add(index1, firstBlock );
			
			if (secondBlock.size() == 1)
				conditions.add(index1+1, secondBlock.get(0));
			else
				conditions.add(index1+1, secondBlock );
		
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();		
			return true;
			
		} 
		// No deep search is needed. The "And" function must be changed to "OR"
		else {
			if (value!=ConditionsController.EVAL_FUNCTION_OR)
				return false;

			merged = true;
			// Merge both wrappers
			Conditions newBlock = mergeBlocks(upper, lower);
			lb= newBlock.getSimpleConditions();
			// Insert in "upper" position
			la1=conditions.delete(index1);
			la2=conditions.delete(index1);
			conditions.add(index1, newBlock);
			
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}	
	}

	@Override
	public boolean redoTool() {
		if (merged){
			conditions.delete(index1);
			conditions.delete(index1);
			conditions.add(index1, lb);
			
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		} else {
			conditions.delete(index1);
			conditions.add(index1, la1);
			conditions.add(index1+1, la2);
			
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}
	}

	@Override
	public boolean undoTool() {
		if (merged){
			conditions.delete(index1);
			conditions.add(index1, la1);
			conditions.add(index1+1, la2);
			
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		} else {
			conditions.delete(index1);
			conditions.delete(index1);
			conditions.add(index1, lb);
			
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		}
	}
	
	
	/**
	 * Local function. Used only in setEvalFunction. It splits a block in two blocks (Required when an OR eval function is
	 * set to AND)
	 * @param conditions
	 * @param index
	 * @return
	 */
	private List<List<Condition>> splitBlock ( List<Condition> conditions, int index ){
		List<List<Condition>> result = new ArrayList<List<Condition>>();
	
		if (index<0 || index>=conditions.size()-1)
			return null;
		
		List<Condition> block1 = new ArrayList<Condition>();
		List<Condition> block2 = new ArrayList<Condition>();
		
		for (int i=0; i<=index; i++){
			block1.add(conditions.get(i));
		}
		for (int i=index+1; i<conditions.size(); i++){
			block2.add(conditions.get(i));
		}

		result.add(block1);
		result.add(block2);
		
		return result;
	}
	
	/**
	 * Local function. Used only in setEvalFunction. It merges two blocks, returning a single one (Required when an AND eval function is
	 * set to OR)
	 * @param conditions
	 * @param index
	 * @return
	 */
	private Conditions mergeBlocks ( List<Condition> wrapper1, List<Condition> wrapper2 ){
		Conditions newBlock = new Conditions();
		transferConditions(newBlock, wrapper1);
		transferConditions(newBlock, wrapper2);
		return newBlock;
	}
	
	// Auxiliar method for mergeBlocks
	private void transferConditions ( Conditions container, List<Condition> wrapper1){
		for (Condition condition: wrapper1 ){
			container.add(condition);
		}
	}
}
