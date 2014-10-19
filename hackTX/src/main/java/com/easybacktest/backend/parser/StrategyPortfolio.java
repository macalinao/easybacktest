/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easybacktest.backend.parser;

import com.easybacktest.backend.DayInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author simplyianm
 */
public class StrategyPortfolio {

    private int shares;

    private double cash;

    private List<PortfolioEvent> events = new ArrayList<>();

    public StrategyPortfolio(double cash) {
        shares = 0;
        this.cash = cash;
    }

    public double getShares() {
        return shares;
    }

    public double getCash() {
        return cash;
    }

    public List<PortfolioEvent> getEvents() {
        return events;
    }

    public int allInBuy(DayInfo day) {
        double cost = day.getOpen();
        if (cost == 0) {
            return 0;
        }
        int amt = (int) (cash / cost);

        // Prevent null buy
        if (amt == 0) {
            return 0;
        }

        shares += amt;
        cash -= amt * cost;
        PortfolioEvent e = new PortfolioEvent(true, amt, day);
        events.add(e);
        return amt;
    }

    public int allInSell(DayInfo day) {
        int amt = shares;

        // prevent null buy
        if (amt == 0) {
            return 0;
        }

        double money = amt * day.getOpen();
        shares = 0;
        cash += money; // CASH$Money
        PortfolioEvent e = new PortfolioEvent(false, amt, day);
        events.add(e);
        return amt;
    }

}
