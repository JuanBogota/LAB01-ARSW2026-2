package edu.eci.arsw.blacklistvalidator;
import java.util.LinkedList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * A worker thread responsible for searching a single segment of the
 * registered black list servers for a given host IP address.
 * 
 * This class is the core building block used to parallelize
 * {@link HostBlackListsValidator#checkHost}: instead of scanning every
 * black list sequentially, the total search space is split into N
 * segments, and one {@code BlackListSearchThread} is assigned to each
 * segment, all of them searching concurrently.
 * 
 * Each thread operates over the half-open interval {@code [startIndex, endIndex)}
 * and reports its own results ({@link #getOccurrences()} and
 * {@link #getCheckedCount()}) once it has finished running, so the caller
 * can aggregate them after joining all threads.
 *
 * @author Juan Daniel Bogotá
 * @author Carlos Rojas
 * @version 1.0
 */
public class BlackListSearchThread extends Thread{

    private final int startIndex;
    private final int endIndex;
    private final String ipaddress;
    private final HostBlacklistsDataSourceFacade skds;
    private final List<Integer> ocurrences = new LinkedList<>();
    private int checkedCount = 0;
  
    
    public BlackListSearchThread(int startIndex, int endIndex, String ipaddress, HostBlacklistsDataSourceFacade skds){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipaddress = ipaddress;
        this.skds = skds;
    }
    
    @Override
    public void run(){
        for (int i = startIndex; i < endIndex; i++) {
            checkedCount++;
            if (skds.isInBlackListServer(i, ipaddress)) {
                ocurrences.add(i);
            }
        }
    }

    public List<Integer> getOccurrences() {
        return ocurrences;
    }

    public int getCheckedCount() {
        return checkedCount;
    }

}
