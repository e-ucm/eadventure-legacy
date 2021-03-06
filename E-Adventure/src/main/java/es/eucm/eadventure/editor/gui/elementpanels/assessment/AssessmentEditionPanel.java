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
package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.tools.adaptation.AddRuleTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DeleteRuleTool;
import es.eucm.eadventure.editor.control.tools.adaptation.DuplicateRuleTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.JPositionedDialog;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.GenericOptionPaneDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;

public class AssessmentEditionPanel extends JPanel implements DataControlsPanel, Updateable {

    private static final long serialVersionUID = -6772977555087637745L;

    /**
     * Panel which contains the profiles's type
     */
    private JPanel profileTypePanel;

    /**
     * Panel which contains the data to show/send feedback
     */
    private JPanel feedbackPanel;

    /**
     * Panel which contains all the rules associated with current profile
     */
    private JPanel ruleListPanel;

    /**
     * Button to duplicate selected rule
     */
    private JButton duplicate;

    /**
     * Combo box for profile type
     */
    private JComboBox comboProfile;

    /**
     * Panel which contains the initial state and LMS state of selected rule
     */
    private JPanel rulesInfoPanel;

    private JCheckBox showReportAtEnd;

    private JTextField sendByEmailText;

    private JCheckBox sendByEmailCheck;

    private JCheckBox smtpSSL;

    private JTextField smtpServer;

    private JTextField smtpPort;

    private JTextField smtpUser;

    private JTextField smtpPwd;

    private JPanel smtpConfig;

    private JButton saveSmtpConfig;

    private JButton delete;

    private JTable informationTable;
    

    /**
     * Text area for the documentation.
     */
    private JTextArea conceptTextArea;

    private JTextArea textTextArea;

    /**
     * Data control
     */
    private AssessmentProfileDataControl dataControl;

    private AssessmentRuleDataControl currentRuleDataControl;

    public AssessmentEditionPanel( AssessmentProfileDataControl dataControl ) {

        this.dataControl = dataControl;
        createProfileTypePanel( );
        createFeedbackPanel( );
        createRuleListPanel( );
        rulesInfoPanel = new JPanel( new GridBagLayout( ) );
        createRulesInfoPanel( );
        this.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        this.add( profileTypePanel, c );
        c.gridx = 1;
        this.add( feedbackPanel, c );
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        // c.ipady = 80;
        c.gridy++;
        this.add( ruleListPanel, c );
        c.ipady = 50;
        c.gridy++;
        this.add( rulesInfoPanel, c );
    }

    private void createRulesInfoPanel( ) {

        if( informationTable.getSelectedRow( ) < 0 || informationTable.getSelectedRow( ) >= dataControl.getAssessmentRules( ).size( ) ) {
            rulesInfoPanel.removeAll( );
            JPanel empty = new JPanel( );
            JLabel label = new JLabel( TC.get( "AssessmentProfile.Empty" ) );
            empty.add( label );
            rulesInfoPanel.add( empty );
            rulesInfoPanel.setMinimumSize( new Dimension( 0, 190 ) );
            rulesInfoPanel.updateUI( );
        }
        else {
            rulesInfoPanel.removeAll( );
            // take the current rule data control
            currentRuleDataControl = dataControl.getAssessmentRules( ).get( informationTable.getSelectedRow( ) );
            if( currentRuleDataControl.isTimedRule( ) ) {
                GridBagConstraints c = new GridBagConstraints( );
                c.fill = GridBagConstraints.BOTH;
                c.weightx = 1;
                c.weighty = 1;
                rulesInfoPanel.setLayout( new GridBagLayout( ) );
                rulesInfoPanel.add( new TimedAssessmentRulePanel( currentRuleDataControl, dataControl.isScorm12( ), dataControl.isScorm2004( ) ), c );
                rulesInfoPanel.updateUI( );
            }
            else {
                rulesInfoPanel.removeAll( );
                rulesInfoPanel.setLayout( new TwoColumnsLayout(0.65f) );
                // Create the effect panel with the header and the text to be printed
                JPanel effectPanel = new JPanel( );
                effectPanel.setLayout( new GridBagLayout( ) );
                effectPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentRule.Effect.Text" ) ) );
                
                JPanel conceptPanel = new JPanel( );
                conceptPanel.setLayout( new BorderLayout( ) );
                conceptTextArea = new JTextArea( currentRuleDataControl.getConcept( ), 4, 0 );
                conceptTextArea.setLineWrap( true );
                conceptTextArea.setWrapStyleWord( true );
                conceptTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
                conceptPanel.add( new JScrollPane( conceptTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
                conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentRule.Header" ) )  );
                
                GridBagConstraints c2 = new GridBagConstraints( );
                c2.gridx = 0;
                c2.gridy = 0;
                c2.insets = new Insets( 5, 5, 5, 5 );
                c2.fill = GridBagConstraints.BOTH;
                c2.weightx = 1;
                c2.weighty = 0;
                effectPanel.add( conceptPanel, c2 );
                
                
                JPanel textPanel = new JPanel( );
                textPanel.setLayout( new GridLayout( ) );
                textTextArea = new JTextArea( currentRuleDataControl.getEffectText( 0 ), 4, 0 );
                textTextArea.setLineWrap( true );
                textTextArea.setWrapStyleWord( true );
                textTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
                textPanel.add( new JScrollPane( textTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
                textPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentRule.Body" ) ) );
                
                c2.weighty = 1;
                c2.fill = GridBagConstraints.BOTH;
                c2.gridy = 1;
                
                effectPanel.add( textPanel, c2 );
              
                rulesInfoPanel.add( effectPanel,TwoColumnsLayout.LEFT_COMPONENT );

                AssessmentPropertiesPanel propPanel = new AssessmentPropertiesPanel( this.currentRuleDataControl, this.dataControl.isScorm12( ), this.dataControl.isScorm2004( ) );
                
                rulesInfoPanel.add( propPanel, TwoColumnsLayout.RIGHT_COMPONENT );
                rulesInfoPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentRule.Effect.Title" ) ) );
                rulesInfoPanel.updateUI( );
            }
        }
    }

    private void createRuleListPanel( ) {

        AssessmentRulesTableModel model = new AssessmentRulesTableModel( dataControl );
        informationTable = new JTable( model );
        model.setTable( informationTable );
        informationTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent e ) {

                createRulesInfoPanel( );
                if( informationTable.getSelectedRow( ) == -1 ) {
                    delete.setEnabled( false );
                    duplicate.setEnabled( false );
                }
                else {
                    delete.setEnabled( true );
                    duplicate.setEnabled( true );
                }
            }
        } );

        informationTable.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        JComboBox importance = new JComboBox( new String[] { TC.get( "AssessmentRule.Importance.VeryLow" ), TC.get( "AssessmentRule.Importance.Low" ), TC.get( "AssessmentRule.Importance.Normal" ), TC.get( "AssessmentRule.Importance.High" ), TC.get( "AssessmentRule.Importance.VeryHigh" ) } );
        informationTable.getColumnModel( ).getColumn( 1 ).setCellEditor( new DefaultCellEditor( importance ) );
        informationTable.getColumnModel( ).getColumn( 2 ).setCellEditor( new ConditionsCellRendererEditor( ) );
        informationTable.getColumnModel( ).getColumn( 2 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        informationTable.getColumnModel( ).getColumn( 3 ).setCellEditor( informationTable.getDefaultEditor( Boolean.class ) );
        informationTable.getColumnModel( ).getColumn( 3 ).setCellRenderer(  informationTable.getDefaultRenderer( Boolean.class ) );


        ruleListPanel = new JPanel( );
        ruleListPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentRulesList.ListTitle" ) ) );
        ruleListPanel.setLayout( new BorderLayout( ) );
        ruleListPanel.add( new TableScrollPane( informationTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
        JButton add = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        add.setContentAreaFilled( false );
        add.setMargin( new Insets( 0, 0, 0, 0 ) );
        add.setBorder( BorderFactory.createEmptyBorder( ) );
        add.setFocusable( false );
        add.setToolTipText( TC.get( "AdaptationProfile.AddRule" ) );

        add.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseClicked( MouseEvent evt ) {

                JPopupMenu menu = getAddChildPopupMenu( );
                menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
            }
        } );

        duplicate = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
        duplicate.setContentAreaFilled( false );
        duplicate.setMargin( new Insets( 0, 0, 0, 0 ) );
        duplicate.setBorder( BorderFactory.createEmptyBorder( ) );
        duplicate.setToolTipText( TC.get( "AdaptationProfile.Duplicate" ) );
        duplicate.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                if( Controller.getInstance( ).addTool( new DuplicateRuleTool( dataControl, Controller.ASSESSMENT_RULE, informationTable.getSelectedRow( ) ) ) ) {
                    ( (AssessmentRulesTableModel) informationTable.getModel( ) ).fireTableDataChanged( );
                    informationTable.changeSelection( dataControl.getAssessmentRules( ).size( ) - 1, 0, false, false );
                }
            }
        } );
        duplicate.setEnabled( false );

        delete = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        delete.setContentAreaFilled( false );
        delete.setMargin( new Insets( 0, 0, 0, 0 ) );
        delete.setBorder( BorderFactory.createEmptyBorder( ) );
        delete.setFocusable( false );
        delete.setToolTipText( TC.get( "AdaptationProfile.DeleteRule" ) );
        delete.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                if( Controller.getInstance( ).addTool( new DeleteRuleTool( dataControl, Controller.ASSESSMENT_RULE, informationTable.getSelectedRow( ) ) ) ) {
                    informationTable.clearSelection( );
                    ( (AssessmentRulesTableModel) informationTable.getModel( ) ).fireTableDataChanged( );
                    createRulesInfoPanel( );
                }
            }

        } );
        delete.setEnabled( false );
        JPanel buttonsPanel = new JPanel( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( add, c );
        c.gridy = 1;
        buttonsPanel.add( duplicate, c );
        c.gridy = 3;
        buttonsPanel.add( delete, c );
        c.gridy = 2;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add( new JFiller( ), c );
        ruleListPanel.add( buttonsPanel, BorderLayout.EAST );
    }

    public void createProfileTypePanel( ) {

        profileTypePanel = new JPanel( new BorderLayout( ) );
        profileTypePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentRule.ProfileType" ) ) );

        String[] options = new String[] { TC.get( "AdaptationRulesList.Scorm2004" ), TC.get( "AdaptationRulesList.Scorm12" ), TC.get( "AdaptationRulesList.Normal" ) };
        comboProfile = new JComboBox( options );
        if( dataControl.isScorm12( ) )
            comboProfile.setSelectedIndex( 1 );
        else if( dataControl.isScorm2004( ) )
            comboProfile.setSelectedIndex( 0 );
        else
            comboProfile.setSelectedIndex( 2 );

        comboProfile.addActionListener( new ComboListener( comboProfile.getSelectedIndex( ) ) );

        profileTypePanel.add( comboProfile, BorderLayout.CENTER );
    }

    private class ComboListener implements ActionListener {

        private int pastSelection;

        public ComboListener( int pastSelection ) {

            this.pastSelection = pastSelection;
        }

        //@Override
        public void actionPerformed( ActionEvent e ) {

            JComboBox combo = ( (JComboBox) e.getSource( ) );
            if( pastSelection != combo.getSelectedIndex( ) ) {
                informationTable.clearSelection( );
                if( combo.getSelectedIndex( ) == 0 ) {
                    dataControl.changeToScorm2004Profile( );
                }
                else if( combo.getSelectedIndex( ) == 1 ) {
                    dataControl.changeToScorm12Profile( );
                }
                else if( combo.getSelectedIndex( ) == 2 ) {
                    dataControl.changeToNormalProfile( );
                }
                pastSelection = combo.getSelectedIndex( );
            }

        }

    }

    public void createFeedbackPanel( ) {

        feedbackPanel = createFeedbackPanel( dataControl );
    }

    private JPanel createFeedbackPanel( AssessmentProfileDataControl assRulesListDataControl ) {

        JPanel feedbackPanel = new JPanel( );

        feedbackPanel.setLayout( new GridBagLayout( ) );
        feedbackPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "AssessmentProfile.Feedback.Title" ) ) );

        GridBagConstraints c2 = new GridBagConstraints( );

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1.0;
        c2.gridy = 0;

        showReportAtEnd = new JCheckBox( TC.get( "AssessmentProfile.Feedback.ShowReportAtEnd" ) );
        if( assRulesListDataControl.isShowReportAtEnd( ) )
            showReportAtEnd.setSelected( true );
        showReportAtEnd.addActionListener( new ShowReportAtEndActionListener( ) );
        feedbackPanel.add( showReportAtEnd, c2 );

        sendByEmailCheck = new JCheckBox( TC.get( "AssessmentProfile.Feedback.SendByEmail" ) );
        sendByEmailCheck.setEnabled( dataControl.isShowReportAtEnd( ) );
        sendByEmailCheck.setSelected( dataControl.isShowReportAtEnd( ) && dataControl.isSendByEmail( ) );
        sendByEmailCheck.addActionListener( new SendByEmailActionListener( ) );

        sendByEmailText = new JTextField( );
        sendByEmailText.setEnabled( dataControl.isShowReportAtEnd( ) && dataControl.isSendByEmail( ) );
        if( dataControl.isSendByEmail( ) && dataControl.isShowReportAtEnd( ) )
            sendByEmailText.setText( dataControl.getEmail( ) );
        sendByEmailText.addKeyListener( new KeyListener( ) {

            public void keyPressed( KeyEvent arg0 ) {

            }

            public void keyReleased( KeyEvent arg0 ) {

                dataControl.setEmail( sendByEmailText.getText( ) );
            }

            public void keyTyped( KeyEvent arg0 ) {

            }
        } );

        JPanel tempPanelemail = new JPanel( );
        tempPanelemail.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        tempPanelemail.add( sendByEmailCheck, c );
        c.gridx++;
        c.weightx = 3.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        tempPanelemail.add( sendByEmailText, c );
        c.gridx++;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        JButton expand = new JButton( TC.get( "AssessmentProfile.Feedback.EditSMTPConfiguration" ) );
        expand.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                new SMTPDialog( );
            }
        } );
        tempPanelemail.add( expand, c );

        c2.gridy++;
        feedbackPanel.add( tempPanelemail, c2 );

        smtpConfig = createSmtpConfigPanel( );
        if( !dataControl.isSendByEmail( ) ) {
            smtpSSL.setEnabled( false );
            smtpServer.setEnabled( false );
            smtpPort.setEnabled( false );
            smtpUser.setEnabled( false );
            smtpPwd.setEnabled( false );
        }

        return feedbackPanel;
    }

    private JPanel createSmtpConfigPanel( ) {

        JPanel tempPanel = new JPanel( );
        tempPanel.setLayout( new BorderLayout( ) );

        JPanel panel = new JPanel( );
        panel.setLayout( new GridLayout( 5, 2 ) );

        this.smtpSSL = new JCheckBox( "Use SSL" );
        panel.add( smtpSSL );
        panel.add( new JLabel( "" ) );

        smtpServer = new JTextField( );
        panel.add( new JLabel( "SMTP SERVER" ) );
        panel.add( smtpServer );

        smtpPort = new JTextField( );
        panel.add( new JLabel( "SMTP PORT" ) );
        panel.add( smtpPort );

        smtpUser = new JTextField( );
        panel.add( new JLabel( "SMTP USER" ) );
        panel.add( smtpUser );

        smtpPwd = new JPasswordField( );
        ( (JPasswordField) smtpPwd ).setEchoChar( '*' );
        panel.add( new JLabel( "SMTP PASSWORD" ) );
        panel.add( smtpPwd );

        if( dataControl.isSendByEmail( ) ) {
            smtpSSL.setSelected( dataControl.isSmtpSSL( ) );
            smtpServer.setText( dataControl.getSmtpServer( ) );
            smtpPort.setText( dataControl.getSmtpPort( ) );
            smtpUser.setText( dataControl.getSmtpUser( ) );
            smtpPwd.setText( dataControl.getSmtpPwd( ) );
        }

        tempPanel.add( panel, BorderLayout.CENTER );

        saveSmtpConfig = new JButton(TC.get( "Assessment.SMTPConfig.save" ));
        saveSmtpConfig.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                if( /* validateIP(smtpIP.getText()) && */validatePort( smtpPort.getText( ) ) ) {
                    dataControl.setSmtpServer( smtpServer.getText( ) );
                    dataControl.setSmtpPort( smtpPort.getText( ) );
                    dataControl.setSmtpPwd( smtpPwd.getText( ) );
                    dataControl.setSmtpSSL( smtpSSL.isSelected( ) );
                    dataControl.setSmtpUser( smtpUser.getText( ) );
                }
                else {
                    GenericOptionPaneDialog.showMessageDialog( null, TC.get( "Assessment.SMTPConfig.InvalidData" ), TC.get( "Assessment.SMTPConfig.InvalidData" ), JOptionPane.ERROR_MESSAGE );

                }
            }

            private boolean validatePort( String text ) {

                try {
                    int port = Integer.parseInt( text );
                    if( port > 0 && port < 16000 )
                        return true;
                }
                catch( Exception e ) {
                }
                return false;
            }

        } );
        tempPanel.add( saveSmtpConfig, BorderLayout.SOUTH );

        return tempPanel;
    }

    private class SMTPDialog extends JPositionedDialog {

        private static final long serialVersionUID = -1152972955243725648L;

        public SMTPDialog( ) {

            super( Controller.getInstance( ).peekWindow( ), TC.get( "AssessmentProfile.Feedback.EditSMTPConfiguration" ), Dialog.ModalityType.TOOLKIT_MODAL );
            Controller.getInstance( ).pushWindow( this );

            JPanel tempPanel = new JPanel( );
            tempPanel.setLayout( new BorderLayout( ) );
            // tempPanel.add(sendByEmailPanel, BorderLayout.CENTER);
            tempPanel.add( smtpConfig );
            this.add( tempPanel );

            addWindowListener( new WindowAdapter( ) {

                @Override
                public void windowClosed( WindowEvent e ) {

                    Controller.getInstance( ).popWindow( );
                }
            } );

            this.setSize( new Dimension( 250, 200 ) );
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
            //setResizable( false );
            setVisible( true );
        }
    }

    /**
     * Listener for the text area. It checks the value of the area and updates
     * the documentation.
     */
    private class DocumentationTextAreaChangesListener implements DocumentListener {

        public void changedUpdate( DocumentEvent arg0 ) {

            // Do nothing
        }

        public void insertUpdate( DocumentEvent arg0 ) {

            // Set the new content
            if( arg0.getDocument( ) == conceptTextArea.getDocument( ) ) {
                currentRuleDataControl.setConcept( conceptTextArea.getText( ) );
            }

            else if( arg0.getDocument( ) == textTextArea.getDocument( ) ) {
                currentRuleDataControl.setEffectText( -1, textTextArea.getText( ) );
            }

        }

        public void removeUpdate( DocumentEvent arg0 ) {

            // Set the new content

            if( arg0.getDocument( ) == conceptTextArea.getDocument( ) ) {
                currentRuleDataControl.setConcept( conceptTextArea.getText( ) );
            }

            else if( arg0.getDocument( ) == textTextArea.getDocument( ) ) {
                currentRuleDataControl.setEffectText( -1, textTextArea.getText( ) );
            }

        }
    }

    /**
     * Returns a popup menu with the add operations.
     * 
     * @return Popup menu with child adding operations
     */
    public JPopupMenu getAddChildPopupMenu( ) {

        JPopupMenu addChildPopupMenu = new JPopupMenu( );

        JMenuItem addChildMenuItem = new JMenuItem( TC.get( "AdaptationProfile.AddRule" ) );
        addChildMenuItem.setEnabled( true );
        addChildMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                if( Controller.getInstance( ).addTool( new AddRuleTool( dataControl, Controller.ASSESSMENT_RULE ) ) ) {
                    ( (AssessmentRulesTableModel) informationTable.getModel( ) ).fireTableDataChanged( );
                    informationTable.changeSelection( dataControl.getAssessmentRules( ).size( ) - 1, 0, false, false );
                }
            }
        } );
        addChildPopupMenu.add( addChildMenuItem );

        addChildMenuItem = new JMenuItem( TC.get( "AdaptationProfile.AddTimedRule" ) );
        addChildMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                if( Controller.getInstance( ).addTool( new AddRuleTool( dataControl, Controller.TIMED_ASSESSMENT_RULE ) ) ) {
                    ( (AssessmentRulesTableModel) informationTable.getModel( ) ).fireTableDataChanged( );
                    informationTable.changeSelection( dataControl.getAssessmentRules( ).size( ) - 1, 0, false, false );
                }
            }

        } );
        addChildPopupMenu.add( addChildMenuItem );

        return addChildPopupMenu;
    }

    
    private void sendByEmailCheck(){
	
	sendByEmailText.setEnabled( sendByEmailCheck.isSelected( ) );
        smtpSSL.setEnabled( sendByEmailCheck.isSelected( ) );
        smtpServer.setEnabled( sendByEmailCheck.isSelected( ) );
        smtpPort.setEnabled( sendByEmailCheck.isSelected( ) );
        smtpUser.setEnabled( sendByEmailCheck.isSelected( ) );
        smtpPwd.setEnabled( sendByEmailCheck.isSelected( ) );
	
        if( sendByEmailCheck.isSelected( ) ) {
            sendByEmailText.setText( dataControl.getEmail( ) );
            smtpSSL.setSelected( dataControl.isSmtpSSL( ) );
            smtpServer.setText( dataControl.getSmtpServer( ) );
            smtpPort.setText( dataControl.getSmtpPort( ) );
            smtpUser.setText( dataControl.getSmtpUser( ) );
            smtpPwd.setText( dataControl.getSmtpPwd( ) );
        }
        else {
            sendByEmailText.setText( "" );
            smtpSSL.setSelected( false );
            smtpServer.setText( "" );
            smtpPort.setText( "" );
            smtpUser.setText( "" );
            smtpPwd.setText( "" );
        }
    }
    
    private class SendByEmailActionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            //dataControl.setSendByEmail( sendByEmailCheck.isSelected( ) );
            
            sendByEmailCheck( );
            dataControl.setSendByEmail( sendByEmailCheck.isSelected( ) );
        }
    }

    private void showReportCheck(){
	boolean is =showReportAtEnd.isSelected( );
	//if( dataControl.isShowReportAtEnd( ) != showReportAtEnd.isSelected( ) ) {
	if( showReportAtEnd.isSelected( ) ) {
            sendByEmailCheck.setEnabled( true );
            sendByEmailCheck.setSelected( dataControl.isSendByEmail( ) );
            sendByEmailText.setEnabled( dataControl.isSendByEmail( ) );
            smtpSSL.setEnabled( dataControl.isSendByEmail( ) );
            smtpServer.setEnabled( dataControl.isSendByEmail( ) );
            smtpPort.setEnabled( dataControl.isSendByEmail( ) );
            smtpUser.setEnabled( dataControl.isSendByEmail( ) );
            smtpPwd.setEnabled( dataControl.isSendByEmail( ) );
            if( dataControl.isSendByEmail( ) ) {
                sendByEmailText.setText( dataControl.getEmail( ) );
                smtpSSL.setSelected( dataControl.isSmtpSSL( ) );
                smtpServer.setText( dataControl.getSmtpServer( ) );
                smtpPort.setText( dataControl.getSmtpPort( ) );
                smtpUser.setText( dataControl.getSmtpUser( ) );
                smtpPwd.setText( dataControl.getSmtpPwd( ) );
            }
        }
        else {
            sendByEmailCheck.setEnabled( false );
            sendByEmailCheck.setSelected( false );
            sendByEmailText.setEnabled( false );
            sendByEmailText.setText( "" );
            smtpSSL.setSelected( false );
            smtpServer.setText( "" );
            smtpPort.setText( "" );
            smtpUser.setText( "" );
            smtpPwd.setText( "" );

        }
   // }
    
    }
    
    private class ShowReportAtEndActionListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            showReportCheck( );
            dataControl.setShowReportAtEnd( showReportAtEnd.isSelected( ) );
        }
    }

    public boolean updateFields( ) {

        int selected = informationTable.getSelectedRow( );
        // the call is not spread 
        //if( rulesInfoPanel != null && rulesInfoPanel instanceof Updateable )
          //  ( (Updateable) rulesInfoPanel ).updateFields( );     
        if( informationTable.getCellEditor( ) != null ) {
            informationTable.getCellEditor( ).cancelCellEditing( );
        }
        ( (AbstractTableModel) informationTable.getModel( ) ).fireTableDataChanged( );
        
        // update the combobox
        if( dataControl.isScorm12( ) )
            comboProfile.setSelectedIndex( 1 );
        else if( dataControl.isScorm2004( ) )
            comboProfile.setSelectedIndex( 0 );
        else
            comboProfile.setSelectedIndex( 2 );
        
        showReportAtEnd.setSelected(dataControl.isShowReportAtEnd());
        showReportCheck( );
        sendByEmailCheck.setSelected(dataControl.isSendByEmail());
        sendByEmailCheck( );
        
        informationTable.getSelectionModel( ).setSelectionInterval( selected, selected );
       
       
        return true;
    }

    public void setSelectedItem( List<Searchable> path ) {

        if( path.size( ) > 0 ) {
            for( int i = 0; i < dataControl.getDataControls( ).size( ); i++ ) {
                if( dataControl.getDataControls( ).get( i ) == path.get( path.size( ) - 1 ) )
                    informationTable.changeSelection( i, i, false, false );
            }
        }
    }
}
