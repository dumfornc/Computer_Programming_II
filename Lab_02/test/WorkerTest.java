import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    private Worker w1;

    @BeforeEach
    void setUp()
    {
        w1 = new Worker("123456", "Alice", "Smith", "Ms.", 1985, 20.0);
    }

    @Test
    void setHourlyPayRate()
    {
        w1.setHourlyPayRate(25.0);
        assertEquals(25.0, w1.getHourlyPayRate(), "Hourly pay rate should update correctly");
    }

    @Test
    void calculateWeeklyPay_NoOvertime()
    {
        double pay = w1.calculateWeeklyPay(40);
        assertEquals(800.0, pay, "Weekly pay for 40 hours at $20/hr should be $800");
    }

    @Test
    void calculateWeeklyPay_WithOvertime()
    {
        double pay = w1.calculateWeeklyPay(45);
        // 40 * 20 + 5 * (20 * 1.5) = 800 + 150 = 950
        assertEquals(950.0, pay, "Weekly pay with 5 overtime hours should include time and a half");
    }

    @Test
    void displayWeeklyPay()
    {
        // Redirect System.out to capture the output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        w1.displayWeeklyPay(42);

        String output = outContent.toString();
        assertTrue(output.contains("Alice worked 42.0 hours:"), "Output should mention hours worked");
        assertTrue(output.contains("2.0 at overtime pay"), "Output should include overtime details");
    }

    @Test
    void toJSON()
    {
        String json = w1.toJSON();
        assertTrue(json.contains("\"firstName\": \"Alice\""), "JSON should include first name");
        assertTrue(json.contains("\"hourlyPayRate\""), "JSON should include hourly pay rate");
    }

    @Test
    void toXML()
    {
        String xml = w1.toXML();
        assertTrue(xml.contains("<worker>"), "XML should start with <worker>");
        assertTrue(xml.contains("<firstName>Alice</firstName>"), "XML should include first name");
        assertTrue(xml.contains("</worker>"), "XML should end with </worker>");
    }

    @Test
    void testToString()
    {
        String str = w1.toString();
        assertTrue(str.contains("Alice"), "toString should contain the first name");
        assertTrue(str.contains("hourlyPayRate=20.0"), "toString should contain hourly pay rate");
    }
}
