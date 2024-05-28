package operators;

import chunk.CSVChunk;
import chunk.columns.DataColumn;
import predicates.Predicate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterOperator implements Operator
{

private final Operator nextOp;
private final String colName;
private final Predicate predicate;
private final String value;
private int colIndex = -1;
private static final Map<String, Predicate> stringToPredicateMap = new HashMap<>();

static
{
    stringToPredicateMap.put("=", Predicate.EQUALS);
    stringToPredicateMap.put("!=", Predicate.NOT_EQUALS);
    stringToPredicateMap.put(">", Predicate.GREATER_THAN);
    stringToPredicateMap.put("<", Predicate.LESS_THAN);
}

public FilterOperator(String colName, String strPredicate, String value, Operator nextOp)
{
    this.colName = colName;

    this.predicate = stringToPredicateMap.get(strPredicate);
    if (this.predicate == null)
    {
        throw new RuntimeException("Given strPredicate is not supported ..");
    }

    this.value = value;
    this.nextOp = nextOp;
}

@Override
public void pushChunk(CSVChunk chunk)
{
    List<String> colNames = chunk.getColumnNames();

    for (int indexes = 0; indexes < colNames.size(); indexes++)
    {
        if (colNames.get(indexes).equals(colName))
        {
            colIndex = indexes;
            break;
        }
    }
    if (colIndex == -1)
    {
        throw new RuntimeException("Column '" + colName + "' not found.");
    }

    DataColumn dataColumn = chunk.get(colIndex);
    List<Integer> indexToRemoveList = dataColumn.filter(predicate, value);
    chunk.removeByIndices(chunk, indexToRemoveList);

    nextOp.pushChunk(chunk);
}

}

