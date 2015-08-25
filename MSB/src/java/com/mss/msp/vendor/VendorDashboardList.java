/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.vendor;

/**
 *
 * @author miracle
 */
public class VendorDashboardList {

    private String months;
    private int requirementCount;
    private int noOfReqWon;
    private int noOfReqLose;
    private int noOfConSelected;
    private int noOfConRejected;
    private int noOfConProcessing;

    public int getNoOfReqWon() {
        return noOfReqWon;
    }

    public void setNoOfReqWon(int noOfReqWon) {
        this.noOfReqWon = noOfReqWon;
    }

    public int getNoOfReqLose() {
        return noOfReqLose;
    }

    public void setNoOfReqLose(int noOfReqLose) {
        this.noOfReqLose = noOfReqLose;
    }

    public int getNoOfConSelected() {
        return noOfConSelected;
    }

    public void setNoOfConSelected(int noOfConSelected) {
        this.noOfConSelected = noOfConSelected;
    }

    public int getNoOfConRejected() {
        return noOfConRejected;
    }

    public void setNoOfConRejected(int noOfConRejected) {
        this.noOfConRejected = noOfConRejected;
    }

    public int getNoOfConProcessing() {
        return noOfConProcessing;
    }

    public void setNoOfConProcessing(int noOfConProcessing) {
        this.noOfConProcessing = noOfConProcessing;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public int getRequirementCount() {
        return requirementCount;
    }

    public void setRequirementCount(int requirementCount) {
        this.requirementCount = requirementCount;
    }
    
    
}
