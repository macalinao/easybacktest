/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easybacktest.backend.parser;

import com.easybacktest.backend.BLPIntegration;
import com.easybacktest.backend.DayInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simplyianm
 */
public class Strategy {

    private List<Signal> signals;

    public Strategy(List<String> desc) {
        signals = new ArrayList<>();
        for (String line : desc) {
            signals.add(Signal.fromString(line));
        }
    }

    public StrategyPortfolio execute(String stock, double initialCapital) {
        List<DayInfo> data = BLPIntegration.getDailyData(stock + " US Equity");

        StrategyPortfolio sp = new StrategyPortfolio(initialCapital);

        double localMax = data.get(0).getOpen();
        double localMin = data.get(0).getOpen();

        for (DayInfo day : data) {
            double price = day.getOpen();
            if (price == 0) {
                continue;
            }
            localMax = Math.max(localMax, price);
            localMin = Math.min(localMin, price);

            double dropPercent = (localMax - price) / localMax;
            double risePercent = (price - localMin) / localMin;
            day.setCalculated(localMax, localMin, dropPercent, risePercent);
            day.setSharesCash(sp.getShares(), sp.getCash());

            for (Signal sig : signals) {
                if (sig.getChangeCondition() < 0 && dropPercent > -sig.getChangeCondition()) {
                    if (sig.isBuy()) {
                        sp.allInBuy(day);
                    } else {
                        sp.allInSell(day);
                    }
                    localMax = price;
                    break;
                } else if (sig.getChangeCondition() > 0 && risePercent > sig.getChangeCondition()) {
                    if (sig.isBuy()) {
                        sp.allInBuy(day);
                    } else {
                        sp.allInSell(day);
                    }
                    localMin = price;
                    break;
                }
            }
        }

        return sp;
    }
}
