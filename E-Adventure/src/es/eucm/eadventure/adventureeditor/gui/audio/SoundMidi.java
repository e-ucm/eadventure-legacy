package es.eucm.eadventure.adventureeditor.gui.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import es.eucm.eadventure.adventureeditor.control.controllers.AssetsController;

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
	 * Creates a new SoundMidi. <p>If any error happens, an error message is printed to System.out and this sound is
	 * disabled
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
		} catch( InvalidMidiDataException e ) {
			sequencer = null;
			System.err.println( "WARNING - \"" + filename + "\" is an invalid MIDI file - sound will be disabled" );
		} catch( IOException e ) {
			sequencer = null;
			System.err.println( "WARNING - could not open \"" + filename + "\" - sound will be disabled" );
		} catch( MidiUnavailableException e ) {
			sequencer = null;
			System.err.println( "WARNING - MIDI device is unavailable to play \"" + filename + "\" - sound will be disabled" );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.multimedia.Sound#playOnce()
	 */
	public void playOnce( ) {
		if( sequencer != null ) {
			sequencer.setTickPosition( sequencer.getLoopStartPoint( ) );
			sequencer.start( );
			while( sequencer.isRunning( ) )
				try {
					sleep( 100 );
				} catch( InterruptedException e ) {}
		} else
			stopPlaying( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.multimedia.Sound#stopPlaying()
	 */
	public void stopPlaying( ) {
		if( sequencer != null && sequencer.isOpen( ) ) {
			sequencer.stop( );
			sequencer.close( );
		}
	}
}
