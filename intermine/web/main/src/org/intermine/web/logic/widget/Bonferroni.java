package org.intermine.web.logic.widget;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.HashMap;
import java.util.Iterator;

import java.math.BigDecimal;


/**
 * See online documentation for an in depth description of error correction and bonferroni.
 * Briefly, the p-values are adjusted (multiple hypothesis test correction) by multiplying
 * the original value by the number of tests performed.
 * @author Julie Sullivan
 */
public class Bonferroni implements ErrorCorrection
{
    private HashMap<String, BigDecimal> originalMap = new HashMap<String, BigDecimal>();
    private HashMap<String, BigDecimal> adjustedMap = new HashMap<String, BigDecimal>();
    private BigDecimal numberOfTests;
    private BigDecimal alpha = new BigDecimal(0.05);
    private BigDecimal alphaPerTest;

    
    /**
     * @param originalMap HashMap of go terms and their p-value
     */
    public Bonferroni(HashMap<String, BigDecimal> originalMap) {
        this.originalMap = originalMap;
        numberOfTests = new BigDecimal(originalMap.size());
        alphaPerTest = alpha.divide(numberOfTests);
    }

    /**
     * @param max maximum value to display
     */
    @SuppressWarnings("unchecked")
    public void calculate(Double max) {

        for (Iterator iter = originalMap.keySet().iterator(); iter.hasNext();) {

            // get original values
            String label = (String) iter.next();
            BigDecimal p = new BigDecimal("" + originalMap.get(label));
            
            // calc new value
            // (alpha / number of tests) + p
            BigDecimal adjustedP = p.add(alphaPerTest);

            // don't store values >= maxValue
            if (adjustedP.doubleValue() < max.doubleValue()) {
                adjustedMap.put(label, adjustedP);
            }
        }
    }

    /**
     * @return adjusted map
     */
    public HashMap<String, BigDecimal> getAdjustedMap() {
        return adjustedMap;
    }
}
