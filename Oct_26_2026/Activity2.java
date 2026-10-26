import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
class Activity2
{
    public static void main(String[] args)
    {

       try
       {
            System.out.println("Enter Zone Id : ");

            Scanner sc=new Scanner(System.in);

            String zoneId=sc.nextLine();

            ZoneId id=ZoneId.of(zoneId);

            ZonedDateTime ans=ZonedDateTime.now(id);

            System.out.println(ans);
       }
       catch(java.time.DateTimeException e)
       {
        System.out.println("Invalid time zone !!");
       }
    }
}