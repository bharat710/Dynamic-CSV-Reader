package chunk.columns;

import datatypes.Datatype;
import predicates.Predicate;

import java.util.ArrayList;
import java.util.List;

public class DoubleColumn implements DataColumn
{

private double doubleArray[];
private int size = 0;

public DoubleColumn()
{
    doubleArray = new double[50];
}

@Override
public void convertRawStringDataToDataType(String str)
{
    try
    {
        double doubleValue = Double.parseDouble(str);
        addElement(doubleValue);
    }
    catch (NumberFormatException e)
    {
        throw new RuntimeException(e.getMessage());
    }
}

private void addElement(double doubleValue)
{
    expandIfNecessary();
    doubleArray[size] = doubleValue;
    size++;
}

private void expandIfNecessary()
{
    if (size >= doubleArray.length)
    {
        int updatedArrSize = size * 2;
        double[] tempArr = doubleArray;
        doubleArray = new double[updatedArrSize];
        System.arraycopy(tempArr, 0, doubleArray, 0, tempArr.length);
    }
}

@Override
public int size()
{

    return size;
}

@Override
public Object get(int index)
{
    if (index < size)
    {
        return doubleArray[index];
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
        doubleArray[i] = doubleArray[i + 1];
    }
    size--;
}

@Override
public List<Integer> filter(Predicate predicate, String value)
{
    Double filterValue;
    try
    {
        filterValue = Double.parseDouble(value);
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
            if (doubleArray[index] != (filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else if (predicate.equals(Predicate.NOT_EQUALS))
    {
        for (int index = 0; index < size; index++)
        {
            if (doubleArray[index] == (filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else if (predicate.equals(Predicate.GREATER_THAN))
    {
        for (int index = 0; index < size; index++)
        {
            if (doubleArray[index] <= (filterValue))
            {
                indexToRemoveList.add(index);
            }
        }
    }
    else
    {
        for (int index = 0; index < doubleArray.length; index++)
        {
            if (doubleArray[index] >= (filterValue))
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
    return Datatype.DOUBLE;
}

}

