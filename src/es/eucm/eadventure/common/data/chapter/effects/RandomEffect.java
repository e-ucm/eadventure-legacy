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
package es.eucm.eadventure.common.data.chapter.effects;

/**
 * Data representation of a random effect. According to a probability defined by
 * user, the system chooses between two effects which is to be launched So the
 * behaviour is: PROBABILITY times POSITIVE EFFECT is triggered 100-PROBABILITY
 * times NEGATIVE EFFECT is triggered
 * 
 * @author Javier
 */
public class RandomEffect extends AbstractEffect {

    /**
     * Effect to be triggered PROBABILITY% of the times
     */
    private AbstractEffect positiveEffect;

    /**
     * Effect to be triggered 100-PROBABILITY% of the times
     */
    private AbstractEffect negativeEffect;

    /**
     * Probability in range 0%-100%
     */
    private int probability;

    /**
     * Constructor
     * 
     * @param probability
     */
    public RandomEffect( int probability ) {

        super( );
        this.probability = probability;
    }

    /**
     * Default constructor. Sets probability to 50%
     */
    public RandomEffect( ) {

        this( 50 );
    }

    @Override
    public int getType( ) {

        return Effect.RANDOM_EFFECT;
    }

    /**
     * @param positiveEffect
     *            the positiveEffect to set
     */
    public void setPositiveEffect( AbstractEffect positiveEffect ) {

        this.positiveEffect = positiveEffect;
    }

    /**
     * @param negativeEffect
     *            the negativeEffect to set
     */
    public void setNegativeEffect( AbstractEffect negativeEffect ) {

        this.negativeEffect = negativeEffect;
    }

    /**
     * @return the probability
     */
    public int getProbability( ) {

        return probability;
    }

    /**
     * @param probability
     *            the probability to set
     */
    public void setProbability( int probability ) {

        this.probability = probability;
    }

    /**
     * @return the positiveEffect
     */
    public AbstractEffect getPositiveEffect( ) {

        return positiveEffect;
    }

    /**
     * @return the negativeEffect
     */
    public AbstractEffect getNegativeEffect( ) {

        return negativeEffect;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        RandomEffect re = (RandomEffect) super.clone( );
        re.negativeEffect = ( negativeEffect != null ? (AbstractEffect) negativeEffect.clone( ) : null );
        re.positiveEffect = ( positiveEffect != null ? (AbstractEffect) positiveEffect.clone( ) : null );
        re.probability = probability;
        return re;
    }
}
