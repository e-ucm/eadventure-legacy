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
package es.eucm.eadventure.common.data.chapter.conversation.line;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.Player;

/**
 * This class stores a single conversation line, along with the name of the
 * speaker character.
 */
public class ConversationLine implements Cloneable, Named {

    /**
     * Constant for the player identifier for the lines.
     */
    public static final String PLAYER = Player.IDENTIFIER;

    /**
     * String that holds the name of the character.
     */
    private String name;

    /**
     * Sentence said by the character.
     */
    private String text;

    /**
     * Path for the audio track where the line is recorded. Its use is optional.
     */
    private String audioPath;

    /**
     * Tell if the line has to be read by synthesizer
     */
    private boolean synthesizerVoice;

    /**
     * Conditions associated to this line
     */
    private Conditions conditions;

    /**
     * Constructor.
     * 
     * @param name
     *            Name of the character
     * @param text
     *            Sentence
     */
    public ConversationLine( String name, String text ) {

        this.name = name;
        this.text = text;
        this.synthesizerVoice = false;
        conditions = new Conditions( );
    }

    /**
     * Returns the name of the character.
     * 
     * @return The name of the character
     */
    public String getName( ) {

        return name;
    }

    /**
     * Returns the text of the converstational line.
     * 
     * @return The text of the conversational line
     */
    public String getText( ) {

        return text;
    }

    /**
     * Returns if the line belongs to the player.
     * 
     * @return True if the line belongs to the player, false otherwise
     */
    public boolean isPlayerLine( ) {

        return name.equals( PLAYER );
    }

    /**
     * Sets the new name of the line.
     * 
     * @param name
     *            New name
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * Sets the new text of the line.
     * 
     * @param text
     *            New text
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * @return the audioPath
     */
    public String getAudioPath( ) {

        return audioPath;
    }

    /**
     * @param audioPath
     *            the audioPath to set
     */
    public void setAudioPath( String audioPath ) {

        this.audioPath = audioPath;
    }

    /**
     * Returns true if the audio path is valid. That is when it is not null and
     * different to ""
     */
    public boolean isValidAudio( ) {

        return audioPath != null && !audioPath.equals( "" );
    }

    /**
     * Returns if the line has to be read by synthesizer
     * 
     * @return if this line has to be read by synthesizer
     */
    public Boolean getSynthesizerVoice( ) {

        return synthesizerVoice;
    }

    /**
     * Set if the line to be read by synthesizer
     * 
     * @param synthesizerVoice
     *            true for to be read by synthesizer
     */
    public void setSynthesizerVoice( Boolean synthesizerVoice ) {

        this.synthesizerVoice = synthesizerVoice;
    }

    /**
     * @return the conditions
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * @param conditions
     *            the conditions to set
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ConversationLine cl = (ConversationLine) super.clone( );
        cl.audioPath = ( audioPath != null ? new String( audioPath ) : null );
        cl.name = ( name != null ? new String( name ) : null );
        cl.synthesizerVoice = synthesizerVoice;
        cl.text = ( text != null ? new String( text ) : null );
        cl.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        return cl;
    }

}
