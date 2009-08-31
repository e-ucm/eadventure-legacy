/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
			public void ancestorAdded( AncestorEvent event ) {}

			public void ancestorMoved( AncestorEvent event ) {}

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
	 * Stops the sound being played (if there was one) and start playing a new sound.
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
	 * Stops the sound that is currently being played. If no sound was being played, it does nothing.
	 */
	public synchronized void stopSound( ) {
		// Stop playing the sound and delete the player
		if( sound != null ) {
			sound.stopPlaying( );
			sound = null;
		}
	}
}
