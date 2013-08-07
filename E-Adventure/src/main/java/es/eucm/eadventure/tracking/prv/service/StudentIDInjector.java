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

package es.eucm.eadventure.tracking.prv.service;

import java.util.List;

import es.eucm.eadventure.engine.assessment.AssessmentEngine;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.tracking.prv.GameLogEntry;


public class StudentIDInjector extends GameLogConsumer{

    private String studentID;
    
    public StudentIDInjector(ServiceConstArgs args){
        super(args);
        studentID=args.config.getStudentId( );
        consumerCode(null);
    }
    
    @Override
    protected boolean consumerCode( List<GameLogEntry> newQ ) {
        if (Game.getInstance( )!=null){
            if (Game.getInstance( ).getAssessmentEngine( )!=null){
                AssessmentEngine ae = Game.getInstance( ).getAssessmentEngine( );
                if (studentID!=null){
                    System.out.println( "Injecting student ID:"+studentID);
                    ae.setPlayerName( studentID );
                    this.setTerminate( true );
                }
            }
        }
        return true;
    }

    @Override
    protected boolean consumerClose( List<GameLogEntry> newQ ) {
        return false;
    }

    @Override
    protected void consumerInit( ) {
        consumerCode(null);
    }

}
