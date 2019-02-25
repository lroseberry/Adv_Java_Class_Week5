package com.origamisoftware.teach.advanced.util;
/**
 * Enum of intervals to get a stock quote
 */

    public enum IntervalEnums {
        HOURLY(1), HALF_DAY(12), DAILY(24);

        private int numVal;

        /**
        * Constructs a new instance
        * @param numVal representation for an {@code enum} element
        */
        IntervalEnums(int numVal) {

            this.numVal = numVal;
        }

        /**
        * @return the numVal associated with the enum element
        */
        public int getNumVal() {

            return numVal;
        }
    }