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

package es.eucm.eadventure.tracking.prv.studentcode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class NameInputScreen extends JDialog{

    private static final int WIDTH=810;
    private static final int HEIGHT=635;
    
    private class NISLayout implements LayoutManager2{

        public void addLayoutComponent( String name, Component comp ) {
        }

        public void removeLayoutComponent( Component comp ) {
        }

        public Dimension preferredLayoutSize( Container parent ) {
            return new Dimension(WIDTH,HEIGHT);
        }

        public Dimension minimumLayoutSize( Container parent ) {
            return preferredLayoutSize(parent);
        }

        public void layoutContainer( Container parent ) {
            instructions.setBounds( new Rectangle(50,200,550,80) );
            input.setBounds( new Rectangle(340,270,140,25) );
            message.setBounds( new Rectangle(50,300,550,80) );
            continueBtn.setBounds( new Rectangle(335,370,150,25) );
        }

        public void addLayoutComponent( Component comp, Object constraints ) {

            // TODO Auto-generated method stub
            
        }

        public Dimension maximumLayoutSize( Container target ) {
            return preferredLayoutSize(target);
        }

        public float getLayoutAlignmentX( Container target ) {

            // TODO Auto-generated method stub
            return 0;
        }

        public float getLayoutAlignmentY( Container target ) {

            // TODO Auto-generated method stub
            return 0;
        }

        public void invalidateLayout( Container target ) {

            // TODO Auto-generated method stub
            
        }
        
    }
    
    private JTextArea instructions;
    private JTextArea input;
    private JTextArea message;
    private JButton continueBtn;
    private List<Integer> codes;
    private int code;
    private Image toPaint = null;
    
    public NameInputScreen ( ){
        this.setModal( true );
        NISPanel panel = new NISPanel();
        panel.setLayout( new NISLayout() );
        ImageIcon image = new ImageIcon("Caratula.jpg");
        toPaint = image.getImage( );
        code=-2;
        codes= ReadCodes.getEncodedCodes( );
        this.getRootPane( ).setLayout( new BorderLayout() );
        this.getRootPane( ).add( panel, BorderLayout.CENTER );
        instructions= new JTextArea ("Bienvenido al juego de la Dama Boba. Introduce los cuatro u ocho primeros dígitos del código que te han dado para empezar a jugar (los que aparecen antes del guión).");
        Font font = new Font ("Tahoma", Font.BOLD, 16);
        instructions.setWrapStyleWord( true );
        instructions.setFont( font );
        instructions.setEditable( false );
        instructions.setLineWrap( true );
        instructions.setOpaque( false );
        this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        input = new JTextArea();
        input.setFont( font );
        input.getDocument( ).addDocumentListener( new DocumentListener(){

            public void insertUpdate( DocumentEvent e ) {
                checkCode();
            }

            public void removeUpdate( DocumentEvent e ) {
                checkCode();
            }

            public void changedUpdate( DocumentEvent e ) {
                checkCode();
            }
            
        });
        input.setBorder( BorderFactory.createEtchedBorder( ) );
        message=new JTextArea("");
        message.setEditable( false );
        message.setWrapStyleWord( true );
        message.setLineWrap( true );
        message.setOpaque( false );
        message.setFont( font );
        continueBtn = new JButton ("Empezar");
        continueBtn.setFont( font );
        continueBtn.addActionListener( new ActionListener(){

            public void actionPerformed( ActionEvent e ) {
                close(!checkCode());    
            }
            
        });
        continueBtn.setEnabled( false );
        panel.add( instructions );
        panel.add( input );
        panel.add( message );
        panel.add( continueBtn );
        panel.setOpaque( false );
        this.addWindowListener( new WindowListener(){

            public void windowOpened( WindowEvent e ) {
                
            }

            public void windowClosing( WindowEvent e ) {
                close(true);
            }

            public void windowClosed( WindowEvent e ) {
            }

            public void windowIconified( WindowEvent e ) {
                
            }

            public void windowDeiconified( WindowEvent e ) {
                
            }

            public void windowActivated( WindowEvent e ) {
                
            }

            public void windowDeactivated( WindowEvent e ) {
                
            }
            
        });
        //this.setUndecorated( true );
        int sw=Toolkit.getDefaultToolkit( ).getScreenSize( ).width;
        int sh=Toolkit.getDefaultToolkit( ).getScreenSize( ).height;
        int x=(sw-WIDTH)/2, y=(sh-HEIGHT)/2;
        this.setLocation( x, y );
        //this.pack( );
        this.setSize( WIDTH, HEIGHT );
        this.setResizable( false );
        this.setVisible( true );
        input.requestFocusInWindow( );
    }

    private void close( boolean askConfirmation ){
        if (askConfirmation) {
            int option=JOptionPane.showConfirmDialog( NameInputScreen.this, "¿Estás seguro de que quieres cerrar el juego?" );
            if (option ==JOptionPane.OK_OPTION){
                code=-1;
                dispose( );
            } 
            
        } else {
            dispose( );
        }
    }
    
    private class NISPanel extends JPanel {
        @Override
        public void paint (Graphics g){
            g.drawImage( toPaint, 0, 0, 800, 600, null );
            super.paint( g );
        }
    }
    
    private boolean checkCode(){
        String txt = input.getText( );
        boolean enterPressed = txt.endsWith( "\n" ); 
        txt=txt.replace( "\t", "" ).replaceAll( "\n", "" ).replaceAll(" ","");
        String txt2="";
        for (int i=0; i<txt.length( ); i++){
            if (txt.charAt( i )>='0' && txt.charAt( i )<='9'){
                txt2+=txt.charAt(i);
            }
        }
        txt=txt2;
        String m="";
        boolean valid=true;
        code=-1;
        if (txt==null || txt.length( )<4){
            m ="El código tiene que ser de al menos 4 dígitos numéricos. Has introducido menos dígitos.";
            valid=false;
            code=-1;
        } else if (txt.length( )>8){
            m ="Introduce sólo los 4 u 8 dígitos anteriores al guión. El código que has introducido es demasiado largo.";
            valid=false;
            code=-1;
        }
        else {
            try {
                code=Integer.parseInt( txt );
                if (codes.contains( code )){
                    valid =true;
                    m="Código correcto. Pulsa \"Empezar\" para lanzar el juego.";
                } else {
                    code=-1;
                    valid=false;
                    m="Código incorrecto. Tienes que introducir el código que te han dado en clase.";
                }
            }catch(NumberFormatException e){
                code=-1;
                valid=false;
                m="¡Introduce sólo números 0-9! Ni letras ni signos raros.";
            }
        }
        
        message.setText( m );
        if (valid)
            message.setForeground( Color.green );
        else
            message.setForeground( Color.red );
        
        continueBtn.setEnabled( valid );
        if (valid && enterPressed){
            close(false);
        }
        return valid;
    }
    
    
    
    public static void main (String[]args){
        int code = NameInputScreen.getCode( );
        System.out.println("CODE="+code );
    }

    public static int getCode( ) {
        
        NameInputScreen nis = new NameInputScreen();
        System.out.println( "NIS Created" );
        int code =nis.code;
        System.out.println( "RETURNING CODE: "+code );
        return code;
    }
}
