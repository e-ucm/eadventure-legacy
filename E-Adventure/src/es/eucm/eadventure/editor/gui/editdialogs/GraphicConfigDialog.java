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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

/**
 * Dialog to let the user select different graphic options (fullscreen,
 * windowed, etc)
 * 
 * @author Eugenio Jorge Marchiori
 */
public class GraphicConfigDialog extends JDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the option selected;
     */
    private int optionSelected;

    /**
     * Radio button for the "Windowed" option.
     */
    private JRadioButton windowedRadioButton;

    /**
     * Radio button for the "Black Background" option.
     */
    private JRadioButton blackBkgRadioButton;

    /**
     * Radio button for the "Fullscreen" option.
     */
    private JRadioButton fullscreenRadioButton;

    /**
     * Constructor.
     * 
     * @param optionSelected
     *            GUI style active
     */
    public GraphicConfigDialog( int optionSelected ) {

        // Set the values
        super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "GraphicConfig.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
        if( optionSelected != -1 )
            this.optionSelected = optionSelected;
        else
            this.optionSelected = DescriptorData.GRAPHICS_WINDOWED;

        // Panel with the report options
        JPanel graphicConfigPanel = new JPanel( );
        graphicConfigPanel.setLayout( new GridBagLayout( ) );
        graphicConfigPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GraphicConfig.Title" ) ) );

        // Windowed radio button
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 0, 0, 0, 0 );
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.BOTH;
        windowedRadioButton = new JRadioButton( TextConstants.getText( "GraphicConfig.Windowed" ) );
        graphicConfigPanel.add( windowedRadioButton, c );

        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 1;
        JTextPane xmlReportInfo = new JTextPane( );
        xmlReportInfo.setEditable( false );
        xmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        xmlReportInfo.setText( TextConstants.getText( "GraphicConfig.WindowedDescription" ) );
        graphicConfigPanel.add( xmlReportInfo, c );

        c.gridy = 1;
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.3;
        Icon logo = new ImageIcon( "img/graphicconf1.png" );
        JLabel label = new JLabel( logo );
        graphicConfigPanel.add( label, c );

        c.gridy = 2;
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        blackBkgRadioButton = new JRadioButton( TextConstants.getText( "GraphicConfig.BlackBkg" ) );
        blackBkgRadioButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

            }
        } );
        graphicConfigPanel.add( blackBkgRadioButton, c );

        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        JTextPane htmlReportInfo = new JTextPane( );
        htmlReportInfo.setEditable( false );
        htmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        htmlReportInfo.setText( TextConstants.getText( "GraphicConfig.BlackBkgDescription" ) );
        graphicConfigPanel.add( htmlReportInfo, c );

        c.gridy = 3;
        c.gridx = 1;
        c.weightx = 0.3;
        c.anchor = GridBagConstraints.LINE_END;
        logo = new ImageIcon( "img/graphicconf2.png" );
        label = new JLabel( logo );
        graphicConfigPanel.add( label, c );

        c.gridx = 0;
        c.gridy = 4;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        fullscreenRadioButton = new JRadioButton( TextConstants.getText( "GraphicConfig.Fullscreen" ) );
        fullscreenRadioButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

            }
        } );
        graphicConfigPanel.add( fullscreenRadioButton, c );

        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        htmlReportInfo = new JTextPane( );
        htmlReportInfo.setEditable( false );
        htmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        htmlReportInfo.setText( TextConstants.getText( "GraphicConfig.FullscreenDescription" ) );
        graphicConfigPanel.add( htmlReportInfo, c );

        c.gridy = 5;
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0.3;
        logo = new ImageIcon( "img/graphicconf3.png" );
        label = new JLabel( logo );
        graphicConfigPanel.add( label, c );

        // Panel with the buttons
        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new FlowLayout( ) );
        JButton btnLoad = new JButton( TextConstants.getText( "GeneralText.OK" ) );
        btnLoad.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                setVisible( false );
                dispose( );
            }
        } );
        buttonsPanel.add( btnLoad );
        JButton btnCancel = new JButton( TextConstants.getText( "GeneralText.Cancel" ) );
        btnCancel.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                invalidateOptionSelected( );
                setVisible( false );
                dispose( );
            }
        } );
        buttonsPanel.add( btnCancel );

        // Add the principal and the buttons panel
        add( graphicConfigPanel, BorderLayout.CENTER );
        add( buttonsPanel, BorderLayout.SOUTH );

        // Configure the radio buttons
        windowedRadioButton.addActionListener( new OptionChangedListener( ) );
        blackBkgRadioButton.addActionListener( new OptionChangedListener( ) );
        fullscreenRadioButton.addActionListener( new OptionChangedListener( ) );
        ButtonGroup guiButtonGroup = new ButtonGroup( );
        guiButtonGroup.add( windowedRadioButton );
        guiButtonGroup.add( blackBkgRadioButton );
        guiButtonGroup.add( fullscreenRadioButton );
        switch( this.optionSelected ) {
            case DescriptorData.GRAPHICS_WINDOWED:
                windowedRadioButton.setSelected( true );
                break;
            case DescriptorData.GRAPHICS_BLACKBKG:
                blackBkgRadioButton.setSelected( true );
                break;
            case DescriptorData.GRAPHICS_FULLSCREEN:
                fullscreenRadioButton.setSelected( true );
                break;
            default:
        }
        // If the dialog is being closed, disable the selection option
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosing( WindowEvent e ) {

                invalidateOptionSelected( );
                setVisible( false );
                dispose( );
            }
        } );

        // Set size and position and show the dialog
        setSize( 450, 410 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setResizable( false );
        setVisible( true );
    }

    /**
     * Returns the option that has been selected in the dialog.
     * 
     * @return Option selected: 0 for traditional, 1 for contextual, -1 if the
     *         dialog was closed or the "Cancel" button pressed
     */
    public int getOptionSelected( ) {

        return optionSelected;
    }

    /**
     * Invalidates the option selected (when the "Cancel" button is pressed or
     * the dialog is closed).
     */
    private void invalidateOptionSelected( ) {

        optionSelected = -1;
    }

    /**
     * Listener for the radio buttons.
     */
    private class OptionChangedListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            if( e.getSource( ).equals( windowedRadioButton ) )
                optionSelected = DescriptorData.GRAPHICS_WINDOWED;
            else if( e.getSource( ).equals( blackBkgRadioButton ) )
                optionSelected = DescriptorData.GRAPHICS_BLACKBKG;
            else if( e.getSource( ).equals( fullscreenRadioButton ) )
                optionSelected = DescriptorData.GRAPHICS_FULLSCREEN;
        }
    }
}
