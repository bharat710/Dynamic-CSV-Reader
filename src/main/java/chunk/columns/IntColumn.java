package chunk.columns;

import datatypes.Datatype;
import predicates.Predicate;

import java.util.ArrayList;
import java.util.List;

public class IntColumn implements DataColumn
{

private int array[];
private int size = 0;

public IntColumn()
{
    array = new int[50];
}

@Override
public void convertRawStringDataToDataType(String str)
{

    try
    {
        int intValue = Integer.parseInt(str);
        addElement(intValue);
    }
    catch (NumberFormatException e)
    {
        throw new RuntimeException(e.getMessage());
    }
}

private void addElement(int intValue)
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
        int[] tempArr = array;
        array = new int[updatedArrSize];
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
    if (indexRemoved < size)
    {
        size--;
    }
}

@Override
public List<Integer> filter(Predicate predicate, String value)
{
    int filterValue;
    try
    {
        filterValue = Integer.parseInt(value);
    }
    catch (NumberFormatException e)
    {
        throw new RuntimeException(e);
    }
    List<Integer> indexToRemoveList = new ArrayList<>();

    if (predicate.equals(Predicate.EQUALS))

    {
        for (int index = 0; index < size; index++)
        {
            if (array[index] != (filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else if (predicate.equals(Predicate.NOT_EQUALS))
    {
        for (int index = 0; index < size; index++)
        {
            if (array[index] == (filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else if (predicate.equals(Predicate.GREATER_THAN))
    {
        for (int index = 0; index < size; index++)
        {
            if (array[index] <= (filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else
    {
        for (int index = 0; index < size; index++)
        {
            if (array[index] >= (filterValue))
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
    return Datatype.INT;
}

}



