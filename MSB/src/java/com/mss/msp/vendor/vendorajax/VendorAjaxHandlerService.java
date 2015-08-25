/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.vendor.vendorajax;

import com.mss.msp.util.ServiceLocatorException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author NagireddySeerapu
 */
public interface VendorAjaxHandlerService {

    public String getVendorStates(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public String getVendorSearchDetails(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public int updateVendorDetails(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public String getVendorContactDetails(int orgId) throws ServiceLocatorException;

    public String saveVendorContacts(int userId, int userSessionId) throws ServiceLocatorException;

    public String getVendorContactSearchResults(VendorAjaxHandler vendorAjaxHandler, int orgId) throws ServiceLocatorException;

    public String getVendorsListByTireType(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public int SaveVendorsAssociationDetals(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public String getVendorAssociationDetails(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public String searchVendorAssociationDetails(HttpServletRequest httpServletRequest, VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;
    // added by Aklakh

    public String editVendorAssociation(HttpServletRequest httpServletRequest, int vendorId,int orgId) throws ServiceLocatorException;

    public String getVendorNames(int tireId) throws ServiceLocatorException;

    public int updateVendorAssociationDetails(VendorAjaxHandler vendorAjaxHandler) throws ServiceLocatorException;

    public String getVendorDashboardList(int year, int month, int sessionOrgId) throws ServiceLocatorException;
}
