package operators;

import chunk.CSVChunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectionOperator implements Operator
{

List<String> columnNames = new ArrayList<>();
List<String> dataTypes = new ArrayList<>();

String input;
private Operator nextOperator;

public ProjectionOperator(String input)
{

    this.input = input;
}

public void setNextOperator(Operator nextOp)
{
    nextOperator = nextOp;
}

@Override
public void pushChunk(CSVChunk chunk)
{
    List<String> stringList = new ArrayList<>();
    List<String> dataTypeList = new ArrayList<>();

    List<String> getColumnsNameFromString = new ArrayList<>();
    List<Integer> indexesList = new ArrayList<>();

    String[] words = input.split("\\s*,\\s*");

    Collections.addAll(stringList, words);
    columnNames = chunk.getColumnNames();
    dataTypes = chunk.getDataTypeNames();
    CSVChunk projectedChunk = new CSVChunk();
    for (int i = 0; i < stringList.size(); i++)
    {
        if (stringList.get(i).equals("*"))
        {

        }
    }
    for (int i = 0; i < stringList.size(); i++)
    {
        for (int y = 0; y < columnNames.size(); y++)
        {
            if (stringList.get(i).equals(columnNames.get(y)))
            {
                getColumnsNameFromString.add(stringList.get(i));
                indexesList.add(y);
            }
        }
    }

    for (Integer integer : indexesList)
    {
        dataTypeList.add(dataTypes.get(integer));
    }

    projectedChunk.setColumnNames(getColumnsNameFromString);

    for (Integer integer : indexesList)
    {
        projectedChunk.addDataColumn(chunk.get(integer));
    }
    nextOperator.pushChunk(projectedChunk);
}

}
