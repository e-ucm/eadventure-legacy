/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control;

import java.util.TimerTask;

import es.eucm.eadventure.common.gui.TC;

public class AutoSave extends TimerTask {

    SaveThread thread;

    public AutoSave( ) {

        super( );
    }

    @Override
    public void run( ) {

        thread = new SaveThread( );
        thread.start( );
    }

    public void stop( ) {

        if( thread != null ) {
            WindowThread windowThread = new WindowThread( );
            windowThread.start( );
            while( thread.isAlive( ) ) {
            }
            windowThread.close( );
        }
    }

    private class WindowThread extends Thread {

        private boolean finish = false;

        public void close( ) {

            finish = true;
        }

        @Override
        public void run( ) {

            Controller.getInstance( ).showLoadingScreen( TC.get( "GeneralText.FinishingBackup" ) );
            while( !finish ) {
                try {
                    Thread.sleep( 100 );
                }
                catch( InterruptedException e ) {
                }
            }
            Controller.getInstance( ).hideLoadingScreen( );
        }
    }

    private class SaveThread extends Thread {

        @Override
        public void interrupt( ) {

            super.interrupt( );
            try {
                this.finalize( );
            }
            catch( Exception e ) {
            }
            catch( Throwable e ) {
                e.printStackTrace( );
            }
        }

        @Override
        public void run( ) {

            try {
                System.out.println( "Started backup..." );
                boolean temp = Controller.getInstance( ).createBackup( null );
                System.out.println( "Finished backup " + ( temp ? "OK" : "FAIL" ) );
            }
            catch( Exception e ) {
                System.out.println( "Backup interrupted" );
            }
        }

    }

}
