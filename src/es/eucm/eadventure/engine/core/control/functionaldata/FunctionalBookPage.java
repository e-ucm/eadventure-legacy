package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.text.rtf.RTFEditorKit;

import de.schlichtherle.io.File;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

import javax.swing.text.html.HTMLDocument;

public class FunctionalBookPage extends JPanel{

    private BookPage bookPage;
    
    private boolean isValid;
    
    private Image background;
    
    private JEditorPane editorPane;
    
    public FunctionalBookPage ( Image background ){
        this.background = background;
    }
    
    public FunctionalBookPage ( BookPage bookPage, Image background, boolean listenHyperLinks ){
        super();
        editorPane = new JEditorPane();
        isValid=true;
        this.bookPage = bookPage;
        this.background = background;
        this.addMouseListener(new FunctionalBookMouseListener());
        URL url = null;
        if (bookPage.getType( ) == BookPage.TYPE_URL){
            try {
                url = new URL(bookPage.getUri( ));
                url.openStream( ).close( );
            } catch( Exception e ) {
                isValid = false;
                //System.out.println( "[LOG] FunctionalBookPage - Constructor - Error creating URL "+bookPage.getUri( ) );
            }
            
            try {

                if (isValid){
                editorPane.setPage( url );
                editorPane.setEditable( false );
                if (listenHyperLinks)
                    editorPane.addHyperlinkListener( new BookHyperlinkListener() );
                if (!( editorPane.getEditorKit( ) instanceof HTMLEditorKit ) && !( editorPane.getEditorKit( ) instanceof RTFEditorKit )){
                   isValid = false;
                   //System.out.println( "[LOG] FunctionalBookPage - Constructor - Type of page not valid "+bookPage.getUri( ) );
                }else{
                    //System.out.println( "[LOG] FunctionalBookPage - Constructor - Page OK "+bookPage.getUri( ) );
                }
                
                }            
            } catch( IOException e ) {}

        }
        else if (bookPage.getType( ) == BookPage.TYPE_RESOURCE){
            //System.out.println( bookPage.getUri( ) );
            url = ResourceHandler.getInstance( ).getResourceAsURLFromZip( bookPage.getUri( ) );
            //System.out.println( url );
            String ext =url.getFile( ).substring( url.getFile().lastIndexOf( '.' )+1, url.getFile( ).length( ) ).toLowerCase( );
            if (ext.equals( "html" ) || ext.equals( "htm" ) || ext.equals( "rtf" )){
                
                //Read the text
                StringBuffer textBuffer = new StringBuffer();
                InputStream is =null;
                try{
                    is =url.openStream( );
                    int c;
                    while((c=is.read( ))!=-1){
                        textBuffer.append( (char)c );
                    }
                }catch (IOException e){
                    isValid = false;
                }
                finally {
                    if (is!=null){
                        try {
                            is.close();
                        } catch( IOException e ) {
                            isValid = false;
                        }
                    }
                }
                
                //Set the proper content type
                if (ext.equals( "html" ) || ext.equals( "htm" )){
                    editorPane.setContentType( "text/html" );
                    ProcessHTML processor = new ProcessHTML (textBuffer.toString( ));
                    String htmlProcessed = processor.start( );
                    editorPane.setText( htmlProcessed );
                } else{
                    editorPane.setContentType( "text/rtf" );
                    editorPane.setText( textBuffer.toString( ) );
                }
                isValid=true;
                
            }
            /*try {
                File file = new File ("C:\\Datos\\workspace\\EAdventure2D\\ChocolateRevisited\\assets\\styledtext\\index.htm");
                url = file.toURI( ).toURL( );
            } catch( MalformedURLException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            
        }
        
        if (url==null){
            isValid=false;
            //System.out.println( "[LOG] FunctionalBookPage - Constructor - URL is null "+bookPage.getUri( ) );
        }
        
        
        
       
        editorPane.addMouseListener( new FunctionalBookMouseListener() );
        //addMouseListener(Game.getInstance());
        editorPane.setOpaque( false );
        //editorPane.setCaret( null );
        editorPane.setEditable( false );
        //editorPane.setHighlighter( null );
        
        this.setOpaque( false );
        
        this.setLayout(null );
        //if (bookPage.getMargin( )>0){
        //    Component margin = Box.createRigidArea( new Dimension(bookPage.getMargin( ), GUI.WINDOW_HEIGHT) );
            //margin.addMouseListener( new FunctionalBookMouseListener() );
        //    this.add( margin );
        //}
    	editorPane.setBounds(bookPage.getMargin(), bookPage.getMarginTop(), GUI.WINDOW_WIDTH - bookPage.getMargin() - bookPage.getMarginEnd(), GUI.WINDOW_HEIGHT - bookPage.getMarginTop() - bookPage.getMarginBottom());
        if ( bookPage.getScrollable( ) )
            this.add( new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) );
        else
            this.add( editorPane );
    }
    
   
    private class FunctionalBookMouseListener extends MouseAdapter {
        
        public void mouseClicked(MouseEvent evt){
            int x = evt.getX( );
            int y = evt.getY( );
            if (evt.getSource( ) == editorPane){
                //Spread the call gauging the positions so the margin is taken into account
                x+=bookPage.getMargin( );
                y+=bookPage.getMarginTop();
            }

            MouseEvent nEvt = new MouseEvent((Component)evt.getSource( ), evt.getID( ), evt.getWhen( ), evt.getModifiers( ),
                    x , y, 
                   evt.getClickCount( ), evt.isPopupTrigger( ), evt.getButton( ));
           Game.getInstance( ).mouseClicked( nEvt );
        }
        
    }
    
    /**
     * Listener for the Hyperlinks in the HTML books
     * @author Javier Torrente
     *
     */
    private class BookHyperlinkListener implements HyperlinkListener {
        
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                JEditorPane pane = (JEditorPane) e.getSource();
                if (e instanceof HTMLFrameHyperlinkEvent) {
                    HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                    HTMLDocument doc = (HTMLDocument)pane.getDocument();
                    doc.processHTMLFrameHyperlinkEvent(evt);
                } else {
                    try {
                        pane.setPage(e.getURL());
                    } catch (Exception t) {
                    	ReportDialog.GenerateErrorReport(t, true, "UNKNOWERROR");
                    }
                }
            }
        }
    }
    
    public void paint (Graphics g){
        g.drawImage( background, 0, 0, background.getWidth( null ), background.getHeight( null ), null );
        super.paint( g );
    }

    /**
     * @return the bookPage
     */
    public BookPage getBookPage( ) {
        return bookPage;
    }

    /**
     * @param bookPage the bookPage to set
     */
    public void setBookPage( BookPage bookPage ) {
        this.bookPage = bookPage;
    }

    /**
     * @return the isValid
     */
    public boolean isValid( ) {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setValid( boolean isValid ) {
        this.isValid = isValid;
    }

    public class ProcessHTML{

        private String html;
        
        private boolean sthBetween;
        
        private int currentPos;
        
        private int state;
        
        private final int STATE_NONE = 0;
        private final int STATE_LT = 1;
        private final int STATE_SRC = 2;
        private final int STATE_EQ = 3;
        private final int STATE_RT = 4;
        private final int STATE_RTQ = 5;
        
        private String reference;
        
        private final String[] tokens = new String[]{"<",">","=","\"", "src"};
        
        public ProcessHTML ( String html ){
            this.html = html;
            sthBetween = false;
            currentPos = 0;
            state = STATE_NONE;
        }
        
        
        
        public String start(){
            state = STATE_NONE;
            String lastThree = "";
            reference = "";
            for (currentPos=0; currentPos<html.length(); currentPos++){
                char current = html.charAt( currentPos );
                if (lastThree.length( )<3)
                    lastThree+=current;
                else
                    lastThree=lastThree.substring( 1,3 )+current;
                
                if (state == STATE_NONE){
                    if (current=='<'){
                        state = STATE_LT;
                    }
                }
                else if (state == STATE_LT){
                    if (lastThree.toLowerCase( ).equals( "src" )){
                        state = STATE_SRC;
                    } else if (current=='>'){
                        state = STATE_NONE;
                    }
                }

                else if (state == STATE_SRC){
                    if (current=='='){
                        state = STATE_EQ;
                    }else if (current!=' '){
                        state = STATE_NONE;
                    }
                }
                else if (state == STATE_EQ){
                    if (current =='"'){
                        state = STATE_RTQ;
                    } else if  (current != ' '){
                        reference += current;
                        state = STATE_RT;
                    }
                }
                else if (state == STATE_RTQ){
                    if (current!='>' && current!='"'){
                        reference+=current;
                    }else{
                        state = STATE_NONE;
                        replaceReference(currentPos-reference.length( ), reference.length( ));
                    }
                }else if (state == STATE_RT){
                    if (current!='>' && current!=' '){
                        reference+=current;
                    }else{
                        state = STATE_NONE;
                        replaceReference(currentPos-reference.length( ), reference.length( ));
                    }
                }

            }
            
            return html;
        }
        
        
        private void replaceReference (int index, int length){
            try{
            int lastSlash = Math.max( bookPage.getUri( ).lastIndexOf( "/" ), bookPage.getUri( ).lastIndexOf( "\\" ));
            String assetPath = bookPage.getUri( ).substring( 0, lastSlash )+"/"+reference;
            String destinyPath = ResourceHandler.getInstance( ).getResourceAsURL( assetPath ).getFile();
            if (destinyPath!=null){
                String leftSide = html.substring( 0, index );
                String rightSide = html.substring( index+length, html.length( ) );
                File file = new File(destinyPath);
                html = leftSide+file.toURI( ).toURL( ).toString( )+rightSide;
            }
            reference = "";
            }catch (Exception e){
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
            }
        }
    }
    
}
