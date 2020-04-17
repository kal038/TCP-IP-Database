package main;

public enum Jobs {
    Office_Worker(6),Boulder_Reducer(3),Ditch_Digger(3),Civilian_Guard(8),Farmer(4),Construction(4),Computer_Repair(8),Food_Tester(8),Shoe_Tester(6),NA(-1);
    public final int employTime;
    private Jobs(int employTime){
        this.employTime = employTime;
    }
    public int getEmployTime(){
        return employTime;
    }
}
