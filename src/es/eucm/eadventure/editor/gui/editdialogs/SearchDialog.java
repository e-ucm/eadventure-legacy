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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class SearchDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Controller controller;

    private JTextField textField;

    private JCheckBox caseSensitive;

    private JCheckBox fullMatch;

    private JTable table;

    private DefaultTableModel dtm;

    private List<Searchable> dataControls;

    public SearchDialog( ) {

        controller = Controller.getInstance( );
        this.setLayout( new BorderLayout( ) );
        this.setTitle( TextConstants.getText( "Search.DialogTitle" ) );

        JPanel inputPanel = new JPanel( );
        textField = new JTextField( 15 );
        textField.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                search( );
            }
        } );
        inputPanel.add( textField );
        caseSensitive = new JCheckBox( TextConstants.getText( "Search.CaseSensitive" ) );
        inputPanel.add( caseSensitive );
        fullMatch = new JCheckBox( TextConstants.getText( "Search.FullMatch" ) );
        inputPanel.add( fullMatch );
        JButton search = new JButton( TextConstants.getText( "Search.Search" ) );
        search.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                search( );
            }
        } );
        inputPanel.add( search );

        this.add( inputPanel, BorderLayout.NORTH );

        dataControls = new ArrayList<Searchable>( );
        dtm = new DefaultTableModel( );
        dtm.setColumnCount( 2 );
        String[] ids = { TextConstants.getText( "Search.Where" ), TextConstants.getText( "Search.Places" ) };
        dtm.setColumnIdentifiers( ids );
        //table = new JTable(dtm);
        table = new JTable( dtm ) {

            private static final long serialVersionUID = -4886780250056223726L;

            @Override
            public boolean isCellEditable( int rowIndex, int colIndex ) {

                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane( table );
        ListSelectionModel listMod = table.getSelectionModel( );
        listMod.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        listMod.addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                if( !arg0.getValueIsAdjusting( ) && table.getSelectedRow( ) != -1 )
                    StructureControl.getInstance( ).changeDataControl( dataControls.get( table.getSelectedRow( ) ) );
            }
        } );
        table.setFillsViewportHeight( true );

        this.add( scrollPane, BorderLayout.CENTER );

        this.setSize( 460, 600 );

        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( screenSize.width - 460, ( screenSize.height - 600 ) / 2 );

        this.setVisible( true );
        Controller.getInstance( ).pushWindow( this );
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosing( WindowEvent e ) {

                Controller.getInstance( ).popWindow( );
            }
        } );
    }

    protected void search( ) {

        String text = textField.getText( );
        if( text != null && !text.equals( "" ) ) {
            table.clearSelection( );
            while( dtm.getRowCount( ) > 0 ) {
                dtm.removeRow( 0 );
            }
            dataControls.clear( );

            boolean caseSensitive = this.caseSensitive.isSelected( );
            boolean fullMatch = this.fullMatch.isSelected( );

            HashMap<Searchable, List<String>> result = controller.getSelectedChapterDataControl( ).search( text, caseSensitive, fullMatch );

            for( Searchable dc : result.keySet( ) ) {
                String where = "";
                for( String s : result.get( dc ) )
                    where += ( s + "|" );
                if( where.length( ) > 1 )
                    where = where.substring( 0, where.length( ) - 1 );
                Object[] row = new Object[] { dc.getClass( ).getSimpleName( ).replace( "DataControl", "" ), where };
                dtm.addRow( row );
                dataControls.add( dc );
            }
            dtm.fireTableDataChanged( );
        }
    }
}
