package test.ModelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Model.TariffTaxInfo;

public class TariffTaxInfoTest {

  private TariffTaxInfo tariffTaxInfo;

  @Before
  public void setUp() {
    tariffTaxInfo = new TariffTaxInfo();
  }

  @Test
  public void testConstructorWithParams() {
    String phase = "Phase1";
    Double regularUnitsPrice = 10.5;
    Double peakUnitsPrice = 15.75;
    Double taxRate = 5.0;
    Double fixedCharges = 100.0;
    String customerType = "Residential";

    TariffTaxInfo tariff = new TariffTaxInfo(phase, regularUnitsPrice, peakUnitsPrice, taxRate, fixedCharges,
        customerType);

    assertEquals(phase, tariff.getPhase());
    assertEquals(regularUnitsPrice, tariff.getRegularUnitsPrice(), 0.01);
    assertEquals(peakUnitsPrice, tariff.getPeakUnitsPrice(), 0.01);
    assertEquals(taxRate, tariff.getTaxRate(), 0.01);
    assertEquals(fixedCharges, tariff.getFixedCharges(), 0.01);
    assertEquals(customerType, tariff.getCustomerType());
  }

  @Test
  public void testGetAndSetPhase() {
    String phase = "Phase1";
    tariffTaxInfo.setPhase(phase);
    assertEquals(phase, tariffTaxInfo.getPhase());
  }

  @Test
  public void testGetAndSetCustomerType() {
    String customerType = "Commercial";
    tariffTaxInfo.setCustomerType(customerType);
    assertEquals(customerType, tariffTaxInfo.getCustomerType());
  }

  @Test
  public void testGetAndSetRegularUnitsPrice() {
    Double regularUnitsPrice = 10.5;
    tariffTaxInfo.setRegularUnitsPrice(regularUnitsPrice);
    assertEquals(regularUnitsPrice, tariffTaxInfo.getRegularUnitsPrice(), 0.01);
  }

  @Test
  public void testGetAndSetPeakUnitsPrice() {
    Double peakUnitsPrice = 15.75;
    tariffTaxInfo.setPeakUnitsPrice(peakUnitsPrice);
    assertEquals(peakUnitsPrice, tariffTaxInfo.getPeakUnitsPrice(), 0.01);
  }

  @Test
  public void testGetAndSetTaxRate() {
    Double taxRate = 5.0;
    tariffTaxInfo.setTaxRate(taxRate);
    assertEquals(taxRate, tariffTaxInfo.getTaxRate(), 0.01);
  }

  @Test
  public void testGetAndSetFixedCharges() {
    Double fixedCharges = 100.0;
    tariffTaxInfo.setFixedCharges(fixedCharges);
    assertEquals(fixedCharges, tariffTaxInfo.getFixedCharges(), 0.01);
  }

  @Test
  public void testDefaultConstructor() {
    assertNull(tariffTaxInfo.getPhase());
    assertNull(tariffTaxInfo.getCustomerType());
    assertNull(tariffTaxInfo.getRegularUnitsPrice());
    assertNull(tariffTaxInfo.getPeakUnitsPrice());
    assertNull(tariffTaxInfo.getTaxRate());
    assertNull(tariffTaxInfo.getFixedCharges());
  }

  @Test
  public void testEquality() {
    TariffTaxInfo tariff1 = new TariffTaxInfo("Phase1", 10.5, 15.75, 5.0, 100.0, "Residential");
    TariffTaxInfo tariff2 = new TariffTaxInfo("Phase1", 10.5, 15.75, 5.0, 100.0, "Residential");

    assertEquals(tariff1.getPhase(), tariff2.getPhase());
    assertEquals(tariff1.getRegularUnitsPrice(), tariff2.getRegularUnitsPrice(), 0.01);
    assertEquals(tariff1.getPeakUnitsPrice(), tariff2.getPeakUnitsPrice(), 0.01);
    assertEquals(tariff1.getTaxRate(), tariff2.getTaxRate(), 0.01);
    assertEquals(tariff1.getFixedCharges(), tariff2.getFixedCharges(), 0.01);
    assertEquals(tariff1.getCustomerType(), tariff2.getCustomerType());
  }
}
