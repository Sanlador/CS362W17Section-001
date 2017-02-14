package org.cs362.dominion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RunGameTest {
    private PlayGame play;

    @Test
    public void Start(){
        play.main(null);
    }
}