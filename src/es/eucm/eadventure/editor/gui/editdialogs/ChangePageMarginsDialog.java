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
package es.eucm.eadventure.editor.gui.editdialogs;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookPagePreviewPanel;

/**
 * Class that shows a dialog to change the margins of a page in a book
 * 
 * 
 * @author Eugenio Marchiori
 * 
 */
public class ChangePageMarginsDialog extends ToolManagableDialog {

    /**
     * Default generated serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The bookPage which is being edited
     */
    private BookPagesListDataControl bookPagesList;

    /**
     * The preview panel for the page (where the page is shown)
     */
    private BookPagePreviewPanel bookPagePreview;

    /**
     * Slider for the left (start) margin
     */
    private JSlider marginSlider;

    /**
     * Slider for the right (end) margin
     */
    private JSlider marginEndSlider;

    /**
     * Slider for the top margin
     */
    private JSlider marginTopSlider;

    /**
     * Slider for the bottom margin
     */
    private JSlider marginBottomSlider;

    /**
     * Background image for the book
     */
    private BookDataControl dataControl;

    /**
     * This value is only used to avoid that invoking updateFileds will modify
     * the values stored in dataControl, as this would add new Tools
     */
    private boolean setChanges;

    /**
     * Constructor with a bookPage and an image for the background, displays the
     * dialog
     * 
     * @param bookPagesList
     *            The BookPage to be edited
     * @param background
     *            The image to display in the background
     */
    public ChangePageMarginsDialog( BookPagesListDataControl bookPagesList, BookDataControl dControl ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "BookPage.MarginDialog" ), true );
        this.bookPagesList = bookPagesList;
        this.dataControl = dControl;
        setChanges = true;

        this.setLayout( null );
        this.setTitle( TC.get( "BookPage.MarginDialog" ) );

        bookPagePreview = new BookPagePreviewPanel( dataControl, true );
        bookPagePreview.setCurrentBookPage( bookPagesList.getSelectedPage( ) );

        bookPagePreview.setPreferredSize( new Dimension( 800, 600 ) );
        bookPagePreview.setBounds( 30, 30, 800, 600 );
        bookPagePreview.setDrawArrows( false );
        this.add( bookPagePreview );

        createMarginSlider( );
        createMarginEndSlider( );

        JPanel topPanel = new JPanel( );
        topPanel.setLayout( new BorderLayout( ) );
        topPanel.add( marginSlider, BorderLayout.WEST );
        topPanel.add( marginEndSlider, BorderLayout.EAST );
        topPanel.setPreferredSize( new Dimension( 800, 30 ) );
        topPanel.setBounds( 30, 0, 800, 30 );
        this.add( topPanel );

        createMarginTopSlider( );
        createMarginBottomSlider( );

        JPanel sidePanel = new JPanel( );
        sidePanel.setLayout( new BorderLayout( ) );
        sidePanel.add( marginTopSlider, BorderLayout.NORTH );
        sidePanel.add( marginBottomSlider, BorderLayout.SOUTH );

        sidePanel.setPreferredSize( new Dimension( 30, 600 ) );
        sidePanel.setBounds( 0, 30, 30, 600 );
        this.add( sidePanel );

        this.setSize( 840, 660 );
        //this.pack( );
        this.setResizable( false );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        this.setVisible( true );
    }

    /**
     * Creates the marginBottomSlider
     */
    private void createMarginBottomSlider( ) {

        marginBottomSlider = new JSlider( SwingConstants.VERTICAL, 0, 150, ( bookPagesList != null ) ? bookPagesList.getSelectedPage( ).getMarginBottom( ) : 0 );
        marginBottomSlider.setMajorTickSpacing( 15 );
        marginBottomSlider.setMinorTickSpacing( 5 );
        marginBottomSlider.setPaintTicks( true );
        marginBottomSlider.setPaintLabels( false );
        marginBottomSlider.setEnabled( bookPagesList != null );
        marginBottomSlider.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( !marginBottomSlider.getValueIsAdjusting( ) ) {
                    marginChanged( );
                }
            }
        } );
        marginBottomSlider.setToolTipText( TC.get( "BookPage.MarginToolTip" ) );
    }

    /**
     * Creates the marginTopSlider
     */
    private void createMarginTopSlider( ) {

        marginTopSlider = new JSlider( SwingConstants.VERTICAL, -150, 0, ( bookPagesList != null ) ? -bookPagesList.getSelectedPage( ).getMarginTop( ) : 0 );
        marginTopSlider.setMajorTickSpacing( 15 );
        marginTopSlider.setMinorTickSpacing( 5 );
        marginTopSlider.setPaintTicks( true );
        marginTopSlider.setPaintLabels( false );
        marginTopSlider.setEnabled( bookPagesList != null );
        marginTopSlider.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( !marginTopSlider.getValueIsAdjusting( ) ) {
                    marginChanged( );
                }
            }
        } );
        marginTopSlider.setToolTipText( TC.get( "BookPage.MarginToolTip" ) );
    }

    /**
     * Creates the marginEndSlider
     */
    private void createMarginEndSlider( ) {

        marginEndSlider = new JSlider( SwingConstants.HORIZONTAL, -150, 0, ( bookPagesList != null ) ? -bookPagesList.getSelectedPage( ).getMarginEnd( ) : 0 );
        marginEndSlider.setMajorTickSpacing( 15 );
        marginEndSlider.setMinorTickSpacing( 5 );
        marginEndSlider.setPaintTicks( true );
        marginEndSlider.setPaintLabels( false );
        marginEndSlider.setEnabled( bookPagesList != null );
        marginEndSlider.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( !marginEndSlider.getValueIsAdjusting( ) ) {
                    marginChanged( );
                }
            }
        } );
        marginEndSlider.setToolTipText( TC.get( "BookPage.MarginToolTip" ) );
    }

    /**
     * Creates the marginSlider
     */
    private void createMarginSlider( ) {

        marginSlider = new JSlider( SwingConstants.HORIZONTAL, 0, 150, ( bookPagesList != null ) ? bookPagesList.getSelectedPage( ).getMargin( ) : 0 );
        marginSlider.setMajorTickSpacing( 15 );
        marginSlider.setMinorTickSpacing( 5 );
        marginSlider.setPaintTicks( true );
        marginSlider.setPaintLabels( false );
        marginSlider.setEnabled( bookPagesList != null );
        marginSlider.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent e ) {

                if( !marginSlider.getValueIsAdjusting( ) ) {
                    marginChanged( );
                }
            }
        } );
        marginSlider.setToolTipText( TC.get( "BookPage.MarginToolTip" ) );
    }

    /**
     * Method called when one of the margins is modified
     */
    protected void marginChanged( ) {

        //if( setChanges ) {
        this.bookPagesList.setMargins( marginSlider.getValue( ), -marginTopSlider.getValue( ), marginBottomSlider.getValue( ), -marginEndSlider.getValue( ) );
        bookPagePreview.updateUI( );
        bookPagePreview.repaint( );
        //}
    }

    @Override
    public boolean updateFields( ) {

        // Temporarily deactivate user changes
        setChanges = false;
        marginSlider.setValue( ( bookPagesList != null ) ? bookPagesList.getSelectedPage( ).getMargin( ) : 0 );
        marginEndSlider.setValue( ( bookPagesList != null ) ? -bookPagesList.getSelectedPage( ).getMarginEnd( ) : 0 );
        marginTopSlider.setValue( ( bookPagesList != null ) ? bookPagesList.getSelectedPage( ).getMarginTop( ) : 0 );
        marginBottomSlider.setValue( ( bookPagesList != null ) ? bookPagesList.getSelectedPage( ).getMarginBottom( ) : 0 );
        this.remove( bookPagePreview );
        bookPagePreview = new BookPagePreviewPanel( dataControl, true );
        bookPagePreview.setCurrentBookPage( bookPagesList.getSelectedPage( ) );
        this.add( bookPagePreview, BorderLayout.CENTER );

        bookPagePreview.updateUI( );
        // Reactivate user changes
        setChanges = false;
        return true;

    }
}
