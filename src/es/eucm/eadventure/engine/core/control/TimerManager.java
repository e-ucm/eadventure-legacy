package es.eucm.eadventure.engine.core.control;

import java.util.HashMap;

import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;

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
        FunctionalTimer newTimer = new FunctionalTimer( initConditions, endConditions, listener, timeUpdate );
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

        private long lastUpdate;

        private boolean notifyUpdates;

        private long timeUpdate;

        private Conditions initConditions;

        private Conditions endConditions;

        private TimerEventListener listener;

        private int state;

        public FunctionalTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener ) {
            this( initConditions, endConditions, listener, NO_UPDATE );
        }

        public FunctionalTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener, long timeUpdate ) {
            this.initConditions = initConditions;
            this.endConditions = endConditions;
            this.listener = listener;
            this.lastUpdate = 0;
            this.timeUpdate = timeUpdate;
            notifyUpdates = ( timeUpdate != NO_UPDATE );
            this.state = STATE_NO_INIT;
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
