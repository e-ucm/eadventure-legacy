package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.StopEvent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.TextConstants;

public class VideoPanel extends JPanel implements ControllerListener {

	/**
	 * Required 
	 */
	private static final long serialVersionUID = 3265909297116717581L;

	private boolean realized = false;

	private boolean stoped = false;

	private Player player;

	private boolean prefetched;

	public VideoPanel( String videoPath ) {
		this( );
		loadVideo( videoPath );
	}

	public VideoPanel( ) {
		realized = stoped = prefetched = false;
		this.setLayout( new GridBagLayout() );
		GridBagConstraints c= new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.CENTER;
		
		this.add( new JLabel("Video not available"), c);

	}

	public synchronized void blockingRealize( ) {
		player.realize( );
		while( !realized ) {
			try {
				wait( );
			} catch( InterruptedException e ) {
				System.out.println( "Interrupted while waiting on realize...exiting." );
				System.exit( 1 );
			}
		}
	}

	public synchronized void blockingStop( ) {
		player.stop( );
		while( !stoped ) {
			try {
				wait( );
			} catch( InterruptedException e ) {
				System.out.println( "Interrupted while waiting on realize...exiting." );
				System.exit( 1 );
			}
		}
	}

	public synchronized void blockingPrefetch( ) {
		player.prefetch( );
		while( !prefetched ) {
			try {
				wait( );
			} catch( InterruptedException e ) {
				System.out.println( "Interrupted while waiting on realize...exiting." );
				System.exit( 1 );
			}
		}
	}

	public synchronized void controllerUpdate( ControllerEvent event ) {
		if( event instanceof RealizeCompleteEvent ) {
			realized = true;
			notify( );
		} else if( event instanceof EndOfMediaEvent ) {
			//eomReached = true;
		} else if( event instanceof StopEvent ) {
			stoped = true;
			notify( );
		} else if( event instanceof PrefetchCompleteEvent ) {
			prefetched = true;
			notify( );
		}
		//(else if (event instanceof )
	}

	public void loadVideo( String videoPath ) {
		try {
			realized = stoped = prefetched = false;
			if( player != null )
				blockingStop( );
			this.removeAll( );

			player = Manager.createPlayer( AssetsController.getVideo( videoPath ) );
			player.addControllerListener( this );
			this.blockingRealize( );
			this.blockingPrefetch( );
			this.setLayout( new BorderLayout( ) );
			Component visual = player.getVisualComponent( ); 
			if (visual!=null)
				this.add( visual, BorderLayout.CENTER );
			else
				Controller.getInstance().showErrorDialog( TextConstants.getText( "Error.NoVideoFormat"), TextConstants.getText( "Error.NoVideoFormat.Message") );
			this.add( player.getControlPanelComponent( ), BorderLayout.SOUTH );
			this.updateUI( );
			//this.blockingPrefetch( );

			//this.add( 
			//player.getControlPanelComponent( ));

		} catch( NoPlayerException e ) {
			Controller.getInstance().showErrorDialog( TextConstants.getText( "Error.NoVideoFormat"), TextConstants.getText( "Error.NoVideoFormat.Message") );
		} catch( MalformedURLException e ) {
			Controller.getInstance().showErrorDialog( TextConstants.getText( "Error.VideoGeneralError"), TextConstants.getText( "Error.VideoGeneralError.Message") );
		} catch( IOException e ) {
			Controller.getInstance().showErrorDialog( TextConstants.getText( "Error.VideoGeneralError"), TextConstants.getText( "Error.VideoGeneralError.Message") );
		}

	}

	public void removeVideo( ) {
		if (player!=null){
			blockingStop( );
			this.removeAll( );
			this.setLayout( new GridBagLayout() );
			GridBagConstraints c= new GridBagConstraints();
			c.fill=GridBagConstraints.BOTH;
			c.anchor=GridBagConstraints.CENTER;
			
			this.add( new JLabel("Video not available"), c);
			updateUI();
			player = null;
		}

	}

	public void stopVideo( ) {
		if (player!=null)
			blockingStop( );
	}

}
