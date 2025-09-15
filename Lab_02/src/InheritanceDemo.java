import java.util.ArrayList;

public class InheritanceDemo
{
    public static void main(String[] args)
    {
        ArrayList<Worker> employees = new ArrayList<Worker>();

        employees.add(new Worker("000001", "Alex", "Bertrum", "Mr.", 2000, 21.30));
        employees.add(new Worker("000002", "Clark", "Duran", "Dr.", 1976, 25.00));
        employees.add(new Worker("000003", "Erina", "Feckle", "Mrs.", 2003, 20.50));

        employees.add(new SalaryWorker("000004", "Gertrude", "Hickson", "Ms.", 1993, 44500.00));
        employees.add(new SalaryWorker("000005", "Iggy", "Jackson", "Prof.", 1956, 77490.00));
        employees.add(new SalaryWorker("000006", "Kieran", "Larders", "Sr.", 1999, 50000.00));

        String rowFormat = "\n%-8s%-25s%-25s%-10.2f";

        for (int week = 1; week < 4; week++)
        {
            int hoursWorked;

            if (week == 1 || week == 3)
            {
                hoursWorked = 40;
            } else
            {
                hoursWorked = 50;
            }

            System.out.print("\n\nWeek " + week + " (" + hoursWorked + " hours):");
            System.out.printf("\n%-8s%-25s%-25s%-10s\n", "ID#", "First Name", "Last Name", "Weekly Pay");
            for (int i = 0; i < 68; i++) {System.out.print("=");}
            for (int i = 0; i < employees.size(); i++) {
                Worker employee = employees.get(i);

                String id = employee.getID();
                String firstName = employee.getFirstName();
                String lastName = employee.getLastName();
                Double weeklyPay = employee.calculateWeeklyPay(hoursWorked);

                System.out.printf(rowFormat, id, firstName, lastName, weeklyPay);
            }
        }
    }
}