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
package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

public class LOMDialog extends JDialog {

    private LOMDataControl dataControl;

    private JTabbedPane tabs;

    private JButton ok;

    private int currentTab;

    private static final String helpPath = "metadata/LOM.html";

    public LOMDialog( LOMDataControl dataControl ) {

        super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "LOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
        this.dataControl = dataControl;

        JButton infoButton = new JButton( new ImageIcon( "img/icons/information.png" ) );
        infoButton.setContentAreaFilled( false );
        infoButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        infoButton.setFocusable( false );
        infoButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new HelpDialog( helpPath );
            }
        } );

        tabs = new JTabbedPane( );
        tabs.insertTab( TextConstants.getText( "LOM.General.Tab" ), null, new LOMGeneralPanel( dataControl.getGeneral( ) ), TextConstants.getText( "LOM.General.Tip" ), 0 );
        tabs.insertTab( TextConstants.getText( "LOM.LifeCycle.Tab" ) + " & " + TextConstants.getText( "LOM.Technical.Tab" ), null, new LOMLifeCycleAndTechnicalPanel( dataControl.getLifeCycle( ), dataControl.getTechnical( ) ), TextConstants.getText( "LOM.LifeCycle.Tip" ) + " & " + TextConstants.getText( "LOM.Technical.Tip" ), 1 );
        tabs.insertTab( TextConstants.getText( "LOM.Educational.Tab" ), null, new LOMEducationalPanel( dataControl.getEducational( ) ), TextConstants.getText( "LOM.Educational.Tip" ), 2 );

        tabs.add( new JPanel( ), 3 );
        tabs.setTabComponentAt( 3, infoButton );
        tabs.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( tabs.getSelectedIndex( ) == 3 ) {
                    tabs.setSelectedIndex( currentTab );
                }
                else
                    currentTab = tabs.getSelectedIndex( );
            }

        } );
        tabs.setSelectedIndex( 1 );
        currentTab = 1;

        // create button to close the dialog
        ok = new JButton( "OK" );
        ok.setToolTipText( "Close the window" );
        ok.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                close( );
            }
        } );

        JPanel cont = new JPanel( );
        cont.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridy = 0;
        cont.add( tabs, c );
        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        c.ipady = 0;
        cont.add( ok, c );

        // Set size and position and show the dialog
        this.getContentPane( ).setLayout( new BorderLayout( ) );
        this.getContentPane( ).add( cont, BorderLayout.CENTER );
        //setSize( new Dimension( 400, 200 ) );
        setMinimumSize( new Dimension( 450, 520 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );

    }

    public void close( ) {

        this.dispose( );
    }
}
