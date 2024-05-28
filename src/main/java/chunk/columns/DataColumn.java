package chunk.columns;

import datatypes.Datatype;
import predicates.Predicate;

import java.util.List;

public interface DataColumn
{

void convertRawStringDataToDataType(String string);

int size();

Object get(int index);

void remove(int rowIndex);

List<Integer> filter(Predicate predicate, String value);

Datatype getDataType();

}


