package com.carprocessor.main.java.parser.impl;

import com.carprocessor.main.java.model.Car;
import com.carprocessor.main.java.parser.FileParser;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.carprocessor.main.java.utils.Constant.UNKNOWN;

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

                String type = getElementText(carElement, "type");
                String model = getElementText(carElement, "model");

                double priceUSD = 0.0;
                NodeList priceNodes = carElement.getElementsByTagName("price");
                if (priceNodes.getLength() > 0) {
                    Element priceElement = (Element) priceNodes.item(0);
                    String currency = priceElement.getAttribute("currency");
                    if ("USD".equals(currency)) {
                        priceUSD = Double.parseDouble(priceElement.getTextContent());
                    }
                }

                Car car = new Car(null, type, model, priceUSD, null);

                Map<String, Double> prices = new HashMap<>();
                NodeList pricesNodes = carElement.getElementsByTagName("prices");
                if (pricesNodes.getLength() > 0) {
                    Element pricesElement = (Element) pricesNodes.item(0);
                    NodeList currencyPriceNodes = pricesElement.getElementsByTagName("price");

                    for (int j = 0; j < currencyPriceNodes.getLength(); j++) {
                        Element priceNode = (Element) currencyPriceNodes.item(j);
                        String currency = priceNode.getAttribute("currency");
                        double price = Double.parseDouble(priceNode.getTextContent());
                        prices.put(currency, price);
                    }
                }
                car.setPrices(prices);

                cars.add(car);
            }
        }
        return cars;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            Node node = nodes.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return UNKNOWN;
    }
}