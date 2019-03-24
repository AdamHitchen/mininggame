package something;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class XMLReader {
    private static final String TITLE = "title";
    private static final String RECIPE = "Recipe";
    private static final String INPUT = "input";
    private static final String OUTPUT = "output";
    private static final String AMOUNT = "amount";
    int i = 0;

    @SuppressWarnings({"unchecked", "null"})
    XMLReader() {

    }

    public ArrayList readConfig(String configFile) {
        ArrayList<Recipe> items = new ArrayList<Recipe>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Recipe recipe = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart() == (RECIPE)) {
                        recipe = new Recipe();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(TITLE)) {
                                recipe.setName(attribute.getValue());
                            }

                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(INPUT)) {
                            event = eventReader.nextEvent();
                            recipe.addMaterial(Integer.parseInt((event.asCharacters().getData())));
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(AMOUNT)) {
                                    recipe.addReq(Integer.parseInt((attribute.getValue())));
                                }
                            }
                            continue;
                        }


                    }
                    if (event.asStartElement().getName().getLocalPart().equals(OUTPUT)) {
                        event = eventReader.nextEvent();
                        recipe.setResult(Integer.parseInt((event.asCharacters().getData())));
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(AMOUNT)) {
                                recipe.setResultQ(Integer.parseInt((attribute.getValue())));
                            }
                        }
                        continue;
                    }

                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart() == (RECIPE)) {
                        items.add(recipe);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

} 