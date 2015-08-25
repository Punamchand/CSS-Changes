/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.details;

import java.util.Map;

/**
 *
 * @author miracle
 */
public class AccountContactVTO {
    
    private String firstName; 
    private String lastName;
    private String middleName;
    //private String phone;
    private String email;
    private String officePhone;
    //private String OfficeAddress;
    //private String address;
    private String status;
    private int OrgId;
    private int userId;
    //private String ContactEmailId;
    
    //P Address
      private String  conPAddress;
      private String  conPAddress2;
      private String conPCity;
      private String conPZip ; 
      private int conPCountry;
      private int conPState ;  
      private String  conPPhone;
      
      //C Address
      private String conCAddress ;
      private String conCAddress2 ;      
      private String conCZip ;      
      private String conCCity;  
      private String conCPhone;
      private int conCState;
      private int conCCountry;
      private Map state1;
      private Map state2;
      //for title and dept
    private int department;
    private int title;
    private Map titles;
      //for isManager,isTeamLead
    private String isManager;
    private String isTeamLead;
    private boolean checkAddress;
    private String moblieNumber;
    private String email2;
    private String homePhone;
    private String profileImage;
    private String workLocation;
    private String contactDesignation;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrgId() {
        return OrgId;
    }

    public void setOrgId(int OrgId) {
        this.OrgId = OrgId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getConPAddress() {
        return conPAddress;
    }

    public void setConPAddress(String conPAddress) {
        this.conPAddress = conPAddress;
    }

    public String getConPAddress2() {
        return conPAddress2;
    }

    public void setConPAddress2(String conPAddress2) {
        this.conPAddress2 = conPAddress2;
    }

    public String getConPCity() {
        return conPCity;
    }

    public void setConPCity(String conPCity) {
        this.conPCity = conPCity;
    }

    public String getConPZip() {
        return conPZip;
    }

    public void setConPZip(String conPZip) {
        this.conPZip = conPZip;
    }

    public int getConPCountry() {
        return conPCountry;
    }

    public void setConPCountry(int conPCountry) {
        this.conPCountry = conPCountry;
    }

    public int getConPState() {
        return conPState;
    }

    public void setConPState(int conPState) {
        this.conPState = conPState;
    }

    public String getConPPhone() {
        return conPPhone;
    }

    public void setConPPhone(String conPPhone) {
        this.conPPhone = conPPhone;
    }

    public String getConCAddress() {
        return conCAddress;
    }

    public void setConCAddress(String conCAddress) {
        this.conCAddress = conCAddress;
    }

    public String getConCAddress2() {
        return conCAddress2;
    }

    public void setConCAddress2(String conCAddress2) {
        this.conCAddress2 = conCAddress2;
    }

    public String getConCZip() {
        return conCZip;
    }

    public void setConCZip(String conCZip) {
        this.conCZip = conCZip;
    }

    public String getConCCity() {
        return conCCity;
    }

    public void setConCCity(String conCCity) {
        this.conCCity = conCCity;
    }

    public String getConCPhone() {
        return conCPhone;
    }

    public void setConCPhone(String conCPhone) {
        this.conCPhone = conCPhone;
    }

    public int getConCState() {
        return conCState;
    }

    public void setConCState(int conCState) {
        this.conCState = conCState;
    }

    public int getConCCountry() {
        return conCCountry;
    }

    public void setConCCountry(int conCCountry) {
        this.conCCountry = conCCountry;
    }

    public Map getState1() {
        return state1;
    }

    public void setState1(Map state1) {
        this.state1 = state1;
    }

    public Map getState2() {
        return state2;
    }

    public void setState2(Map state2) {
        this.state2 = state2;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Map getTitles() {
        return titles;
    }

    public void setTitles(Map titles) {
        this.titles = titles;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public String getIsTeamLead() {
        return isTeamLead;
    }

    public void setIsTeamLead(String isTeamLead) {
        this.isTeamLead = isTeamLead;
    }

    public boolean isCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(boolean checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getMoblieNumber() {
        return moblieNumber;
    }

    public void setMoblieNumber(String moblieNumber) {
        this.moblieNumber = moblieNumber;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getContactDesignation() {
        return contactDesignation;
    }

    public void setContactDesignation(String contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

   

   

}
