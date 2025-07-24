# Car Data Processor

A command-line Java application that processes and combines car data from CSV and XML files, offering filtering, sorting, and multiple output formats.

## Features

- Parses car data from CSV and XML
- Filters by:
  - Brand & Price
  - Brand & Release Date
- Sorting options:
  - Release Year (newest first)
  - Price (highest first)
  - Type & Currency-based
- Output formats:
  - Table
  - XML
  - JSON

## Installation

### Prerequisites

- Java 21
- Maven 3.6+

### Build

```bash
git clone https://github.com/Jamezz5o9/car-processor.git
cd car-processor
mvn clean package
```

Generates `car-processor-1.0.0.jar` in `target/`.

## File Structure

```
car-processor/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── carprocessor/
│   │   │           ├── CarProcessorApplication.java
│   │   │           ├── CarProcessorRunner.java
│   │   │           ├── model/
│   │   │           │   └── Car.java
│   │   │           ├── parser/
│   │   │           │   ├── FileParser.java
│   │   │           │   └── impl/
│   │   │           │       ├── CsvParser.java
│   │   │           │       └── XmlParser.java
│   │   │           ├── formatter/
│   │   │           │   ├── OutputFormatter.java
│   │   │           │   └── impl/
│   │   │           │       ├── TableFormatter.java
│   │   │           │       ├── XmlFormatter.java
│   │   │           │       └── JsonFormatter.java
│   │   │           └── service/
│   │   │               └── CarProcessorService.java
│   │   └── resources/
│   │       ├── CarsBrand.csv
│   │       └── carsType.xml
│
├── pom.xml
└── README.md
```

## Data Format

**CSV (CarsBrand.csv)**

```csv
Brand,ReleaseDate
Toyota,01/15/2023
```

**XML (carsType.xml)**

```xml
<cars>
  <car>
    <type>SUV</type>
    <model>RAV4</model>
    <price currency="USD">25000.00</price>
  </car>
</cars>
```

> Note: CSV and XML entries are matched by position.

## Run

```bash
java -jar target/car-processor-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Provide relative file paths as:
- ` src/main/java/com/carprocessor/resource/CarsBrand.csv
`
- `src/main/java/com/carprocessor/resource/carsType.xml`



## Usage Examples

**Filter by Brand and Price**

```
Choose option: 1
Brand: Toyota
Max Price (USD): 30000
```

**Filter by Brand and Release Date**

```
Choose option: 2
Brand: Honda
Release Date: 2022-11-20
```

**Sort Options**

```
1. Release Year
2. Price
3. Type + Currency
```

**Output Options**

```
1. Table
2. XML
3. JSON
```

## Example Output (Table)

```
Brand     Type   Model   Price(USD)  Release Date
Toyota    SUV    RAV4    25000.00    2023-01-15
```

## Example Output (XML)
```
<?xml version="1.0" encoding="UTF-8"?>
<cars>
  <car brand="Toyota">
    <type>SUV</type>
    <model>RAV4</model>
    <price currency="USD">25000.00</price>
    <releaseDate>2023-01-15</releaseDate>
  </car>
</cars>
```


## Example Output (JSON)
```
{
  "cars": [
    {
      "brand": "Toyota",
      "type": "SUV",
      "model": "RAV4",
      "priceUSD": 25000.0,
      "releaseDate": "2023-01-15"
    }
  ]
}
```


## Complete Usage Example
```
$ java -jar target/car-processor-1.0-SNAPSHOT-jar-with-dependencies.jar

=== Car Data Processor ===
This application requires both CSV and XML files to process car data.

Enter CSV file path (contains Brand and ReleaseDate):  src/main/java/com/carprocessor/resource/CarsBrand.csv

Enter XML file path (contains car details): src/main/java/com/carprocessor/resource/carsType.xml

Successfully loaded 10 cars.

Filter Options:
1. Filter by Brand and Price
2. Filter by Brand and Release Date
3. No filter
Choose option (1-3): 1
Enter brand: Toyota
Enter maximum price (USD): 30000

Sorting Options:
1. Sort by Release Year (latest first)
2. Sort by Price (highest first)
3. Sort by Type and Currency
4. No sorting
Choose option (1-4): 2

Output Format:
1. Table
2. XML
3. JSON
Choose option (1-3): 1

=== Results ===
Brand           Type       Model           Price(USD) Release Date
-----------------------------------------------------------------
Toyota          SUV        RAV4            $25000.00  2023-01-15
```

## Error Handling

- File not found / invalid format
- Malformed data / invalid dates
- Price or filter validation

## Extend

- Add filters → `CarProcessorService`
- Add sort options → same
- Add formatters → implement `OutputFormatter`

## Troubleshooting

- Check file paths and formats
- Use valid date & currency formats
- Ensure Java version is 11+
