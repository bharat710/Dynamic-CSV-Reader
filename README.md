# CSV Reader Columnar

A columnar CSV processing engine that uses a chunk-based pipeline architecture for efficient data filtering and projection operations.

## Overview

This project implements a columnar storage approach for CSV data processing, allowing for efficient filtering and projection operations on large datasets. The system reads CSV files in batches (chunks), processes them through operator pipelines, and outputs the results.

## Features

- **Columnar Storage**: Data is stored in column-oriented format for efficient access and filtering
- **Chunk-based Processing**: Processes CSV files in configurable batch sizes
- **Type Support**: Handles multiple data types (Integer, Float, Double, String)
- **Filter Operations**: Supports filtering with predicates (=, !=, >, <)
- **Projection Operations**: Select specific columns from the dataset
- **Pipeline Architecture**: Operators can be chained to create complex data processing pipelines

## Architecture

### Core Components

#### 1. Data Structures
- **CSVChunk**: Represents a batch of rows stored in columnar format
- **DataColumn**: Interface for type-specific column implementations
  - `IntColumn`: Integer data storage
  - `FloatColumn`: Float data storage
  - `DoubleColumn`: Double data storage
  - `StringColumn`: String data storage

#### 2. Operators
- **TableScanOperator**: Reads CSV files and creates chunks
- **FilterOperator**: Filters rows based on column predicates
- **ProjectionOperator**: Selects specific columns
- **SinkOperator**: Outputs the final results

#### 3. Pipeline Flow
```
CSV File → TableScanOperator → FilterOperator → ProjectionOperator → SinkOperator
```

## Project Structure

```
src/main/java/
├── chunk/
│   ├── CSVChunk.java
│   └── columns/
│       ├── DataColumn.java
│       ├── IntColumn.java
│       ├── FloatColumn.java
│       ├── DoubleColumn.java
│       └── StringColumn.java
├── operators/
│   ├── Operator.java
│   ├── TableScanOperator.java
│   ├── FilterOperator.java
│   ├── ProjectionOperator.java
│   └── SinkOperator.java
├── predicates/
│   └── Predicate.java
└── datatypes/
    └── Datatype.java
```

## Dependencies

- OpenCSV (5.7.1): CSV file parsing
- JUnit Jupiter: Testing framework
- Java 17+

## CSV Format

The CSV file should have the following format:
```
column1,column2,column3
integer,string,double
1,value1,1.5
2,value2,2.5
```

- **Row 1**: Column names
- **Row 2**: Data types (integer, string, double, float)
- **Row 3+**: Data rows

## Usage Example

```java
// Create operators
TableScanOperator scanOp = new TableScanOperator();
FilterOperator filterOp = new FilterOperator("columnName", "=", "value", nextOp);
ProjectionOperator projectOp = new ProjectionOperator("column1,column2");
SinkOperator sinkOp = new SinkOperator();

// Chain operators
scanOp.setNextOperator(filterOp);
projectOp.setNextOperator(sinkOp);

// Start processing
scanOp.pushChunk();
```

## Supported Predicates

- `=` : Equals
- `!=` : Not equals
- `>` : Greater than
- `<` : Less than

## Configuration

- **Batch Size**: Configurable in `TableScanOperator` (default: 3 rows per chunk)
- **Initial Column Size**: 50 elements with automatic expansion

## Output

The `SinkOperator` outputs results to:
- Console (with color-coded columns)
- `src/main/resources/empty.csv` file

## Building and Running

```bash
# Build with Maven
mvn clean compile

# Run tests
mvn test
```

## Technical Details

### Memory Efficiency
- Columns use dynamic arrays that expand as needed (doubling strategy)
- Initial capacity: 50 elements per column
- Automatic expansion when capacity is reached

### Type Safety
- Strong typing enforced through enum-based type system
- Runtime type validation during data parsing
- Type-specific implementations for optimal performance

## Future Enhancements

Potential areas for improvement:
- Add more aggregate operations (SUM, AVG, COUNT, etc.)
- Support for JOIN operations
- Parallel processing capabilities
- Memory-mapped file support for larger datasets
- Query optimization layer

## License

This project is part of an educational/experimental implementation.

## Contributing

Feel free to submit issues and enhancement requests!

