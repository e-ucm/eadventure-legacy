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
package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import es.eucm.eadventure.editor.gui.audio.Sound;
import es.eucm.eadventure.editor.gui.audio.SoundMidi;
import es.eucm.eadventure.editor.gui.audio.SoundMp3;

/**
 * This panel plays an audio file.
 * 
 * @author Bruno Torijano Bueno
 */
public class AudioPanel extends JPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Play button.
     */
    private JButton playButton;

    /**
     * Stop button.
     */
    private JButton stopButton;

    /**
     * Path to the sound file.
     */
    private String audioPath;

    /**
     * Sound that is being played.
     */
    private Sound sound;

    /**
     * Constructor.
     */
    public AudioPanel( ) {

        super( );

        // Initialize the sound to null
        audioPath = null;
        sound = null;

        // Load the icons
        Icon playImage = new ImageIcon( "img/buttons/play.png" );
        Icon stopImage = new ImageIcon( "img/buttons/stop.png" );

        // Add the ancestor listener
        addAncestorListener( new AncestorListener( ) {

            public void ancestorAdded( AncestorEvent event ) {

            }

            public void ancestorMoved( AncestorEvent event ) {

            }

            public void ancestorRemoved( AncestorEvent event ) {

                stopSound( );
            }
        } );

        // Set the layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Create and add the "Stop" button
        c.insets = new Insets( 10, 10, 10, 10 );
        stopButton = new JButton( stopImage );
        stopButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                stopSound( );
            }
        } );
        stopButton.setPreferredSize( new Dimension( 50, 50 ) );
        stopButton.setEnabled( false );
        add( stopButton, c );

        // Create and add the "Play" button
        c.gridx = 1;
        playButton = new JButton( playImage );
        playButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                playSound( );
            }
        } );
        playButton.setPreferredSize( new Dimension( 50, 50 ) );
        playButton.setEnabled( false );
        add( playButton, c );
    }

    /**
     * Constructor.
     * 
     * @param audioPath
     *            Path to the audio file (relative to the ZIP)
     */
    public AudioPanel( String audioPath ) {

        this( );

        // Sets the audio path
        loadAudio( audioPath );
    }

    /**
     * Sets a new audio to play in the panel.
     * 
     * @param audioPath
     *            Path to the audio file (relative to the ZIP)
     */
    public void loadAudio( String audioPath ) {

        this.audioPath = audioPath;
        playButton.setEnabled( true );
        stopButton.setEnabled( true );
        stopSound( );
    }

    /**
     * Deletes the audio path stored and stops the audio playing.
     */
    public void removeAudio( ) {

        audioPath = null;
        playButton.setEnabled( false );
        stopButton.setEnabled( false );
        stopSound( );
    }

    /**
     * Stops the sound being played (if there was one) and start playing a new
     * sound.
     */
    public synchronized void playSound( ) {

        // Stop the current sound if there was one
        stopSound( );

        // If there is an audio path stored
        if( audioPath != null ) {
            // Create a new player (depending on the file) and start playing
            String lowerCasePath = audioPath.toLowerCase( );
            if( lowerCasePath.endsWith( "mp3" ) )
                sound = new SoundMp3( audioPath );
            else if( lowerCasePath.endsWith( "mid" ) || lowerCasePath.endsWith( "midi" ) )
                sound = new SoundMidi( audioPath );

            // Play the sound
            sound.startPlaying( );
        }
    }

    /**
     * Stops the sound that is currently being played. If no sound was being
     * played, it does nothing.
     */
    public synchronized void stopSound( ) {

        // Stop playing the sound and delete the player
        if( sound != null ) {
            sound.stopPlaying( );
            sound = null;
        }
    }
}
