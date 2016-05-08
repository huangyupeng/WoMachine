package com.example.peng.graduationproject.model;

/**
 * Created by peng on 2016/4/18.
 */
public class Order {

    private User farmOwner;

    private MachineOwner machineOwner;

    private int State; //0表示未接受

    private String location;

    private double latitude;

    private double longitude;

    private int farm_type;

    private int machine_type;

    private String start_time;

    private String end_time;

    private String commit_date;


    private double price;



    public User getFarmOwner() {
        return farmOwner;
    }

    public void setFarmOwner(User farmOwner) {
        this.farmOwner = farmOwner;
    }

    public MachineOwner getMachineOwner() {
        return machineOwner;
    }

    public void setMachineOwner(MachineOwner machineOwner) {
        this.machineOwner = machineOwner;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getFarm_type() {
        return farm_type;
    }

    public void setFarm_type(int farm_type) {
        this.farm_type = farm_type;
    }

    public int getMachine_type() {
        return machine_type;
    }

    public void setMachine_type(int machine_type) {
        this.machine_type = machine_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCommit_date() {
        return commit_date;
    }

    public void setCommit_date(String commit_date) {
        this.commit_date = commit_date;
    }
}
