/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.msp.acc.detailsAjax;

import com.mss.msp.acc.details.AccountDetails;
import com.mss.msp.util.ConnectionProvider;
import com.mss.msp.util.HibernateServiceLocator;
import com.mss.msp.util.ServiceLocator;
import com.mss.msp.util.ServiceLocatorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Greg
 */
public class AccountDetailsAjaxHandlerServiceImpl implements AccountDetailsAjaxHandlerService {

    public AccountDetails ajaxAccountUpdate(AccountDetails account, int relOrgId) {
        Session session = null;
        Transaction tx = null;
        Integer country = Integer.parseInt(account.getCountry());
        Integer industry = Integer.parseInt(account.getIndustry());
        if ("".equals(account.getState())) {
            account.setState(null);
        } else {
            Integer state = Integer.parseInt(account.getState());
            if (state < 0) {
                account.setState(null);
            }
        }
        System.out.println("country" + country);
        System.out.println("country" + industry);

        if (country < 0) {
            account.setCountry(null);
        }
        if (industry < 0) {
            account.setIndustry(null);
        }
        try {
            session = HibernateServiceLocator.getInstance().getSession();
            System.out.println("TRANSACTION TO UPDATE ACCOUNT := " + account);
            tx = session.getTransaction();
            tx.begin();
            session.update(account);
            tx.commit();
            //session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx != null) {
                tx = null;
            }
            if (session != null) {
                session.close();
                session = null;
            }
            return account;
        }
    }

    /**
     *
     * @param name the name to search for
     * @param id the id of the account trying to rename itself
     * @return boolean true if name exists and is not the current account name,
     * false if the account name does not exists or is the current account name.
     * @throws ServiceLocatorException
     */
    public boolean checkForAccountName(String name, int id) throws ServiceLocatorException {
        boolean exists = false;
        Session session = HibernateServiceLocator.getInstance().getSession();
        String q = "select distinct a.id, a.name from Account a where a.name=:accName";
        List<Object[]> likeAccounts = session.createQuery(q).setParameter("accName", name).list();
        if (likeAccounts.size() > 0) {
            for (Object[] a : likeAccounts) {
                //System.out.println("INSIDE AJAX CALL" +a);
                if (Integer.parseInt(a[0].toString()) == id) {
                    exists = false;
                } else {
                    exists = true;
                }
            }
        } else {
            exists = false;
        }
        try {
            // Closing hibernate session
            session.close();
            session = null;
        } catch (HibernateException he) {
            throw new ServiceLocatorException(he);
        } finally {
            if (session != null) {
                try {
                    session.close();
                    session = null;
                } catch (HibernateException he) {
                    he.printStackTrace();
                }
            }
        }
        //System.out.println("AJAX : CHECK FOR NAME:" + name + " IN USE :" + exists);
        return exists;
    }

    public boolean checkForAccountURL(String url, int id) throws ServiceLocatorException {
        boolean exists = false;
        Session session = HibernateServiceLocator.getInstance().getSession();
        String q = "select distinct a.id, a.url from Account a where a.url=:accurl";
        List<Object[]> likeAccounts = session.createQuery(q).setParameter("accurl", url).list();
        if (likeAccounts.size() > 0) {
            exists = true;
            System.out.println("url Exists");
        }
        try {
            // Closing hibernate session
            session.close();
            session = null;
        } catch (HibernateException he) {
            throw new ServiceLocatorException(he);
        } finally {
            if (session != null) {
                try {
                    session.close();
                    session = null;
                } catch (HibernateException he) {
                    he.printStackTrace();
                }
            }
        }
        System.out.println("AJAX : CHECK FOR URL:" + url + " IN USE :" + exists);
        return exists;
    }
}
