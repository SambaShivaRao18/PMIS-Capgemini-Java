
class Bank
{
    static class ABC
    {
        public static void sayHii()
        {
            System.out.println("HI All!!");
        }
    }
    static String name="HDFC";
    static 
    {
        System.out.println("Welcone to "+name);
    }
    private String AccHolderName;
    private String AccNo;
    private double balance;

    public Bank(String ahn,String an,double bal)
    {
        AccHolderName=ahn;
        AccNo=an;
        balance=bal;
    }
    public static void guideAccOpen()
    {
        System.out.println("Visit your nearest "+name+" Bank first");
    }
}
class keywords
{
    public static void main(String[] args)
    {
        Bank b1=new Bank("Samba","1234",12345678);
        Bank b2=new Bank("Shiva","7890",9876);

        Bank.guideAccOpen();

        Bank.ABC.sayHii();

        // System.out.println(b1.name);
        // b1.name="ICICI";
        // System.out.println(b2.name+b1.name);
    }
}