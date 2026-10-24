class Thread1 extends Thread
{
    @Override
    public void run()
    {
        System.out.println("Thread1 here..");
    }
}

class Thread2 implements Runnable
{
    @Override
    public void run()
    {
        System.out.println("Thread2 here..");
    }
}
class MultiThreading
{
    public static void main(String[] args)
    {
        Thread1 t1=new Thread1();
        Thread2 obj=new Thread2();

        Thread t2=new Thread(obj);

        t1.start();
        t2.start();
    }
}