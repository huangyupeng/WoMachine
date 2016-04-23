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

}
