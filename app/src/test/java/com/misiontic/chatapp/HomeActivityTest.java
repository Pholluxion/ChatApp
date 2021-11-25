package com.misiontic.chatapp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class HomeActivityTest extends TestCase {

    private HomeActivity home;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        home = new HomeActivity();
    }

    @Test
    public void testSuma() {
        assertEquals(5,home.suma(2,3));
    }
}