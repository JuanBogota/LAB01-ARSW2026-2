/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     * 
     * @author Juan Daniel Bogotá
     * @author Carlos Rojas
     */
    public List<Integer> checkHost(String ipaddress, int n){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int total = skds.getRegisteredServersCount();

        int chunk = total/n;
        BlackListSearchThread[] threads = new BlackListSearchThread[n];

        for (int i = 0; i < n; i++) {
            int start = i * chunk;
            int end = (i == n - 1) ? total : start + chunk;
            threads[i] = new BlackListSearchThread(start, end, ipaddress, skds);
            threads[i].start();
        }

        int ocurrencesCount=0;
        int checkedListsCount=0;
        
        for (BlackListSearchThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            blackListOcurrences.addAll(t.getOccurrences());
            checkedListsCount += t.getCheckedCount();
            ocurrencesCount += t.getOccurrences().size();
        }

        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}",
                new Object[]{checkedListsCount, skds.getRegisteredServersCount()});

        return blackListOcurrences;
    
    }

}
