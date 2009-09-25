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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

public class LOMESDialog extends JDialog {

    private LOMESDataControl dataControl;

    private JTabbedPane tabs;

    private JButton ok;

    private int currentTab;

    private static final String helpPath = "metadata/LOMES.html";

    public LOMESDialog( LOMESDataControl dataControl ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "LOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
        this.dataControl = dataControl;

        Controller.getInstance( ).pushWindow( this );

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
        tabs.insertTab( TC.get( "LOM.General.Tab" ), null, new LOMESGeneralPanel( dataControl.getGeneral( ) ), TC.get( "LOM.General.Tip" ), 0 );
        tabs.insertTab( TC.get( "LOM.LifeCycle.Tab" ) + " & " + TC.get( "LOM.Technical.Tab" ), null, new LOMESLifeCycleAndTechnicalPanel( dataControl.getLifeCycle( ), dataControl.getTechnical( ) ), TC.get( "LOM.LifeCycle.Tip" ) + " & " + TC.get( "LOM.Technical.Tip" ), 1 );
        tabs.insertTab( TC.get( "IMS.Meta.Tab" ), null, new LOMESMetaMetaDataPanel( dataControl.getMetametadata( ) ), TC.get( "IMS.Meta.Tip" ), 2 );
        tabs.insertTab( TC.get( "LOM.Educational.Tab" ), null, new LOMESEducationalPanel( dataControl.getEducational( ) ), TC.get( "LOM.Educational.Tip" ), 3 );
        tabs.insertTab( TC.get( "IMS.Rights.Tab" ) + " & " + TC.get( "IMS.Classification.Tab" ), null, new LOMESRightsAndClassificationPanel( dataControl.getRights( ), dataControl.getClassification( ) ), TC.get( "IMS.Rights.Tip" ) + " & " + TC.get( "IMS.Classification.Tip" ), 4 );

        tabs.add( new JPanel( ), 5 );
        tabs.setTabComponentAt( 5, infoButton );
        tabs.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( tabs.getSelectedIndex( ) == 5 ) {
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

        //dialogCont.add(ok,BorderLayout.SOUTH);

        JPanel cont = new JPanel( );
        cont.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridy = 0;
        //c.ipady = 70;
        cont.add( tabs, c );
        c.fill = GridBagConstraints.VERTICAL;
        c.gridy = 1;
        c.ipady = 0;
        cont.add( ok, c );
        // Set size and position and show the dialog
        this.getContentPane( ).add( cont );
        setMinimumSize( new Dimension( 490, 520 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );

        // Pop that window when is closed
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosed( WindowEvent e ) {

                Controller.getInstance( ).popWindow( );

            }

        } );

    }

    public void close( ) {

        this.dispose( );
    }
}
