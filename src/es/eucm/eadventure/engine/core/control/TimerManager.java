package es.eucm.eadventure.engine.core.control;

import java.util.ArrayList;
import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.data.SaveTimer;

public class TimerManager {

    private static int ID = 0;

    private static TimerManager singleton = new TimerManager( );

    public static TimerManager getInstance( ) {
        return singleton;
    }

    private HashMap<Integer, FunctionalTimer> timers;

    public TimerManager( ) {
        timers = new HashMap<Integer, FunctionalTimer>( );
        ID = 0;
    }

    public void reset( ) {
        if (timers!=null)
            timers.clear( );
        timers = new HashMap<Integer, FunctionalTimer>( );
        ID = 0;
    }

    public int addTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener ) {
        FunctionalTimer newTimer = new FunctionalTimer( initConditions, endConditions, listener );
        timers.put( new Integer( ID ), newTimer );
        ID++;
        return ID - 1;
    }

    public int addTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener, long timeUpdate ) {
        FunctionalTimer newTimer = new FunctionalTimer( initConditions, endConditions, listener, timeUpdate, false );
        timers.put( new Integer( ID ), newTimer );
        ID++;
        return ID - 1;
    }

    public void deleteTimer( int id ) {
        if( timers.containsKey( new Integer(id) ) ) {
            timers.remove( new Integer(id) );
        }
    }

    public boolean isTimerRunning( int id ) {
        if( id >= 0 && id < timers.size( ) ) {
            FunctionalTimer timer = timers.get( id );
            return timer.getState( ) == FunctionalTimer.STATE_RUNNING;
        }
        return false;
    }

    public boolean isTimerDone( int id ) {
        if( id >= 0 && id < timers.size( ) ) {
            FunctionalTimer timer = timers.get( id );
            return timer.getState( ) == FunctionalTimer.STATE_DONE;
        }
        return true;
    }

    public boolean isTimerNotStarted( int id ) {
        if( id >= 0 && id < timers.size( ) ) {
            FunctionalTimer timer = timers.get( id );
            return timer.getState( ) == FunctionalTimer.STATE_NO_INIT;
        }
        return false;
    }
    
    /**
     * Get the suitable attributes to store in save data
     * 
     * @return
     * 			the array list with suitable attributes to save
     */
    public ArrayList<SaveTimer> getTimers(){
    	ArrayList<SaveTimer> list = new ArrayList<SaveTimer>();
    	SaveTimer st = new SaveTimer();
    	FunctionalTimer ft;
    	for( int i = 0; i < ID; i++ ) {
    		ft = timers.get(new Integer(i));
    		st.setLastUpdate(ft.getLastUpdate());
    		st.setState(ft.getState());
    		st.setTimeUpdate(ft.getTimeUpdate());
    		st.setAssessmentRule(ft.isAssessmentTimer());
    		list.add(st);
    	}
    	return list;
    }
    
    /*
    public FunctionalTimer getTimer(int i){
    	//TODO ver que pasa en el caso chungo
    	if (i < ID){
    		return timers.get(new Integer(i));
    	} else {
    		return new FunctionalTimer();
    	}
    }
    */
    
    public boolean isRunningState(int state){
    	return (state == FunctionalTimer.STATE_RUNNING);
    }
    
    /**
     * Change the state, the timeUpdate and lastUpdate of indexed timer when a game is loaded
     * @return
     * 			-1 if the parameter "i" does´t fit with "ID", "i" in other case
     */
    public int changeValueOfTimer(int i, SaveTimer st){
    	if (i>ID){
    		return -1;
    	} else {
    		FunctionalTimer currentTimer = timers.get( new Integer( i ) );
    		currentTimer.setLastUpdate(st.getLastUpdate());
    		currentTimer.setState(st.getState());
    		currentTimer.setTimeUpdate(st.getTimeUpdate());
    		return i;
    	}
    }
    
    
    /**
     * 
     * @param notifyCycles
     */

    public void update( boolean notifyCycles ) {
        
        // Current time in seconds
        long currentTime = System.currentTimeMillis( ) / 1000;

        // Iterate through timers
        for( int i = 0; i < ID; i++ ) {
            if( timers.containsKey( new Integer( i ) ) ) {
                FunctionalTimer currentTimer = timers.get( new Integer( i ) );

                // First case: the timer has not been initialized yet. In this
                // case
                // check if current conditions are satisfied.
                if( currentTimer.getState( ) == FunctionalTimer.STATE_NO_INIT 
                        || currentTimer.getState( ) == FunctionalTimer.STATE_DONE ) {
                    if( new FunctionalConditions(currentTimer.getInitConditions( )).allConditionsOk( ) ) {
                        currentTimer.setLastUpdate( currentTime );
                        currentTimer.setState( FunctionalTimer.STATE_RUNNING );

                        TimerEventListener listener = currentTimer.getListener( );
                        if( listener != null )
                            listener.timerStarted( i, currentTime );
                    }
                }

                // Second case: the timer is running. Two checks are required:
                // 1) Check if end conditions are OK. In such situation, stop
                // the timer
                // 2) Check if a timer cycle has been accomplished to notify the
                // listener
                else if( currentTimer.getState( ) == FunctionalTimer.STATE_RUNNING ) {
                    if( new FunctionalConditions( currentTimer.getEndConditions( ) ).allConditionsOk( ) ) {
                        currentTimer.setLastUpdate( currentTime );
                        currentTimer.setState( FunctionalTimer.STATE_DONE );

                        TimerEventListener listener = currentTimer.getListener( );
                        if( listener != null )
                            listener.timerStopped( i, currentTime );
                    } else if( notifyCycles ) {
                        // Check the time gap:
                        long gap = currentTime - currentTimer.getLastUpdate( );
                        if( gap >= currentTimer.getTimeUpdate( ) && currentTimer.isNotifyUpdates( ) ) {
                            TimerEventListener listener = currentTimer.getListener( );
                            if( listener != null ) {
                                currentTimer.setLastUpdate( currentTime );
                                listener.cycleCompleted( i, gap );
                            }
                        }
                    }
                }
            }
        }
    }

    private static class FunctionalTimer {

        public static final long NO_UPDATE = Long.MIN_VALUE;

        public static final int STATE_NO_INIT = 0;

        public static final int STATE_RUNNING = 1;

        public static final int STATE_DONE = 2;

        /**
         * Last time the timer was updated
         */
        private long lastUpdate;

        /**
         *	True when timer manager must notify the listener 
         */
        private boolean notifyUpdates;

        /**
         * The time gap of a timer cycle
         */
        private long timeUpdate;

        private Conditions initConditions;

        private Conditions endConditions;

        private TimerEventListener listener;
        
        /**
         * True if it is an assessment functional timer, false if it isn´t
         */
        private boolean isAssessmentTimer;
        
        
        private int state;

        public FunctionalTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener ) {
            this( initConditions, endConditions, listener, NO_UPDATE , true);
        }
        
        /*
        // Empty constructor
        public FunctionalTimer(){
        	
        }*/

        public FunctionalTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener, long timeUpdate, boolean assessment ) {
            this.initConditions = initConditions;
            this.endConditions = endConditions;
            this.listener = listener;
            this.lastUpdate = 0;
            this.timeUpdate = timeUpdate;
            notifyUpdates = ( timeUpdate != NO_UPDATE );
            this.state = STATE_NO_INIT;
            this.isAssessmentTimer = assessment;
        }
        
        public boolean isAssessmentTimer(){
        	return isAssessmentTimer;
        }

        /**
         * @return the lastUpdate
         */
        public long getLastUpdate( ) {
            return lastUpdate;
        }

        /**
         * @param lastUpdate
         *            the lastUpdate to set
         */
        public void setLastUpdate( long lastUpdate ) {
            this.lastUpdate = lastUpdate;
        }

        /**
         * @return the notifyUpdates
         */
        public boolean isNotifyUpdates( ) {
            return notifyUpdates;
        }

        /**
         * @param notifyUpdates
         *            the notifyUpdates to set
         */
        public void setNotifyUpdates( boolean notifyUpdates ) {
            this.notifyUpdates = notifyUpdates;
        }

        /**
         * @return the timeUpdate
         */
        public long getTimeUpdate( ) {
            return timeUpdate;
        }

        /**
         * @param timeUpdate
         *            the timeUpdate to set
         */
        public void setTimeUpdate( long timeUpdate ) {
            this.timeUpdate = timeUpdate;
        }

        /**
         * @return the initConditions
         */
        public Conditions getInitConditions( ) {
            return initConditions;
        }

        /**
         * @param initConditions
         *            the initConditions to set
         */
        public void setInitConditions( Conditions initConditions ) {
            this.initConditions = initConditions;
        }

        /**
         * @return the endConditions
         */
        public Conditions getEndConditions( ) {
            return endConditions;
        }

        /**
         * @param endConditions
         *            the endConditions to set
         */
        public void setEndConditions( Conditions endConditions ) {
            this.endConditions = endConditions;
        }

        /**
         * @return the listener
         */
        public TimerEventListener getListener( ) {
            return listener;
        }

        /**
         * @param listener
         *            the listener to set
         */
        public void setListener( TimerEventListener listener ) {
            this.listener = listener;
        }

        /**
         * @return the state
         */
        public int getState( ) {
            return state;
        }

        /**
         * @param state
         *            the state to set
         */
        public void setState( int state ) {
            this.state = state;
         
        }
    }
    
    
}
