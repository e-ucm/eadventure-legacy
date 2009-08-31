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
package es.eucm.eadventure.comm.manager.commManager;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.engine.adaptation.AdaptationEngine;

public interface CommManagerApi {

	
	public static final int SCORMV12_TYPE = 0;
	
	public static final int SCORMV2004_TYPE = 1;
	
	public static final int LD_ENVIROMENT_TYPE = 2;
	
	public boolean connect(HashMap<String,String> info); //throws CommException;
	
	public boolean disconnect(HashMap<String,String> info); //throws CommException;
	
	public void notifyRelevantState(List<AssessmentProperty> list);
	
	public boolean isConnected();
	
	public void setAdaptationEngine(AdaptationEngine engine);
	
	public int getCommType();
	
	public void getAdaptedState( Set<String> properties );
	
	
	public HashMap<String,String> getInitialStates();
	
	
}
