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
 * An effect that triggers a conversation.
 */
public class TriggerConversationEffect extends AbstractEffect implements HasTargetId {

    /**
     * Id of the conversation to be played
     */
    private String targetConversationId;

    /**
     * Creates a new TriggerConversationEffect.
     * 
     * @param targetConversationId
     *            the id of the conversation to be triggered
     */
    public TriggerConversationEffect( String targetConversationId ) {

        super( );
        this.targetConversationId = targetConversationId;
    }

    @Override
    public int getType( ) {

        return TRIGGER_CONVERSATION;
    }

    /**
     * Returns the targetConversationId
     * 
     * @return String containing the targetConversationId
     */
    public String getTargetId( ) {

        return targetConversationId;
    }

    /**
     * Sets the new targetConversationId
     * 
     * @param targetConversationId
     *            New targetConversationId
     */
    public void setTargetId( String targetConversationId ) {

        this.targetConversationId = targetConversationId;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        TriggerConversationEffect tce = (TriggerConversationEffect) super.clone( );
        tce.targetConversationId = ( targetConversationId != null ? new String( targetConversationId ) : null );
        return tce;
    }
}
