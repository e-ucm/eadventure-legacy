package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JFileChooser;

import es.eucm.eadventure.common.gui.TextConstants;

public class TestLargeImages extends JFrame{

	private ImagePanel imagePanel;
	
	public TestLargeImages(){
		super("Prueba");
		
		imagePanel = new ImagePanel();
		this.getContentPane( ).setLayout( new BorderLayout() );
		this.getContentPane( ).add( imagePanel, BorderLayout.CENTER );
		
		JPanel buttonsPanel = new JPanel();
		JButton load = new JButton("Load");
		load.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog( null );
				if (chooser.getSelectedFile( )!=null)
					imagePanel.loadImage( chooser.getSelectedFile( ).getAbsolutePath( ) );
			}
			
		});
		buttonsPanel.add( load );
		this.addWindowListener( new WindowListener(){

			public void windowActivated( WindowEvent e ) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosed( WindowEvent e ) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosing( WindowEvent e ) {
				setVisible(false);
				dispose();
			}

			public void windowDeactivated( WindowEvent e ) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified( WindowEvent e ) {
				// TODO Auto-generated method stub
				
			}

			public void windowIconified( WindowEvent e ) {
				// TODO Auto-generated method stub
				
			}

			public void windowOpened( WindowEvent e ) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.getContentPane( ).add( buttonsPanel, BorderLayout.SOUTH );
		this.setSize( 800,600 );
		this.setVisible( true );
		
	}
	
	public static void main(String[]args){
		TextConstants.loadStrings( "english.xml" );
		new TestLargeImages();
	}
	
}
