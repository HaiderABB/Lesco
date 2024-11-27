package test.ModelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Model.NadraData;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

public class NadraDataTest {

  private NadraData nadraData;

  @Before
  public void setUp() {
    nadraData = new NadraData();
  }

  @Test
  public void testConstructorWithParams() {
    String issueDate = "29/10/24";
    String expiryDate = "13/11/24";
    BigInteger cnic = new BigInteger("3520105043723");

    NadraData nadra = new NadraData(issueDate, expiryDate, cnic);

    assertEquals(issueDate, nadra.getIssueDate());
    assertEquals(expiryDate, nadra.getExpiryDate());
    assertEquals(cnic, nadra.getCNIC());
  }

  @Test
  public void testConstructorWithCNIC() {
    BigInteger cnic = new BigInteger("3520105043723");
    NadraData nadra = new NadraData(cnic);

    assertEquals(cnic, nadra.getCNIC());
    assertNull(nadra.getIssueDate());
    assertNull(nadra.getExpiryDate());
  }

  @Test
  public void testSetAndGetIssueDate() {
    String issueDate = "29/10/24";
    nadraData.setIssueDate(issueDate);
    assertEquals(issueDate, nadraData.getIssueDate());
  }

  @Test
  public void testSetAndGetExpiryDate() {
    String expiryDate = "13/11/24";
    nadraData.setExpiryDate(expiryDate);
    assertEquals(expiryDate, nadraData.getExpiryDate());
  }

  @Test
  public void testSetAndGetCNIC() {
    BigInteger cnic = new BigInteger("3520105043723");
    nadraData.setCNIC(cnic);
    assertEquals(cnic, nadraData.getCNIC());
  }

  @Test
  public void testDefaultConstructor() {
    assertNull(nadraData.getIssueDate());
    assertNull(nadraData.getExpiryDate());
    assertNull(nadraData.getCNIC());
  }
}
