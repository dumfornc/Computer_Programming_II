public class SalaryWorker extends Worker
{
    double annualSalary;

    /**
     * Constructor that creates a salary worker object
     * @param firstName - A string holding a person's first name
     * @param lastName - A string holding a person's last name
     * @param ID - A unique 6 digit string of numbers
     * @param title - A string holding the honorific or formal title of a person
     * @param YoB - An integer in the range 1940 - 2010
     * @param annualSalary - A double representing the annual salary of a salaried worker
     */
    public SalaryWorker(String ID, String firstName, String lastName, String title, int YoB, double annualSalary)
    {
        // Hourly pay is salary divided by weeks in a year divided by hours in a week
        super(ID, firstName, lastName, title, YoB, annualSalary / 52 / 40);
        this.annualSalary = annualSalary;
    }

    /**
     * Gets the annual salary of the salaried worker this object represents
     * @return - Double the number of dollars per year the worker this object represents is paid
     */
    public double getAnnualSalary() {
        return annualSalary;
    }

    /**
     * Sets the salary of the salaried worker this instance of the object represents
     * @param annualSalary - Double
     */
    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    /**
     * Calculates the amount of pay a worker earned in a week by dividing their annual salary by 52
     * @param hoursWorked - The number of hours the worker worked. Not used, retained for polymorphism
     * @return Double - The number of USD the worker earned in the week
     */
    @Override
    public double calculateWeeklyPay(double hoursWorked)
    {
        return this.annualSalary / 52;
    }

    /**
     * Outputs to the console the number of hours worked and pay due
     * @param hoursWorked - The number of hours the worker worked. Not used, retained for polymorphism
     */
    public void displayWeeklyPay(double hoursWorked)
    {
        String outputFormat = """
            %s worked %.2f hours:
            Earning 1 week's worth of their annual salary: $%.2f
            """;

        System.out.print(outputFormat.formatted(super.getFirstName(), hoursWorked, this.calculateWeeklyPay(hoursWorked)));
    }

    /**
     * Returns salary worker data formatted like a line in a csv file
     * @return - String "ID, firstName, lastName, title, YoB, annualSalary"
     */
    @Override
    public String toCSV()
    {
        return "%s, %s, %s, %s, %d, %.2f".formatted(super.getID(), super.getFirstName(), super.getLastName(), super.getTitle(), super.getYoB(), this.annualSalary);
    }

    /**
     * Returns salary worker data formatted like a JSON object
     * @return - String of JSON data
     */
    @Override
    public String toJSON()
    {
        String jsonFormat = """
            {
              "id": "%s",
              "firstName": "%s",
              "lastName": "%s",
              "title": "%s",
              "yob": %d,
              "annualSalary": .2f
            }
            """;

        return jsonFormat.formatted(super.getID(), super.getFirstName(), super.getLastName(), super.getTitle(), super.getYoB(), this.annualSalary);
    }

    /**
     * Returns salary worker data formatted like an XML object
     * @return - String of xml data
     */
    @Override
    public String toXML()
    {
        String xmlFormat = """
            <worker>
              <id>%s</id>
              <firstName>%s</firstName>
              <lastName>%s</lastName>
              <title>%s</title>
              <yob>%d</yob>
              <annualSalary>%.2f</annualSalary>
            </worker>
            """;

        return xmlFormat.formatted(super.getID(), super.getFirstName(), super.getLastName(), super.getTitle(), super.getYoB(), this.annualSalary);
    }

    /**
     * Overrides salary worker string return to be more legible
     * @return - String of salary worker data
     */
    @Override
    public String toString()
    {
        return "Person{" +
            "firstName='" + super.getFirstName() + '\'' +
            ", lastName='" + super.getLastName() + '\'' +
            ", ID='" + super.getID() + '\'' +
            ", title='" + super.getTitle() + '\'' +
            ", YoB=" + super.getYoB() + '\'' +
            ", annualSalary=" + this.annualSalary +
            '}';
    }
}
