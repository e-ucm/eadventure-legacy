/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
