import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest
{
    private Product p1;

    @BeforeEach
    void setUp()
    {
        p1 = new Product("000001", "D20", "A 20 sided die", 7.50);
    }

    @Test
    void setName()
    {
        p1.setName("D6");
        assertEquals("D6", p1.getName());
    }

    @Test
    void setDescription()
    {
        p1.setDescription("A standard six-sided die");
        assertEquals("A standard six-sided die", p1.getDescription());
    }

    @Test
    void setCost()
    {
        p1.setCost(3.25);
        assertEquals(3.25, p1.getCost());
    }

    @Test
    void toCSV()
    {
        String expected = "000001, D20, A 20 sided die, 7.50";
        assertEquals(expected, p1.toCSV());
    }

    @Test
    void toJSON()
    {
        String expected = """
                {
                  "id": "000001",
                  "name": "D20",
                  "description": "A 20 sided die",
                  "cost": 7.50
                }
                """;
        assertEquals(expected.trim(), p1.toJSON().trim());
    }

    @Test
    void toXML()
    {
        String expected = """
            <product>
              <id>000001</id>
              <name>D20</name>
              <description>A 20 sided die</description>
              <cost>7.50</cost>
            </product>
            """;
        assertEquals(expected.trim(), p1.toXML().trim());
    }

    @Test
    void testToString()
    {
        String result = p1.toString();
        assertTrue(result.contains("D20"));
        assertTrue(result.contains("A 20 sided die"));
        assertTrue(result.contains("7.5")); // cost prints without trailing zero in toString
    }

    @Test
    void testEquals()
    {
        Product p2 = new Product("000001", "D20", "A 20 sided die", 7.50);
        Product p3 = new Product("000002", "D6", "Six-sided die", 3.25);

        assertEquals(p1, p2);    // same data
        assertNotEquals(p1, p3); // different data
    }
}