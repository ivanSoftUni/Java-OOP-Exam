package football;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FootballTeamTests {

    private FootballTeam footballTeam;
    private Footballer footballer;
    private Footballer footballer2;
    private Footballer footballer3;

    @Before
    public void setup() {
        footballTeam = new FootballTeam("Levski", 2);
        footballer = new Footballer("Ivan");
        footballer2 = new Footballer("Pesho");
        footballer3 = new Footballer("Gosho");
    }

    @Test(expected = NullPointerException.class)
    public void test_SetName_WithNull() {
        FootballTeam footballTeam1 = new FootballTeam(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_SetVacantPositions_WithBelowZero() {
        FootballTeam footballTeam1 = new FootballTeam("Ludogorec", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AddFootballer_WhenCapacityIsFull(){
        assertEquals(0, footballTeam.getCount());
        footballTeam.addFootballer(footballer);
        footballTeam.addFootballer(footballer);
        footballTeam.addFootballer(footballer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_RemoveFootballer_WhenNameDoesNotExist(){
      footballTeam.addFootballer(footballer);
      assertEquals(1, footballTeam.getCount());
      footballTeam.removeFootballer("Pesho");
    }

    @Test
    public void test_RemoveFootballer_Success(){
        footballTeam.addFootballer(footballer);
        assertEquals(1, footballTeam.getCount());
        footballTeam.removeFootballer("Ivan");
        assertEquals(0, footballTeam.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_FootballerForSale_WhenNameDoesNotExist(){
        footballTeam.addFootballer(footballer);
        assertEquals(1, footballTeam.getCount());
        footballTeam.footballerForSale("Pesho");
    }

    @Test
    public void test_FootballerForSale_Success(){
        footballTeam.addFootballer(footballer);
        assertEquals(1, footballTeam.getCount());
        assertFalse(footballTeam.footballerForSale("Ivan").isActive());
    }

    @Test
    public void test_getStatistics(){
        String expectedOutput = "The footballer Ivan, Pesho is in the team Levski.";
        footballTeam.addFootballer(footballer);
        footballTeam.addFootballer(footballer2);
        String actualOutput = footballTeam.getStatistics();
        assertEquals(expectedOutput,actualOutput);
    }

}
