package chunk;

import chunk.columns.*;
import datatypes.Datatype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVChunk
{

private final List<DataColumn> columns = new ArrayList<>();
private final List<String> dataTypes = new ArrayList<>();
private final List<String> columnNames = new ArrayList<>();
private static final Map<String, Datatype> stringToDataTypeMap = new HashMap<>();

static
{
    stringToDataTypeMap.put("integer", Datatype.INT);
    stringToDataTypeMap.put("string", Datatype.STRING);
    stringToDataTypeMap.put("double", Datatype.DOUBLE);
    stringToDataTypeMap.put("float", Datatype.FLOAT);
}

public void initialize(List<String> colTypes)
{

    for (String colType : colTypes)

    {
        Datatype dataType = stringToDataTypeMap.get(colType.toLowerCase());
        if (dataType == null)
        {
            throw new RuntimeException("datatype not supported : " + colType);
        }
        columns.add(createColFromType(dataType));
    }
}

public void AddParsedData(String data, int col)
{
    columns.get(col).convertRawStringDataToDataType(data);
}

public DataColumn createColFromType(Datatype dataType)
{

    return switch (dataType)
    {
        case INT -> new IntColumn();
        case STRING -> new StringColumn();
        case DOUBLE -> new DoubleColumn();
        case FLOAT -> new FloatColumn();
    };
}

public CSVChunk()
{

}

public void setColumnNames(List<String> columnNames)
{
    this.columnNames.clear();
    this.columnNames.addAll(columnNames);
}

public List<String> getColumnNames()
{
    return columnNames;
}

public void setDataTypes(List<String> dataTypes)
{
    this.dataTypes.clear();
    this.dataTypes.addAll(dataTypes);
}

public void removeByIndices(CSVChunk inputChunk, List<Integer> indexes)
{

    int indexSize = indexes.size();

    for (int i = indexSize - 1; i >= 0; i--)
    {
        for (int colIndex = 0; colIndex < inputChunk.numColumns(); colIndex++)
        {
            DataColumn column = inputChunk.get(colIndex);
            column.remove(indexes.get(i));
        }
    }
}

public List<DataColumn> addDataColumn(DataColumn d)
{
    columns.add(d);
    return columns;
}

public DataColumn get(int i)
{
    DataColumn dataColumn = columns.get(i);
    return dataColumn;
}

public int numColumns()
{
    return columns.size();
}

public List<String> getDataTypeNames()
{
    return dataTypes;
}

}
