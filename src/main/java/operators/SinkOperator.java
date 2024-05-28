package operators;

import chunk.CSVChunk;
import chunk.columns.DataColumn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SinkOperator implements Operator
{

@Override
public void pushChunk(CSVChunk chunk)
{
    List<String> colNames = chunk.getColumnNames();

    if (colNames.isEmpty())
    {
        System.out.println(" Zeroes Column ..");
        return;
    }

    int numRows = chunk.get(0).size();
    String colors[] = new String[]{"\u001B[32m", "\u001B[31m", "\u001B[33m", "\u001B[35m", "\u001B[34m"};
    String resetColor = "\u001B[0m";
    String outputPath = "src/main/resources/empty.csv";
    for (int rowIndex = 0; rowIndex < numRows; rowIndex++)
    {
        String row = "";
        String rawRow = "";
        for (int colIdx = 0; colIdx < colNames.size(); colIdx++)
        {
            DataColumn dataColumn = chunk.get(colIdx);
            rawRow += dataColumn.get(rowIndex) + ",";
            row += colors[colIdx % colors.length] + dataColumn.get(rowIndex) + resetColor;
            row += "\t\t";
        }
        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdir();

        try (FileWriter writer = new FileWriter(outputFile, true))
        {
            writer.write(rawRow + "\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(row);
    }
}

}
