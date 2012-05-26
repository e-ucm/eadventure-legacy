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
package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.util.HashMap;

import es.eucm.eadventure.editor.control.controllers.ConditionsController;

public interface ConditionsPanelController {

    public static final String CONDITION_TYPE_FLAG = ConditionsController.CONDITION_TYPE_FLAG;

    public static final String CONDITION_TYPE_VAR = ConditionsController.CONDITION_TYPE_VAR;

    public static final String CONDITION_TYPE_GS = ConditionsController.CONDITION_TYPE_GS;

    public static final String CONDITION_ID = ConditionsController.CONDITION_ID;

    public static final String CONDITION_VALUE = ConditionsController.CONDITION_VALUE;

    //active|inactive|<,<=,>,>=,=
    public static final String CONDITION_STATE = ConditionsController.CONDITION_STATE;

    public static final String CONDITION_TYPE = ConditionsController.CONDITION_TYPE;

    public int getConditionCount( int index1 );

    public HashMap<String, String> getCondition( int index1, int index2 );

    public void addCondition( int index1, int index2 );

    public void deleteCondition( int index1, int index2 );

    public void editCondition( int index1, int index2 );

    public void duplicateCondition( int index1, int index2 );

    public void addCondition( );

    public void evalEditablePanelSelectionEvent( EditablePanel source, int oldState, int newState );

    public void evalFunctionChanged( EvalFunctionPanel source, int index1, int index2, int oldValue, int newValue );
}
