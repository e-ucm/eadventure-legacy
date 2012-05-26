/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESDateDataControl;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.gui.auxiliar.JPositionedDialog;

public class LOMLifeCycleDateDialog extends JPositionedDialog {

    private JTextField years;

    private JTextField months;

    private JTextField days;

    private JTextField hours;

    private JTextField minutes;

    private JTextField seconds;

    private JTextField milisec;

    private JTextField timeZone;

    private JTextField description;

    private String dateTimeValue;

    private String descriptionValue;

    private LOMESDateDataControl transformer;

    public LOMLifeCycleDateDialog( LOMESLifeCycleDate composeData ) {

        super( Controller.getInstance( ).peekWindow( ), composeData.getTitle( ), Dialog.ModalityType.APPLICATION_MODAL );

        Controller.getInstance( ).pushWindow( this );

        transformer = new LOMESDateDataControl( composeData );

        dateTimeValue = composeData.getDateTime( );
        descriptionValue = composeData.getDescription( ).getValue( 0 );

        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 2, 2, 2, 2 );
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;

        JPanel dateTimePanel = new JPanel( new GridBagLayout( ) );
        // text field for years
        c.gridy = 0;
        years = new JTextField( transformer.getYears( ) );
        years.getDocument( ).addDocumentListener( new TextFieldListener( years ) );
        years.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Years" ) ) );
        dateTimePanel.add( years, c );

        // text field for months
        c.gridy = 1;
        months = new JTextField( transformer.getMonths( ) );
        months.getDocument( ).addDocumentListener( new TextFieldListener( months ) );
        months.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Months" ) ) );
        dateTimePanel.add( months, c );

        // text field for days
        c.gridy = 2;
        days = new JTextField( transformer.getDays( ) );
        days.getDocument( ).addDocumentListener( new TextFieldListener( days ) );
        days.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Days" ) ) );
        dateTimePanel.add( days, c );

        // text field for hours
        c.gridy = 3;
        hours = new JTextField( transformer.getHours( ) );
        hours.getDocument( ).addDocumentListener( new TextFieldListener( hours ) );
        hours.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Hours" ) ) );
        dateTimePanel.add( hours, c );

        // text field for minutes
        c.gridy = 4;
        minutes = new JTextField( transformer.getMinutes( ) );
        minutes.getDocument( ).addDocumentListener( new TextFieldListener( minutes ) );
        minutes.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Minutes" ) ) );
        dateTimePanel.add( minutes, c );

        // text field for seconds
        c.gridy = 5;
        seconds = new JTextField( transformer.getSeconds( ) );
        seconds.getDocument( ).addDocumentListener( new TextFieldListener( seconds ) );
        seconds.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Seconds" ) ) );
        dateTimePanel.add( seconds, c );

        // text field for miliseconds
        c.gridy = 6;
        milisec = new JTextField( transformer.getMilisec( ) );
        milisec.getDocument( ).addDocumentListener( new TextFieldListener( milisec ) );
        milisec.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.Miliseconds" ) ) );
        dateTimePanel.add( milisec, c );

        // text field for timeZone
        c.gridy = 7;
        timeZone = new JTextField( transformer.getTimeZone( ) );
        timeZone.getDocument( ).addDocumentListener( new TextFieldListener( timeZone ) );
        timeZone.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Date.TimeZone" ) ) );
        dateTimePanel.add( timeZone, c );

        dateTimePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.GeneralId.DateName" ) ) );

        JPanel descriptionPanel = new JPanel( new GridBagLayout( ) );
        description = new JTextField( composeData.getDescription( ).getValue( 0 ) );
        description.getDocument( ).addDocumentListener( new TextFieldListener( description ) );
        descriptionPanel.add( description, c );
        descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.GeneralId.DescriptionName" ) ) );

        JPanel buttonPanel = new JPanel( new GridBagLayout( ) );
        c = new GridBagConstraints( );
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        JButton ok = new JButton( "OK" );
        ok.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                dispose( );

            }

        } );
        c.gridx = 0;
        buttonPanel.add( ok, c );

        JButton info = new JButton( "Info" );
        info.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                showInfo( );

            }

        } );
        c.gridx = 1;
        buttonPanel.add( info, c );

        JPanel mainPanel = new JPanel( new GridBagLayout( ) );
        c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        mainPanel.add( dateTimePanel, c );
        c.gridy = 1;
        mainPanel.add( descriptionPanel, c );
        c.gridy = 2;
        mainPanel.add( buttonPanel, c );
        //this.getContentPane().setLayout(new GridLayout(0,2));
        this.getContentPane( ).add( mainPanel );
        ///this.getContentPane().add(buttonPanel);

        this.setSize( new Dimension( 250, 500 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        //setResizable( false );
        setVisible( true );

        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosed( WindowEvent e ) {

                Controller.getInstance( ).popWindow( );

            }

        } );

    }

    private void showInfo( ) {

        JOptionPane.showMessageDialog( this, TC.get( "LOMES.Date.Info" ), "Info", JOptionPane.INFORMATION_MESSAGE );
    }

    private class TextFieldListener implements DocumentListener {

        private JTextField textField;

        public TextFieldListener( JTextField textField ) {

            this.textField = textField;
        }

        public void changedUpdate( DocumentEvent e ) {

            //Do nothing
        }

        public void insertUpdate( DocumentEvent e ) {

            if( textField == years ) {
                transformer.setYears( textField.getText( ) );
            }
            else if( textField == months ) {
                transformer.setMonths( textField.getText( ) );
            }
            else if( textField == days ) {
                transformer.setDays( textField.getText( ) );
            }
            else if( textField == hours ) {
                transformer.setHours( textField.getText( ) );
            }
            else if( textField == minutes ) {
                transformer.setMinutes( textField.getText( ) );
            }
            else if( textField == seconds ) {
                transformer.setSeconds( textField.getText( ) );
            }
            else if( textField == milisec ) {
                transformer.setMiliSeconds( textField.getText( ) );
            }
            else if( textField == timeZone ) {
                transformer.setTimeZone( textField.getText( ) );
            }
            else if( textField == description ) {
                descriptionValue = textField.getText( );
            }

        }

        public void removeUpdate( DocumentEvent e ) {

            insertUpdate( e );
        }

    }

    /**
     * @return the dateTimeValue
     */
    public String getDateTimeValue( ) {

        dateTimeValue = transformer.toString( );
        return dateTimeValue;
    }

    /**
     * @return the descriptionValue
     */
    public String getDescriptionValue( ) {

        return descriptionValue;
    }
}
