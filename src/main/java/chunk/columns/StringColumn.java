package chunk.columns;

import datatypes.Datatype;
import predicates.Predicate;

import java.util.ArrayList;
import java.util.List;

public class StringColumn implements DataColumn
{

private String array[];
private int size = 0;

public StringColumn()
{
    array = new String[50];
}

@Override
public void convertRawStringDataToDataType(String str)
{

    try
    {
        String Value = str;
        addElement(Value);
    }
    catch (NumberFormatException e)
    {
        throw new RuntimeException(e.getMessage());
    }
}

private void addElement(String intValue)
{
    expandIfNecessary();
    array[size] = intValue;
    size++;
}

private void expandIfNecessary()
{
    if (size >= array.length)
    {
        int updatedArrSize = size * 2;
        String[] tempArr = array;
        array = new String[updatedArrSize];
        System.arraycopy(tempArr, 0, array, 0, tempArr.length);
    }
}

@Override
public int size()
{
    return size;
}

@Override
public Object get(int indexWant)
{
    if (indexWant < size)
    {
        return array[indexWant];
    }
    else
    {
        throw new IndexOutOfBoundsException("Index is out of bounds.");
    }
}

@Override
public void remove(int indexRemoved)
{
    for (int i = indexRemoved; i < size - 1; i++)
    {
        array[i] = array[i + 1];
    }
    size--;
}

@Override
public List<Integer> filter(Predicate predicate, String value)
{
    String filterValue = value;

    List<Integer> indexToRemoveList = new ArrayList<>();

    if (predicate.equals(Predicate.EQUALS))

    {
        for (int index = 0; index < size; index++)
        {
            if (!array[index].equals(filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else if (predicate.equals(Predicate.NOT_EQUALS))
    {
        for (int index = 0; index < size; index++)
        {
            if (array[index].equals(filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else if (predicate.equals(Predicate.GREATER_THAN))
    {
        for (int index = 0; index < size; index++)
        {
            if (array[index].compareTo(filterValue) < 0)
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else
    {
        for (int index = 0; index < size; index++)
        {
            if (array[index].compareTo(filterValue) > 0)
            {
                indexToRemoveList.add(index);
            }
        }
    }

    return indexToRemoveList;
}

@Override
public Datatype getDataType()
{
    return Datatype.STRING;
}

}



