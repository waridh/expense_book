package com.example.waridh_expbook;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExpenseUnitTest {
    private Expense cUT;

    public ExpenseUnitTest() {
        this.cUT = new Expense();
    }
    @Test
    public void regexCheck() {
        assertEquals(true, cUT.monthStartedCheck("2023-11"));
        assertEquals(false, cUT.monthStartedCheck("asldkj"));
        assertEquals(true, cUT.monthStartedCheck("2012-12"));
        assertEquals(false, cUT.monthStartedCheck("2000-13"));
        assertEquals(false, cUT.monthStartedCheck("2000-00"));
        assertEquals(false, cUT.monthStartedCheck("2000-0"));
    }

    @Test
    public void regexCheck2() {
        assertEquals(false, cUT.monthlyChargeCheck("2023-11"));
        assertEquals(false, cUT.monthlyChargeCheck("asldkj"));
        assertEquals(false, cUT.monthlyChargeCheck("2012-12"));
        assertEquals(false, cUT.monthlyChargeCheck("2000-13"));
        assertEquals(false, cUT.monthlyChargeCheck("2000-00"));
        assertEquals(false, cUT.monthlyChargeCheck("2000-0"));
        assertEquals(true, cUT.monthlyChargeCheck("0.52"));
        assertEquals(true, cUT.monthlyChargeCheck("5.30"));
        assertEquals(true, cUT.monthlyChargeCheck("5"));
        assertEquals(true, cUT.monthlyChargeCheck("0"));
    }
}