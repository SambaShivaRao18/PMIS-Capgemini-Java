class Interfaces
{
    public static void main(String[] args)
    {

    }
}
interface Payment
{
   public void pay();
}

class UPI implements Payment
{
    @Override
    public void pay()
    {
        System.out.println("Payment successful through UPI");
    }
}