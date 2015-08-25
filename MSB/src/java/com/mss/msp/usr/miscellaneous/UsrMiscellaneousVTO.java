/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.usr.miscellaneous;

import java.io.Serializable;

/**
 *
 * @author Vinod Siram <vsiram@miraclesoft.com>
 *
 * 
 */
public class UsrMiscellaneousVTO implements Serializable {

    private int userid;
    private int IsTeamLead;
    private int IsManager;
    private int IsPMO;
    private int IsSbteam;
    private int DeptId;
    private int TitleId;
    private int OptContact;
    private int ReportsTo;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getIsTeamLead() {
        return IsTeamLead;
    }

    public void setIsTeamLead(int IsTeamLead) {
        this.IsTeamLead = IsTeamLead;
    }

    public int getIsManager() {
        return IsManager;
    }

    public void setIsManager(int IsManager) {
        this.IsManager = IsManager;
    }

    public int getIsPMO() {
        return IsPMO;
    }

    public void setIsPMO(int IsPMO) {
        this.IsPMO = IsPMO;
    }

    public int getIsSbteam() {
        return IsSbteam;
    }

    public void setIsSbteam(int IsSbteam) {
        this.IsSbteam = IsSbteam;
    }

    public int getDeptId() {
        return DeptId;
    }

    public void setDeptId(int DeptId) {
        this.DeptId = DeptId;
    }

    public int getTitleId() {
        return TitleId;
    }

    public void setTitleId(int TitleId) {
        this.TitleId = TitleId;
    }

    public int getOptContact() {
        return OptContact;
    }

    public void setOptContact(int OptContact) {
        this.OptContact = OptContact;
    }

    public int getReportsTo() {
        return ReportsTo;
    }

    public void setReportsTo(int ReportsTo) {
        this.ReportsTo = ReportsTo;
    }

   
}