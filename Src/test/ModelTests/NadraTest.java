package test.ModelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import Model.Nadra;
import Model.NadraData;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NadraTest {

  private Nadra nadra;

  @Before
  public void setUp() {
    nadra = new Nadra();
    nadra.Data.clear();
  }

  @Test
  public void testIssueCNIC() {
    BigInteger newCNIC = new BigInteger("3520105043726");
    nadra.IssueCNIC(newCNIC);
    assertEquals(1, nadra.Data.size());
    assertEquals(newCNIC, nadra.Data.get(0).getCNIC());
  }

  @Test
  public void testCheckCNIC_NewCNIC() {
    BigInteger newCNIC = new BigInteger("3520105043727");
    boolean result = nadra.checkCNIC(newCNIC);
    assertTrue(result);
    assertEquals(1, nadra.Data.size());
    assertEquals(newCNIC, nadra.Data.get(0).getCNIC());
  }

  @Test
  public void testCheckCNIC_ExistingCNIC() {
    BigInteger existingCNIC = new BigInteger("3520105043723");
    nadra.IssueCNIC(existingCNIC);
    boolean result = nadra.checkCNIC(existingCNIC);
    assertTrue(result);
    assertEquals(1, nadra.Data.size());
    assertEquals(existingCNIC, nadra.Data.get(0).getCNIC());
  }

  @Test
  public void testGetExpired() {
    nadra.IssueCNIC(new BigInteger("3520105043723"));
    nadra.IssueCNIC(new BigInteger("3520114062474"));
    nadra.IssueCNIC(new BigInteger("3520114062475"));

    ArrayList<NadraData> expiring = nadra.getExpired();
    assertEquals(3, expiring.size()); // Assuming the expiry date is more than 30 days away
  }

  @Test
  public void testUpdateExpiry() {
    BigInteger cnic = new BigInteger("3520105043723");
    nadra.IssueCNIC(cnic);
    nadra.updateExpiry(cnic);

    NadraData updatedData = nadra.Data.get(0);
    assertNotNull(updatedData);
    assertNotNull(updatedData.getExpiryDate());
    assertTrue(LocalDate.parse(updatedData.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yy"))
        .isAfter(LocalDate.now()));
  }

}
