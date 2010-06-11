/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.metadatadialog.ims;

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
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

public class IMSDialog extends JDialog {

    private static final String helpPath = "metadata/IMSLOM.html";

    private IMSDataControl dataControl;

    private JTabbedPane tabs;

    private JButton ok;

    private int currentTab;

    public IMSDialog( IMSDataControl dataControl ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "LOM.Title" ), Dialog.ModalityType.TOOLKIT_MODAL );
        this.dataControl = dataControl;

        tabs = new JTabbedPane( );

        JButton infoButton = new JButton( new ImageIcon( "img/icons/information.png" ) );
        infoButton.setContentAreaFilled( false );
        infoButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        infoButton.setFocusable( false );
        infoButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new HelpDialog( helpPath );
            }
        } );

        tabs.insertTab( TC.get( "LOM.General.Tab" ), null, new IMSGeneralPanel( dataControl.getGeneral( ) ), TC.get( "LOM.General.Tip" ), 0 );
        tabs.insertTab( TC.get( "LOM.LifeCycle.Tab" ) + " & " + TC.get( "LOM.Technical.Tab" ) + " & " + TC.get( "IMS.Meta.Tab" ), null, new IMSLifeCycleTechnicalAndMetaPanel( dataControl.getLifeCycle( ), dataControl.getTechnical( ), dataControl.getMetametadata( ) ), TC.get( "LOM.LifeCycle.Tip" ) + " & " + TC.get( "LOM.Technical.Tip" ) + " & " + TC.get( "IMS.Meta.Tip" ), 1 );
        tabs.insertTab( TC.get( "LOM.Educational.Tab" ), null, new IMSEducationalPanel( dataControl.getEducational( ) ), TC.get( "LOM.Educational.Tip" ), 2 );
        tabs.insertTab( TC.get( "IMS.Rights.Tab" ) + " & " + TC.get( "IMS.Classification.Tab" ), null, new IMSRightsAndClassificationPanel( dataControl.getRights( ), dataControl.getClassification( ) ), TC.get( "IMS.Rights.Tip" ) + " & " + TC.get( "IMS.Classification.Tip" ), 3 );
        tabs.add( new JPanel( ), 4 );
        tabs.setTabComponentAt( 4, infoButton );
        tabs.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( tabs.getSelectedIndex( ) == 4 ) {
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
        this.getContentPane( ).add( cont );
        setMinimumSize( new Dimension( 490, 450 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );

    }

    public void close( ) {

        this.dispose( );
    }

}
