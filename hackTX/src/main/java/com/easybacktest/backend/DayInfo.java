/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easybacktest.backend;

import java.util.Date;

/**
 *
 * @author simplyianm
 */
public class DayInfo {

    private final Date date;

    private final double open;

    private final double last;

    private double localMax;

    private double localMin;

    private double dropPercent;

    private double risePercent;

    private double shares;

    private double cash;

    public DayInfo(Date date, double open, double last) {
        this.date = date;
        this.open = open;
        this.last = last;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    /**
     * Get previous last
     *
     * @return
     */
    public double getClose() {
        return last;
    }

    public double getLocalMax() {
        return localMax;
    }

    public double getLocalMin() {
        return localMin;
    }

    public double getDropPercent() {
        return dropPercent;
    }

    public double getRisePercent() {
        return risePercent;
    }

    public double getShares() {
        return shares;
    }

    public double getCash() {
        return cash;
    }

    public double getValue() {
        return cash + shares * open;
    }

    public void setCalculated(double max, double min, double dropPercent, double risePercent) {
        localMax = max;
        localMin = min;
        this.dropPercent = dropPercent;
        this.risePercent = risePercent;
    }

    public void setSharesCash(double shares, double cash) {
        this.shares = shares;
        this.cash = cash;
    }

}
