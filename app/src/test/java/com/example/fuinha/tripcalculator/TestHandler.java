package com.example.fuinha.tripcalculator;

import com.example.fuinha.tripcalculator.Entities.Trip;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestHandler {

    @Test
    public void performAllTests() {
        new KittyTest().performTests();
        new PersonTest().performTests();
        new TripTest().performTests();
    }

}