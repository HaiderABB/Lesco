package test.ModelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Model.Meter;

import java.math.BigInteger;

public class MeterTest {

  private Meter meter;

  @Before
  public void setUp() {
    meter = new Meter(new BigInteger("3520105043723"));
  }

  @Test
  public void testConstructorWithBigIntegerCNIC() {
    assertEquals(new BigInteger("3520105043723"), meter.getCNIC());
    assertEquals(1, meter.getNumber());
  }

  @Test
  public void testConstructorWithStringCNICAndNumber() {
    Meter m = new Meter("3520105043723", 2);
    assertEquals(new BigInteger("3520105043723"), m.getCNIC());
    assertEquals(2, m.getNumber());
  }

  @Test
  public void testGetCNIC() {
    assertEquals(new BigInteger("3520105043723"), meter.getCNIC());
  }

  @Test
  public void testGetNumber() {
    assertEquals(1, meter.getNumber());
  }

  @Test
  public void testAddMeter_Success() {
    boolean result = meter.addMeter();
    assertTrue(result);
    assertEquals(2, meter.getNumber());
  }

  @Test
  public void testAddMeter_Failure() {
    meter.addMeter();
    meter.addMeter();
    boolean result = meter.addMeter();
    assertFalse(result);
    assertEquals(3, meter.getNumber());
  }

  @Test
  public void testSetNumber() {
    meter.setNumber(5);
    assertEquals(5, meter.getNumber());
  }
}
