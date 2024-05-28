package operators;

import chunk.CSVChunk;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TableScanOperator
{

private String csvPath = "src/main/resources/SampleData.csv";
public int batchSize = 3;
private Operator nextOperator;

public void setNextOperator(Operator nextOperator)
{
    this.nextOperator = nextOperator;
}

public void pushChunk()
{

    List<String> headerRow = new ArrayList<>();
    List<String> dataTypes = new ArrayList<>();

    try (CSVReader reader = new CSVReader(new FileReader(csvPath)))
    {
        String[] nextLine;
        if ((nextLine = reader.readNext()) != null)
        {
            headerRow.addAll(List.of(nextLine));
        }

        if ((nextLine = reader.readNext()) != null)
        {
            dataTypes.addAll(List.of(nextLine));
        }
        while (true)
        {
            CSVChunk chunk1 = new CSVChunk();
            chunk1.initialize(dataTypes);
            chunk1.setColumnNames(headerRow);
            chunk1.setDataTypes(dataTypes);
            boolean doneReading = false;

            int currentBatchSize = 0;
            int x = 0;
            String[] arr;

            while ((nextLine = reader.readNext()) != null && currentBatchSize < batchSize)
            {

                for (int i = 0; i < nextLine.length; i++)
                {
                    chunk1.AddParsedData(nextLine[i], i);
                }
                currentBatchSize++;
            }
            if (nextLine == null)
            {
                doneReading = true;
            }

            nextOperator.pushChunk(chunk1);
            if (doneReading)
            {
                break;
            }
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
}

}
