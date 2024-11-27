package Model;

public class TariffTaxInfo {

  protected String Phase;
  protected Double RegularUnitsPrice;
  protected Double PeakUnitsPrice;
  protected Double TaxRate;
  protected Double FixedCharges;
  protected String CustomerType;

  public TariffTaxInfo() {

  }

  public TariffTaxInfo(String Phase, Double RegularUnitsPrice, Double PeakUnitsPrice, Double TaxRate,
      Double FixedCharges,
      String CustomerType) {
    this.Phase = Phase;
    this.RegularUnitsPrice = RegularUnitsPrice;
    this.PeakUnitsPrice = PeakUnitsPrice;
    this.TaxRate = TaxRate;
    this.FixedCharges = FixedCharges;
    this.CustomerType = CustomerType;
  }

  public String getPhase() {
    return Phase;
  }

  public String getCustomerType() {
    return CustomerType;
  }

  public void setCustomerType(String phase) {
    this.CustomerType = phase;
  }

  public void setPhase(String phase) {
    this.Phase = phase;
  }

  public Double getRegularUnitsPrice() {
    return RegularUnitsPrice;
  }

  public void setRegularUnitsPrice(Double regularUnitsPrice) {
    this.RegularUnitsPrice = regularUnitsPrice;
  }

  public Double getPeakUnitsPrice() {
    return PeakUnitsPrice;
  }

  public void setPeakUnitsPrice(Double peakUnitsPrice) {
    this.PeakUnitsPrice = peakUnitsPrice;
  }

  public Double getTaxRate() {
    return TaxRate;
  }

  public void setTaxRate(Double taxRate) {
    this.TaxRate = taxRate;
  }

  public Double getFixedCharges() {
    return FixedCharges;
  }

  public void setFixedCharges(Double fixedCharges) {
    this.FixedCharges = fixedCharges;
  }

}
