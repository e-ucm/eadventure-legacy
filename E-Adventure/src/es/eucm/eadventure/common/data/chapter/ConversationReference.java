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
package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.HasTargetId;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * This class holds the data of a conversation reference in eAdventure
 */
public class ConversationReference implements Cloneable, Documented, HasTargetId {

    /**
     * Id of the target conversation
     */
    private String idTarget;

    /**
     * Documentation of the conversation reference.
     */
    private String documentation;

    /**
     * Conditions to trigger the conversation
     */
    private Conditions conditions;

    /**
     * Creates a new ConversationReference
     * 
     * @param idTarget
     *            the id of the conversation that is referenced
     */
    public ConversationReference( String idTarget ) {

        this.idTarget = idTarget;

        documentation = null;
        conditions = new Conditions( );
    }

    /**
     * Returns the id of the conversation that is referenced
     * 
     * @return the id of the conversation that is referenced
     */
    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Returns the documentation of the conversation.
     * 
     * @return the documentation of the conversation
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * Returns the conditions for this conversation
     * 
     * @return the conditions for this conversation
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * Sets the new conversation id target.
     * 
     * @param idTarget
     *            Id of the referenced conversation
     */
    public void setTargetId( String idTarget ) {

        this.idTarget = idTarget;
    }

    /**
     * Changes the documentation of this conversation reference.
     * 
     * @param documentation
     *            The new documentation
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    /**
     * Changes the conditions for this conversation
     * 
     * @param conditions
     *            the new conditions
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ConversationReference cr = (ConversationReference) super.clone( );
        cr.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        cr.documentation = ( documentation != null ? new String( documentation ) : null );
        cr.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        return cr;
    }
}
