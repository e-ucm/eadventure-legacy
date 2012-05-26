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
