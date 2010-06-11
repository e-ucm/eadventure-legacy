/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.assessment;

import java.util.List;

public class TimedAssessmentEffect extends AssessmentEffect {

    protected Integer minTime;

    protected Integer maxTime;

    public TimedAssessmentEffect( ) {

        super( );
        minTime = 0;
        maxTime = 120;
    }
    
    @Override
    public List<AssessmentProperty> getAssessmentProperties( ) {

        return properties;
    }

    /**
     * @return the minTime
     */
    public Integer getMinTime( ) {

        return minTime;
    }

    /**
     * @param minTime
     *            the minTime to set
     */
    public void setMinTime( Integer minTime ) {

        this.minTime = minTime;
    }

    /**
     * @return the maxTime
     */
    public Integer getMaxTime( ) {

        return maxTime;
    }

    /**
     * @param maxTime
     *            the maxTime to set
     */
    public void setMaxTime( Integer maxTime ) {

        this.maxTime = maxTime;
    }

    public boolean isMinTimeSet( ) {

        return this.minTime != Integer.MIN_VALUE;
    }

    public boolean isMaxTimeSet( ) {

        return this.maxTime != Integer.MAX_VALUE;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        TimedAssessmentEffect tae = (TimedAssessmentEffect) super.clone( );
        tae.maxTime = maxTime;
        tae.minTime = minTime;
        return tae;
    }
}
