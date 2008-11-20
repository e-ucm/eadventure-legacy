package es.eucm.eadventure.engine.assessment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.gui.TextConstants;

/**
 * Dialog showing the options to create a report dialog
 * from the triggered assessment rules.
 */
public class ReportDialog extends JDialog {

    /**
     * String values for the different importance values (for printing)
     */
    public static final String[] IMPORTANCE_VALUES_PRINT = {
        TextConstants.getText( "Report.Importance.VeryLow" ),
        TextConstants.getText( "Report.Importance.Low" ),
        TextConstants.getText( "Report.Importance.Normal" ),
        TextConstants.getText( "Report.Importance.High" ),
        TextConstants.getText( "Report.Importance.VeryHigh" )
    };
	
    /**
     * Width of the dialog.
     */
    private static final int WINDOW_WIDTH = 340;
    
    /**
     * Height of the dialog.
     */
    private static final int WINDOW_HEIGHT = 350;
    
    /**
     * Link to the assessment engine.
     */
    private AssessmentEngine assessmentEngine;
    
    /**
     * Id of the adventure.
     */
    private String adventureID;
    
    /**
     * Text field with the current folder.
     */
    private JTextField txtCurrentDir;

    /**
     * Check box to generate XML report.
     */
    private JCheckBox chkXMLReport;
    
    /**
     * Check box to generate HTML report.
     */
    private JCheckBox chkHTMLReport;
    
    /**
     * Label of the importance field.
     */
    private JLabel lblImportanceFilter;
    
    /**
     * Combo box with the importance values.
     */
    private JComboBox cmbImportanceFilter;
    
    /**
     * Constructor.
     * @param owner Owner frame
     * @param assessmentEngine Assessment engine
     * @param adventureID Id of the adventure
     */
    public ReportDialog( Frame owner, AssessmentEngine assessmentEngine, String adventureID ) {
        
        // Set the values
        super( owner, TextConstants.getText( "Report.Title" ), true );
        this.assessmentEngine = assessmentEngine;
        this.adventureID = adventureID;
        
        // Set size and position
        setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - WINDOW_WIDTH ) / 2, ( screenSize.height - WINDOW_HEIGHT ) / 2 );
        setResizable( false );
        
        // Create panel for the current folder
        JPanel currentDirectoryPanel = new JPanel( );
        currentDirectoryPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 4, 10, 2, 10 );
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        currentDirectoryPanel.add( new JLabel( TextConstants.getText( "Report.SelectFolder" ) ), c );
        
        c.insets = new Insets( 0, 10, 10, 3 );
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 8;
        c.fill = GridBagConstraints.HORIZONTAL;
        try {
            txtCurrentDir = new JTextField( new File( "." ).getCanonicalPath( ) );
        } catch( IOException e ) {
            e.printStackTrace();
        }
        txtCurrentDir.setEditable( false );
        currentDirectoryPanel.add( txtCurrentDir, c );
        
        c.insets = new Insets( 0, 3, 10, 10 );
        c.gridx = 1;
        c.weightx = 0;
        JButton btnExamine = new JButton( TextConstants.getText( "Report.BtnExamine" ) );
        btnExamine.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {
                examine( );
            }
        } );
        currentDirectoryPanel.add( btnExamine, c );
        
        // Panel with the report options
        JPanel reportsPanel = new JPanel( );
        reportsPanel.setLayout( new GridBagLayout( ) );
        
        // Panel with the XML options
        JPanel xmlReportPanel = new JPanel();
        xmlReportPanel.setLayout( new GridBagLayout() );
        xmlReportPanel.setBorder( new TitledBorder( new EtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "Report.XMLReport" ), TitledBorder.LEFT, TitledBorder.TOP ) );
        
        c.insets = new Insets( 10, 10, 3, 10 );
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        chkXMLReport = new JCheckBox( TextConstants.getText( "Report.GenerateXML" ), false );
        xmlReportPanel.add( chkXMLReport, c );
        
        c.insets = new Insets( 0, 10, 10, 10 );
        c.gridy = 1;
        c.gridwidth = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        JTextPane xmlReportInfo = new JTextPane( );
        xmlReportInfo.setEditable( false );
        xmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        xmlReportInfo.setText( TextConstants.getText( "Report.TextXMLReport" ) );
        xmlReportPanel.add( xmlReportInfo, c );
        
        // Panel with the HTML options
        JPanel htmlReportPanel = new JPanel();
        htmlReportPanel.setLayout( new GridBagLayout() );
        htmlReportPanel.setBorder( new TitledBorder( new EtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "Report.HTMLReport" ) , TitledBorder.LEFT, TitledBorder.TOP ) );
        
        c.insets = new Insets( 10, 10, 3, 10 );
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        chkHTMLReport = new JCheckBox( TextConstants.getText( "Report.GenerateHTML" ), false );
        chkHTMLReport.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent arg0 ) {
                lblImportanceFilter.setEnabled( chkHTMLReport.isSelected( ) );
                cmbImportanceFilter.setEnabled( chkHTMLReport.isSelected( ) );
            }
        } );
        htmlReportPanel.add( chkHTMLReport, c );
        
        c.insets = new Insets( 10, 10, 3, 2 );
        c.gridx = 1;
        c.weightx = 0;
        lblImportanceFilter = new JLabel( TextConstants.getText( "Report.Importance" ) );
        lblImportanceFilter.setEnabled( chkHTMLReport.isSelected( ) );
        htmlReportPanel.add( lblImportanceFilter, c );
        
        c.insets = new Insets( 10, 2, 3, 10 );
        c.gridx = 2;
        cmbImportanceFilter = new JComboBox( IMPORTANCE_VALUES_PRINT );
        cmbImportanceFilter.setSelectedIndex( AssessmentRule.N_IMPORTANCE_VALUES / 2 );
        cmbImportanceFilter.setEnabled( chkHTMLReport.isSelected( ) );
        htmlReportPanel.add( cmbImportanceFilter, c );
        
        c.insets = new Insets( 0, 10, 10, 10 );
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        JTextPane htmlReportInfo = new JTextPane( );
        htmlReportInfo.setEditable( false );
        htmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        htmlReportInfo.setText( TextConstants.getText( "Report.TextHTMLReport" ) );
        htmlReportPanel.add( htmlReportInfo, c );
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        reportsPanel.add( xmlReportPanel, c );
        
        c.gridy = 1;
        reportsPanel.add( htmlReportPanel, c );
        
        // Panel with the buttons
        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new FlowLayout( ) );
        JButton btnLoad = new JButton( TextConstants.getText( "Report.GenerateHTMLReport" ) );
        btnLoad.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent arg0 ) {
                generateReport( );
                setVisible( false );
            }
        });
        buttonsPanel.add( btnLoad );
        JButton btnCancel = new JButton( TextConstants.getText( "Report.Cancel" ) );
        btnCancel.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent arg0 ) {
                setVisible( false );
            }
        });
        buttonsPanel.add( btnCancel );
        
        add( reportsPanel, BorderLayout.CENTER );
        
        JPanel southPanel = new JPanel();
        southPanel.setLayout( new BorderLayout() );
        
        southPanel.add( currentDirectoryPanel, BorderLayout.CENTER );
        southPanel.add( buttonsPanel, BorderLayout.SOUTH );
        
        add( southPanel, BorderLayout.SOUTH );
        
        setVisible( true );
    }
    
    /**
     * Shows a file chooser dialog to select the new folder.
     */
    private void examine( ) {
        JFileChooser fileDialog = new JFileChooser( "." );
        fileDialog.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        fileDialog.setFileFilter( new FolderFileFilter( ) );
        if( fileDialog.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
            try {
                txtCurrentDir.setText( fileDialog.getSelectedFile( ).getCanonicalPath( ) );
            } catch( IOException e ) {
                e.printStackTrace( );
            }
        }
    }
    
    /**
     * Generate the report according to the selected options.
     */
    private void generateReport( ) {
        if( chkXMLReport.isSelected( ) )
            assessmentEngine.generateXMLReport( txtCurrentDir.getText( ) + "/" + adventureID + "-report.xml" );
        if( chkHTMLReport.isSelected( ) )
            assessmentEngine.generateHTMLReport( txtCurrentDir.getText( ) + "/" + adventureID + "-report.html", cmbImportanceFilter.getSelectedIndex( ) );
    }
    
    /**
     * File filter for the folders.
     */
    private class FolderFileFilter extends FileFilter {
        @Override
        public boolean accept( File f ) {
            return f.isDirectory( );
        }

        @Override
        public String getDescription( ) {
            return "Folders";
        }
    }
}
