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

package es.eucm.eadventure.tracking.prv.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import es.eucm.eadventure.tracking.prv.form.TrackingContactForm.Gender;
import es.eucm.eadventure.tracking.prv.form.TrackingContactForm.Occupation;
import es.eucm.eadventure.tracking.prv.form.TrackingContactForm.TrackMe;


public class TrackingContactFormScreen extends JFrame{
    
    /**
     * Generated
     */
    private static final long serialVersionUID = 756993285990936134L;

    private boolean done = false;
    
    private TrackingContactForm contactForm;
    
    
    public TrackingContactFormScreen(){
        super ("Formulario de contacto sobre el juego \"La Dama Boba\"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        contactForm = new TrackingContactForm();
        final ConfigPanel<TrackingContactForm> cp = makeFormScreen();
        cp.setTarget(contactForm);
        cp.initialize();        
        cp.setOpaque( false );
        cp.setBackground( Color.WHITE );
        this.getContentPane( ).setBackground( Color.white );
        add(cp, BorderLayout.CENTER);
        
        JButton button = new JButton("Enviar");
        button.addActionListener( new ActionListener(){

            public void actionPerformed( ActionEvent e ) {
                TrackingContactFormScreen.this.setDone( true );
            }
            
        });
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque( false );
        buttonPanel.setBackground( Color.white );
        buttonPanel.add( button, BorderLayout.EAST );
        add(buttonPanel, BorderLayout.SOUTH);

        BufferedImage labelImg = null; 
        try {
            labelImg = ImageIO.read( TrackingContactFormScreen.class.getResourceAsStream( "instructions.png" ) );
        }
        catch( IOException e1 ) {
            e1.printStackTrace();
        }
        if (labelImg!=null){
            ImageIcon image = new ImageIcon(labelImg);
            JLabel label = new JLabel(image);
            add (label, BorderLayout.NORTH);
        }
        
        List<Image> icons = new ArrayList<Image>( );
        try {
            icons.add( ImageIO.read( TrackingContactFormScreen.class.getResourceAsStream( "dbicon.png" ) ));
        }
        catch( IOException e1 ) {
            e1.printStackTrace();
        }
        setIconImages( icons );
        
        this.pack( );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - this.getSize( ).width ) / 2, ( screenSize.height - getSize().height ) / 2 );
        this.setVisible( true );
    }
    
    
    private ConfigPanel<TrackingContactForm> makeFormScreen(){
        ConfigPanel<TrackingContactForm> config = new ConfigPanel<TrackingContactForm>();
        
        config.addOption(  new ConfigPanel.TextOption<TrackingContactForm>("Nombre", "Introduzca aquí su nombre", "name")) ;
        config.addOption(  new ConfigPanel.TextOption<TrackingContactForm>("Apellidos", "Introduzca aquí sus apellidos", "surname")) ;
        config.addOption( new ConfigPanel.IntegerOption<TrackingContactForm>( "Edad", "Su edad en años", "age", 1, 99 ) );
        config.addOption(  new ConfigPanel.ChoiceOption<TrackingContactForm>( "Sexo", "Masculino o Femenino", "gender", Gender.values( ) ) );
        config.addOption(  new ConfigPanel.ChoiceOption<TrackingContactForm>( "Ocupación", "Elija la opción que mejor describa a qué se dedica actualmente", "occupation", Occupation.values( ) ) );
        config.addOption(  new ConfigPanel.TextOption<TrackingContactForm>("Si quiere que nos pongamos en contacto con Ud. con novedades sobre el proyecto, introduzca su email aquí", "Ejemplo: manolo@pepito.es", "email")) ;
        config.addOption(  new ConfigPanel.TextOption<TrackingContactForm>("¿Qué uso pretende hacer del juego?", "", "intendedUse")) ;
        config.addOption(  new ConfigPanel.ChoiceOption<TrackingContactForm>( "Activar envío de datos de interacción ", "Sólo se enviarán datos anónimos sobre el uso del juego", "trackingEnabled", TrackMe.values( ) ) );
        config.endOptions( );
        return config;
    }

    public synchronized boolean isDone( ) {
    
        return done;
    }
    
    public synchronized void setDone( boolean done ) {
    
        this.done = done;
    }
    
    public static TrackingContactForm showForm(){
        TrackingContactFormScreen screen = new TrackingContactFormScreen();
        while (!screen.isDone( )){
            try {
                Thread.sleep( 500 );
            }
            catch( InterruptedException e ) {
                e.printStackTrace();
                break;
            }
        }
        TrackingContactForm contactForm = screen.getContactForm();
        screen.dispose( );
        return contactForm;
    }

    
    public static void main (String[]args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName( ));
        }
        catch( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        catch( InstantiationException e ) {
            e.printStackTrace();
        }
        catch( IllegalAccessException e ) {
            e.printStackTrace();
        }
        catch( UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }
        
        TrackingContactForm form = TrackingContactFormScreen.showForm( );
        System.out.println( form.toString( ));
    }

    
    public TrackingContactForm getContactForm( ) {
    
        return contactForm;
    }


    
    public void setContactForm( TrackingContactForm contactForm ) {
    
        this.contactForm = contactForm;
    }
}
