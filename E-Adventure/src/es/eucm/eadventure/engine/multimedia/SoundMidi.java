package es.eucm.eadventure.engine.multimedia;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class is an implementation for Sound that
 * is able to play midi sounds.
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
     * <p>If any error happens, an error message is printed to System.out
     * and this sound is disabled
     * @param filename path to the midi file
     * @param loop defines whether or not the sound must be played in a loop
     */
    public SoundMidi( String filename, boolean loop ) {
        super( loop );

        try {
            // Open the MIDI file
            InputStream is = ResourceHandler.getInstance( ).getResourceAsStreamFromZip( filename );
            // Load sound from file
            sequence = MidiSystem.getSequence( is );
            sequencer = MidiSystem.getSequencer( );
            sequencer.open( );
            sequencer.setSequence( sequence );
            // Close the MIDI file
            is.close( );
        } catch( InvalidMidiDataException e ) {
            sequencer = null;
            //System.out.println( "WARNING - \"" + filename + "\" is an invalid MIDI file - sound will be disabled" );
        } catch( IOException e ) {
            sequencer = null;
            //System.out.println( "WARNING - could not open \"" + filename + "\" - sound will be disabled" );
        } catch( MidiUnavailableException e ) {
            sequencer = null;
            //System.out.println( "WARNING - MIDI device is unavailable to play \"" + filename + "\" - sound will be disabled" );
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.multimedia.Sound#playOnce()
     */
    public void playOnce( ) {
        if( sequencer != null ) {
            // Start playing the sound, and wait until it has finished
            sequencer.setTickPosition( sequencer.getLoopStartPoint( ) );
            sequencer.start( );
            while( sequencer.isRunning( ) )
                try {
                    sleep( 250 );
                } catch( InterruptedException e ) {
                }
        } else
            // If there was any error loading the sound, do nothing
            stopPlaying( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.multimedia.Sound#stopPlaying()
     */
    public synchronized void stopPlaying( ) {
        super.stopPlaying( );
        // Stop playing the sound
        if( sequencer != null && sequencer.isOpen( ) ) {
            sequencer.stop( );
            sequencer.close( ); 
        }
    }

    /*
     *  (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    public synchronized void finalize( ) {
        // Free resources
        sequencer.stop( );
        sequencer.close( );
        sequencer = null;
        sequence = null;
        
    }

}
