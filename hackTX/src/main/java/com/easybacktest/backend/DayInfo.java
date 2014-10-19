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

    public DayInfo(Date date, double open) {
        this.date = date;
        this.open = open;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

}
