import java.util.*;
import java.util.stream.*;
class StreamAPI
{
    public static void main(String[] args)
    {
        List<Integer> orgList=Arrays.asList(1,2,3,4,5);

        System.out.println(orgList);

        List<Integer> al=orgList.stream()
        .filter(n->n%2==0)
        .map(n->n*n)
        .collect(Collectors.toList());

        System.out.println(al);
    }
}