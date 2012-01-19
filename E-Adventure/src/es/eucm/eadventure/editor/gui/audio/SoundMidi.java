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
package es.eucm.eadventure.editor.gui.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This class is an implementation for Sound that is able to play midi sounds.
 */
public class SoundMidi extends Sound {

    /**
     * Sequencer for the Midi
     */
    private Sequencer sequencer;

    /**
     * Sequence containing the Midi
     */
    private Sequence sequence;

    /**
     * Creates a new SoundMidi.
     * <p>
     * If any error happens, an error message is printed to System.out and this
     * sound is disabled
     * 
     * @param filename
     *            path to the midi file
     */
    public SoundMidi( String filename ) {

        super( );

        try {
            InputStream is = AssetsController.getInputStream( filename );
            sequence = MidiSystem.getSequence( is );
            sequencer = MidiSystem.getSequencer( );
            sequencer.open( );
            sequencer.setSequence( sequence );
            is.close( );
        }
        catch( InvalidMidiDataException e ) {
            sequencer = null;
            System.err.println( "WARNING - \"" + filename + "\" is an invalid MIDI file - sound will be disabled" );
        }
        catch( IOException e ) {
            sequencer = null;
            System.err.println( "WARNING - could not open \"" + filename + "\" - sound will be disabled" );
        }
        catch( MidiUnavailableException e ) {
            sequencer = null;
            System.err.println( "WARNING - MIDI device is unavailable to play \"" + filename + "\" - sound will be disabled" );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.multimedia.Sound#playOnce()
     */
    @Override
    public void playOnce( ) {

        if( sequencer != null ) {
            sequencer.setTickPosition( sequencer.getLoopStartPoint( ) );
            sequencer.start( );
            while( sequencer.isRunning( ) )
                try {
                    sleep( 100 );
                }
                catch( InterruptedException e ) {
                }
        }
        else
            stopPlaying( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.multimedia.Sound#stopPlaying()
     */
    @Override
    public void stopPlaying( ) {

        if( sequencer != null && sequencer.isOpen( ) ) {
            sequencer.stop( );
            sequencer.close( );
        }
    }
}
