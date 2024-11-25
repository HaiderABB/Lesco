package test.ModelTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import Model.Meter;
import Model.MeterInfo;

public class MeterInfoTest {

  private MeterInfo meterInfo;

  @Before
  public void setUp() {
    meterInfo = new MeterInfo();
    meterInfo.Meters.clear();
  }

  @Test
  public void testAddMeter_NewMeter() {
    BigInteger cnic = new BigInteger("3520105043723");
    boolean result = meterInfo.addMeter(cnic);
    assertTrue(result);
    assertEquals(1, meterInfo.getMeterNumber(cnic));
  }

  @Test
  public void testAddMeter_ExistingMeter() {
    BigInteger cnic = new BigInteger("3520105043723");
    meterInfo.addMeter(cnic);
    boolean result = meterInfo.addMeter(cnic);
    assertTrue(result);
    assertEquals(2, meterInfo.getMeterNumber(cnic));
  }

  @Test
  public void testAddMeter_MaxLimitReached() {
    BigInteger cnic = new BigInteger("3520105043723");
    meterInfo.addMeter(cnic);
    meterInfo.addMeter(cnic);
    meterInfo.addMeter(cnic);
    boolean result = meterInfo.addMeter(cnic);
    assertFalse(result);
  }

  @Test
  public void testRemoveMeter() {
    BigInteger cnic = new BigInteger("3520105043723");
    meterInfo.addMeter(cnic);
    meterInfo.addMeter(cnic);
    meterInfo.removeMeter(cnic);
    assertEquals(1, meterInfo.getMeterNumber(cnic));
  }

  @Test
  public void testRemoveMeter_LastMeter() {
    BigInteger cnic = new BigInteger("3520105043723");
    meterInfo.addMeter(cnic);
    meterInfo.removeMeter(cnic);
    assertEquals(0, meterInfo.getMeterNumber(cnic));
  }

  @Test
  public void testGetMeterNumber() {
    BigInteger cnic = new BigInteger("3520105043723");
    meterInfo.addMeter(cnic);
    meterInfo.addMeter(cnic);
    int meterNumber = meterInfo.getMeterNumber(cnic);
    assertEquals(2, meterNumber);
  }

  @Test
  public void testGetMeterNumber_CNICNotFound() {
    BigInteger cnic = new BigInteger("3520105043723");
    int meterNumber = meterInfo.getMeterNumber(cnic);
    assertEquals(0, meterNumber);
  }

  @Test
  public void testMeterAddLimit() {
    BigInteger cnic = new BigInteger("3520105043723");
    meterInfo.addMeter(cnic);
    meterInfo.addMeter(cnic);
    meterInfo.addMeter(cnic);
    boolean result = meterInfo.addMeter(cnic);
    assertFalse(result);
  }
}
