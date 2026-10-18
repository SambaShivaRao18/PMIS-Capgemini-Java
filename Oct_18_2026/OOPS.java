class BankAccount
{
    //properties
    String AccNo;
    String AccHolderName;
    private double balance;

    public BankAccount(String AccNo,String AccHolderName)
    {
        this.AccHolderName=AccHolderName;
        this.AccNo=AccNo;
    }

    //check balance
    public void checkBal()
    {
        System.out.println("Available Balance  : "+this.balance);
    }

    //deposit money into account
    public void deposit(double money)
    {
        this.balance+=money;
        System.out.println("Dear "+this.AccHolderName+" ₹ "+money+" added to your account !!");
    }

    //Withdraw money into account
    public void withdraw(double amount)
    {
        this.balance-=amount;
        System.out.println("Dear "+this.AccHolderName+" ₹ "+amount+" debted from your account !!");
    }
}
public class OOPS
{
    public static void main(String[] args)
    {
        BankAccount acc1=new BankAccount("1234567890","Samba Shiva Rao");

        acc1.checkBal();

        acc1.deposit(10000);

        acc1.checkBal();

        acc1.withdraw(3500);

        acc1.checkBal();
    }
}
