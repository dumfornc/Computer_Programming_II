import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaryWorkerTest {

    private SalaryWorker sw1;

    @BeforeEach
    void setUp()
    {
        sw1 = new SalaryWorker("654321", "Bob", "Jones", "Mr.", 1990, 52000.0);
    }

    @Test
    void testGetAnnualSalary()
    {
        assertEquals(52000.0, sw1.getAnnualSalary());
    }

    @Test
    void testSetAnnualSalary()
    {
        sw1.setAnnualSalary(60000.0);
        assertEquals(60000.0, sw1.getAnnualSalary());
    }

    @Test
    void testCalculateWeeklyPay()
    {
        double weeklyPay = sw1.calculateWeeklyPay(40);
        assertEquals(1000.0, weeklyPay, "Weekly pay should be annual salary / 52");
    }

    @Test
    void testCalculateWeeklyPay_IgnoresHoursWorked()
    {
        double pay40 = sw1.calculateWeeklyPay(40);
        double pay60 = sw1.calculateWeeklyPay(60);
        assertEquals(pay40, pay60, "Weekly pay for salaried worker should not depend on hours worked");
    }

    @Test
    void testDisplayWeeklyPay()
    {
        // Capture console output
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        // Call method
        sw1.displayWeeklyPay(45);

        // Expected output
        String expected = """
            Bob worked 45.00 hours:
            Earning 1 week's worth of their annual salary: $1000.00
            """;

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void testToJSON()
    {
        String expected = """
            {
              "id": "654321",
              "firstName": "Bob",
              "lastName": "Jones",
              "title": "Mr.",
              "yob": 1990,
              "annualSalary": .2f
            }
            """.formatted(52000.0);

        assertEquals(expected, sw1.toJSON());
    }

    @Test
    void testToXML()
    {
        String expected = """
            <worker>
              <id>654321</id>
              <firstName>Bob</firstName>
              <lastName>Jones</lastName>
              <title>Mr.</title>
              <yob>1990</yob>
              <annualSalary>52000.00</annualSalary>
            </worker>
            """;

        assertEquals(expected, sw1.toXML());
    }

    @Test
    void testToString()
    {
        String expected = "Person{" +
            "firstName='Bob'" +
            ", lastName='Jones'" +
            ", ID='654321'" +
            ", title='Mr.'" +
            ", YoB=1990'" +
            ", annualSalary=52000.0" +
            '}';

        assertEquals(expected, sw1.toString());
    }
}
