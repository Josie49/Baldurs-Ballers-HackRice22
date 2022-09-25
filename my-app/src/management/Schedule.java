package management;

public class Schedule {

    public boolean[] timeTable;

    public Schedule(){
        timeTable = new boolean[24];
        for (int i = 0; i < 24; i++){
            timeTable[i] = false;
        }
    }
}
