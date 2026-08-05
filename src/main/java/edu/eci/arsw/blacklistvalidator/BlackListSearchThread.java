package edu.eci.arsw.blacklistvalidator;
import java.util.LinkedList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 *
 * @author juanb
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
