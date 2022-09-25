package management;

public class Schedule {

    // Array of JobIDs reflecting the job occupying that hour long slot
    public long[] timeTable;

    public Schedule(){
        timeTable = new long[24];
        for (int i = 0; i < 24; i++){
            timeTable[i] = 0;
        }
    }
    public Schedule(long[] timeTable){
        this.timeTable = timeTable;
    }

    public long[] getTable(){
        return timeTable;
    }
}
