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
}