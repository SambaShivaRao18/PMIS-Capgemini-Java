import java.time.LocalDate;
import java.util.Scanner;
class DateExplorer
{
    public void displayMonth(LocalDate obj)
    {
        System.out.println(obj.getMonth());
    }
    public void displayYear(LocalDate obj)
    {
        System.out.println(obj.getYear());
    }
    public void lookBackMonths(LocalDate obj,int val)
    {
        System.out.println(obj.minusMonths(val));
    }
}
class Activity
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        // System.out.println(LocalDate.now());
        System.out.println("Enter a Date : [YYYY-MM-DD]");

        String userInp=sc.next();

        LocalDate obj=LocalDate.parse(userInp);

        while(true)
        {
            System.out.println("Select option From menu below");
            System.out.println("1. What was the date on x no.of months back.");
            System.out.println("2. Display the date");
            System.out.println("3. Check Leap year or not ");
            System.out.println("4. Exit ");

            int option=sc.nextInt();

            if(option==4)
             break;

             DateExplorer object=new DateExplorer();
            
            switch(option)
            {
                case 1: 
                    System.out.println("Enter no.of months t loo back !!");
                    int val=sc.nextInt();
                    object.lookBackMonths(obj,val);
                    break;
                case 2:
                    System.out.println(obj);
                    break;
                case 3:
                    int year=obj.getYear();
                    System.out.println((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
                    break;
                default:
                    System.out.println("Invalid option selected....");
                    break;
            }

        }

    }
}