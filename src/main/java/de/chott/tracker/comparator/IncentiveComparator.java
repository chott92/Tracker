/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.comparator;

import de.chott.tracker.model.Game;
import de.chott.tracker.model.Incentive;
import java.util.Comparator;

/**
 *
 * @author cot
 */
public class IncentiveComparator implements Comparator<Incentive>{

    @Override
    public int compare(Incentive o1, Incentive o2) {
        Game g1 = o1.getGame();
        Game g2 = o2.getGame();
        if (g1.getScheduleOrder()==g2.getScheduleOrder()){
            return o1.getIncentiveName().compareTo(o2.getIncentiveName());
        } else{
            return Integer.compare(g1.getScheduleOrder(), g2.getScheduleOrder());
        }
    }
    
}
