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

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that deactivates a flag.
 */
public class DeactivateEffect extends AbstractEffect implements HasTargetId {

    /**
     * Name of the flag to be activated
     */
    private String idFlag;

    /**
     * Creates a new DeactivateEffect.
     * 
     * @param idFlag
     *            the id of the flag to be deactivated
     */
    public DeactivateEffect( String idFlag ) {

        super( );
        this.idFlag = idFlag;
    }

    @Override
    public int getType( ) {

        return DEACTIVATE;
    }

    /**
     * Returns the idFlag
     * 
     * @return String containing the idFlag
     */
    public String getTargetId( ) {

        return idFlag;
    }

    /**
     * Sets the new idFlag
     * 
     * @param idFlag
     *            New idFlag
     */
    public void setTargetId( String idFlag ) {

        this.idFlag = idFlag;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        DeactivateEffect de = (DeactivateEffect) super.clone( );
        de.idFlag = ( idFlag != null ? new String( idFlag ) : null );
        return de;
    }
}
