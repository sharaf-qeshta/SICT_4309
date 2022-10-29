package SICT_4309.blockchain;
public class Entry<T, E>
{
    private T key;
    private E n;

    public Entry(T key, E n)
    {
        this.key = key;
        this.n = n;
    }

    public T getKey()
    {
        return key;
    }

    public E getN()
    {
        return n;
    }
}
