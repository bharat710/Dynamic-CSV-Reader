import operators.FilterOperator;
import operators.ProjectionOperator;
import operators.SinkOperator;
import operators.TableScanOperator;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

public class TestEngineFeatures
{

@Test
public void testWithNoFilter()
{
    TableScanOperator table = new TableScanOperator();
    SinkOperator sinkOperator = new SinkOperator();
    //        Filter filter = new Filter("Year", ">", "2015", sink);
    table.setNextOperator(sinkOperator);
    table.pushChunk();
}

@Test
public void testWithFilter_equals()
{
    Instant startTime = Instant.now();
    TableScanOperator table = new TableScanOperator();
    SinkOperator sinkOperator = new SinkOperator();
    ProjectionOperator p = new ProjectionOperator("id, firstname , Random Integer");
    FilterOperator filterOperator = new FilterOperator("Random Integer", "=", "400", p);
    // FilterOperator filterOperator = new FilterOperator("Status", ">", "Final", sinkOperator);
    table.setNextOperator(filterOperator);
    p.setNextOperator(sinkOperator);
    table.pushChunk();
    Instant endTime = Instant.now();
    long executionTime = Duration.between(startTime, endTime).toMillis();
    System.out.println("Total execution time: " + executionTime + " ms");
}

@Test
public void testWithFilter_greaterThan()
{
    Instant startTime = Instant.now();
    TableScanOperator table = new TableScanOperator();
    SinkOperator sinkOperator = new SinkOperator();
    ProjectionOperator p = new ProjectionOperator("id, firstname , Random Integer");
    FilterOperator filterOperator = new FilterOperator("Random Integer", ">", "400", p);
    // FilterOperator filterOperator = new FilterOperator("Status", ">", "Final", sinkOperator);
    table.setNextOperator(filterOperator);
    p.setNextOperator(sinkOperator);
    table.pushChunk();
    Instant endTime = Instant.now();
    long executionTime = Duration.between(startTime, endTime).toMillis();
    System.out.println("Total execution time: " + executionTime + " ms");
}

@Test
public void testWithFilter_lessThan()
{
    Instant startTime = Instant.now();
    TableScanOperator table = new TableScanOperator();
    SinkOperator sinkOperator = new SinkOperator();
    ProjectionOperator p = new ProjectionOperator("id, firstname , Random Integer");
    FilterOperator filterOperator = new FilterOperator("Random Integer", "<", "400", p);
    // FilterOperator filterOperator = new FilterOperator("Status", ">", "Final", sinkOperator);
    table.setNextOperator(filterOperator);
    p.setNextOperator(sinkOperator);
    table.pushChunk();
    Instant endTime = Instant.now();
    long executionTime = Duration.between(startTime, endTime).toMillis();
    System.out.println("Total execution time: " + executionTime + " ms");
}

@Test
public void testWithFilter_noteEquals()
{
    Instant startTime = Instant.now();
    TableScanOperator table = new TableScanOperator();
    SinkOperator sinkOperator = new SinkOperator();
    ProjectionOperator p = new ProjectionOperator("id, firstname , Random Integer");
    FilterOperator filterOperator = new FilterOperator("Random Integer", "!=", "400", p);
    // FilterOperator filterOperator = new FilterOperator("Status", ">", "Final", sinkOperator);
    table.setNextOperator(filterOperator);
    p.setNextOperator(sinkOperator);
    table.pushChunk();
    Instant endTime = Instant.now();
    long executionTime = Duration.between(startTime, endTime).toMillis();
    System.out.println("Total execution time: " + executionTime + " ms");
}

@Test
public void testWithNoProjection()
{
    Instant startTime = Instant.now();
    TableScanOperator table = new TableScanOperator();
    SinkOperator sinkOperator = new SinkOperator();
    FilterOperator filterOperator = new FilterOperator("Random Integer", ">", "400", sinkOperator);
    table.setNextOperator(filterOperator);
    table.pushChunk();
    Instant endTime = Instant.now();
    long executionTime = Duration.between(startTime, endTime).toMillis();
    System.out.println("Total execution time: " + executionTime + " ms");
}

}
