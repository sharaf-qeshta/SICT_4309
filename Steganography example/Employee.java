package SICT_4309;

import java.io.Serializable;


public class Employee implements Serializable
{
    String name;
    byte id; // this field will be used to store the data that we need to hide

    public Employee(String name, byte id)
    {
        this.name = name;
        this.id = id;
    }
}
