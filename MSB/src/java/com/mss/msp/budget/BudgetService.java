/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.budget;

import com.mss.msp.util.ServiceLocatorException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miracle
 */
public interface BudgetService {

    public List getProjectBudgetDetails(BudgetAction budgetAction) throws ServiceLocatorException;

    public String getProjectBudgetSearchResults(HttpServletRequest httpServletRequest, BudgetAction budgetAction) throws ServiceLocatorException;

    public String saveProjectBudgetDetails(HttpServletRequest httpServletRequest, BudgetAction budgetAction) throws ServiceLocatorException;

    public String getProjectBudgetDetailsToViewOnOverlay(HttpServletRequest httpServletRequest, BudgetAction budgetAction) throws ServiceLocatorException;

    public String doBudgetRecordDelete(HttpServletRequest httpServletRequest, BudgetAction budgetAction) throws ServiceLocatorException;

    public String setDirectorResultForBudget(HttpServletRequest httpServletRequest, BudgetAction budgetAction) throws ServiceLocatorException;
}
