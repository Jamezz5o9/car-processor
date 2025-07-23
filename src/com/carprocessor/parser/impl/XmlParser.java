package com.carprocessor.parser.impl;


import com.carprocessor.model.Car;
import com.carprocessor.parser.FileParser;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlParser implements FileParser {

    @Override
    public List<Car> parse(String filePath) throws Exception {
        List<Car> cars = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(filePath));
        doc.getDocumentElement().normalize();

        NodeList carNodes = doc.getElementsByTagName("car");
        for (int i = 0; i < carNodes.getLength(); i++) {
            Node carNode = carNodes.item(i);
            if (carNode.getNodeType() == Node.ELEMENT_NODE) {
                Element carElement = (Element) carNode;
                String brand = carElement.getElementsByTagName("brand").item(0).getTextContent();
                String releaseDate = carElement.getElementsByTagName("releaseDate").item(0).getTextContent();
                LocalDate date = LocalDate.parse(releaseDate);
                cars.add(new Car(brand, "Unknown", "Unknown", 0.0, date));
            }
        }
        return cars;
    }
}

