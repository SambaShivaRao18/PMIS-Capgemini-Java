import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

class Activity3
{
    public static ZonedDateTime zonedDateTime(LocalDateTime var,ZoneId id)
    {
        return var.atZone(id);
    }
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("==========Meeting Time Assist==========");
        System.out.println("Enter meeting date (yyyy-MM-dd): ");
        String dateInp=sc.next();

        System.out.println("Enter meeting time (HH:mm): ");
        String timeInp=sc.next();

        System.out.println("Enter meeting duration in minutes: ");
        long duration=sc.nextLong();

        while(true)
        {
            System.out.println("Select option :");
            System.out.println("1. Display meeting date and time");
            System.out.println("2. Display meeting end time");
            System.out.println("3. Display meeting time in another zone");
            System.out.println("4. Check whether the meeting is in the future");
            System.out.println("5. Exit");

            int option=sc.nextInt();

            if(option==5)
             break;

             LocalDate date=LocalDate.parse(dateInp);
             LocalTime time=LocalTime.parse(timeInp);
             LocalDateTime datetime=LocalDateTime.parse(dateInp+"T"+timeInp);
             

            
            switch(option)
            {
                case 1:
                    System.out.println("Meeting Date :"+date);
                    System.out.println("Meeting Time :"+time);
                    break;
                case 2:
                    System.out.println("Meeting end time : "+datetime.plusMinutes(duration));
                    break;
                case 3:
                    System.out.println("Enter time zone : ");
                    String zone=sc.next();
                    try{
                        ZoneId id=ZoneId.of(zone);
                        System.out.println(zonedDateTime(datetime,id));
                    }
                    catch(java.time.DateTimeException e)
                    {
                        System.out.println("Select valid Zone!!");
                    }
                    break;
                case 4:
                    System.out.println(LocalDateTime.now().isAfter(datetime));
                    break;
                default:
                    System.out.println("Invalid Option....");
                    break;
            }
        }
    }
}