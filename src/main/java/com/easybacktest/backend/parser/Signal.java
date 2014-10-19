/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easybacktest.backend.parser;

import com.easybacktest.backend.DayInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author simplyianm
 */
public class Signal {

    private final boolean buy;

    private final double magnitude;

    private final double changeCondition;
    
    private final HashMap<String, Set<String>> keyword;
    
    public Signal(boolean buy, double magnitude, double changeCondition) {
        this.buy = buy;
        this.magnitude = magnitude;
        this.changeCondition = changeCondition;
        
        this.keyword = new HashMap<String, Set<String>>();
        this.keyword.put("buy", new HashSet<String>(Arrays.asList("buy","get","purchase","long")));
        this.keyword.put("sell", new HashSet<String>(Arrays.asList("sell","throw","dump","short")));
    }

    public boolean isBuy() {
        return buy;
    }

    public boolean isSell() {
        return !buy;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getChangeCondition() {
        return changeCondition;
    }

    /**
     * Generates a signal from a string.
     *
     * Examples: buy when it drops 20; sell when it rises 10
     *
     * @param line
     * @return
     */
    public Signal fromString(String line) {
        line = line.toLowerCase();
        String[] pts = line.split(" ");
        List<String> ptsL = new ArrayList<>();
        for (String pt : pts) {
            if (!pt.trim().equals("")) {
                ptsL.add(pt);
            }
        }

        String actionStr = ptsL.get(0);
        boolean drops = ptsL.get(ptsL.size() - 2).equals("drops");
        String changeStr = ptsL.get(ptsL.size() - 1);
        double chgPercent = 0.01 * Double.parseDouble(changeStr.substring(0, changeStr.length() - 1));
        if (drops) {
            chgPercent *= -1;
        }

        String magnitudeS = ptsL.get(1);
        double magnitude = 1;
        if (magnitudeS.equals("all")) {
            magnitude = 1;
        } else if (magnitudeS.equals("half")) {
            magnitude = 0.5;
        } else if (magnitudeS.equals("several")) {
            magnitude = 0.25;
        } else {
            try {
                magnitude = Double.parseDouble(magnitudeS.substring(0, magnitudeS.length() - 1)) * 0.01;
            } catch (NumberFormatException ex) {
            }
        }
        
        
        if(this.keyword.get("buy").contains(actionStr))
        	return new Signal(true, magnitude, chgPercent);
        else if (this.keyword.get("sell").contains(actionStr))
        	return new Signal(false, magnitude, chgPercent);
        else
        	return null; // bad syntax
    }

}
