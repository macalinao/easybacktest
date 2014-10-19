/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easybacktest.backend.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simplyianm
 */
public class Signal {

    private final boolean buy;

    private final double changeCondition;

    public Signal(boolean buy, double changeCondition) {
        this.buy = buy;
        this.changeCondition = changeCondition;
    }

    public boolean isBuy() {
        return buy;
    }

    public boolean isSell() {
        return !buy;
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
    public static Signal fromString(String line) {
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

        return new Signal(actionStr.equals("buy"), chgPercent);
    }

}
