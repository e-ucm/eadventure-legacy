package es.eucm.eadventure.engine.core.control;

public interface TimerEventListener {

    public void cycleCompleted( int timerId, long elapsedTime );
    
    public void timerStarted ( int timerId, long currentTime );
    
    public void timerStopped ( int timerId, long currentTime );
}
