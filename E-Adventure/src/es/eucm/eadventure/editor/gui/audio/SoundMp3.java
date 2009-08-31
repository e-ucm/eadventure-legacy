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
package es.eucm.eadventure.editor.gui.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This class is an implementation for Sound that is able to play mp3 sounds.
 */
public class SoundMp3 extends Sound {

    /**
     * Filename of the mp3 file.
     */
    private String filename;

    /**
     * Format to decode the sound.
     */
    private AudioFormat decodedFormat;

    /**
     * Input stream with the decoded mp3.
     */
    private AudioInputStream audioInputStream;

    /**
     * Source of the MP3 data.
     */
    private SourceDataLine line;

    /**
     * Tells if the sound must stop or not.
     */
    private boolean stop;

    /**
     * Creates a new SoundMp3
     * <p>
     * If any error happens, an error message is printed to System.out and this
     * sound is disabled
     * 
     * @param filename
     *            path to the midi file
     */
    public SoundMp3( String filename ) {

        super( );

        // Store the file name and set stop to false
        this.filename = filename;
        stop = false;

        try {
            // Open MP3 file
            InputStream is = AssetsController.getInputStream( filename );
            AudioInputStream ais = AudioSystem.getAudioInputStream( is );

            AudioFormat baseFormat = ais.getFormat( );
            decodedFormat = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED, // Encoding to use
            baseFormat.getSampleRate( ), // sample rate (same as base format)
            16, // sample size in bits (thx to Javazoom)
            baseFormat.getChannels( ), // # of Channels
            baseFormat.getChannels( ) * 2, // Frame Size
            baseFormat.getSampleRate( ), // Frame Rate
            false // Big Endian
            );

            audioInputStream = AudioSystem.getAudioInputStream( decodedFormat, ais );

            DataLine.Info info = new DataLine.Info( SourceDataLine.class, decodedFormat );
            line = (SourceDataLine) AudioSystem.getLine( info );
            line.open( decodedFormat );

        }
        catch( UnsupportedAudioFileException e ) {
            System.err.println( "WARNING - \"" + filename + "\" is a no supported MP3 file - sound will be disabled" );
        }
        catch( IOException e ) {
            System.err.println( "WARNING - could not open \"" + filename + "\" - sound will be disabled" );
        }
        catch( LineUnavailableException e ) {
            System.err.println( "WARNING - audio device is unavailable to play \"" + filename + "\" - sound will be disabled" );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.multimedia.Sound#playOnce()
     */
    @Override
    public void playOnce( ) {

        if( line != null ) {
            try {
                line.open( decodedFormat );
                byte[] data = new byte[ 4096 ];
                line.start( );

                int nBytesRead;
                while( !stop && ( nBytesRead = audioInputStream.read( data, 0, data.length ) ) != -1 )
                    line.write( data, 0, nBytesRead );

            }
            catch( IOException e ) {
                stopPlaying( );
                System.out.println( "WARNING - could not open \"" + filename + "\" - sound will be disabled" );
            }
            catch( LineUnavailableException e ) {
                stopPlaying( );
                System.out.println( "WARNING - audio device is unavailable to play \"" + filename + "\" - sound will be disabled" );
            }

        }
        else
            // If there was any error loading the sound, do nothing
            stopPlaying( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.multimedia.Sound#stopPlaying()
     */
    @Override
    public void stopPlaying( ) {

        stop = true;

        // Free the data of the line
        if( line != null ) {
            line.drain( );
            line.stop( );
            line.close( );
        }

        // Close the audio input stream
        if( audioInputStream != null ) {
            try {
                audioInputStream.close( );
            }
            catch( IOException e ) {
                ReportDialog.GenerateErrorReport( e, false, "WARNING - could not close \"" + filename + "\" - sound will be disabled" );
            }
        }
    }
}
