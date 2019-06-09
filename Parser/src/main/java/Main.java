import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File input = new File("src/main/java/przychodnia.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(input);

        doc.getDocumentElement().normalize();
        System.out.println("Tytul: " + doc.getDocumentElement().getNodeName());
        List<Patient> patientsList = printPatients(doc);

        createDocWithPatients(patientsList);
        modifyXml();
    }

    private static List<Patient> printPatients(Document doc) {
        NodeList patients = doc.getElementsByTagName("pacjent");
        System.out.println("Pacjenci: ");

        List<Patient> patientsFromXml = new ArrayList<Patient>();

        for (int i = 0; i < patients.getLength(); i++) {
            Node node = patients.item(i);
            Element element = (Element) node;
            Patient p = new Patient(returnFromElement(element, "imie"), returnFromElement(element, "nazwisko"), returnFromElement(element, "wiek"), returnFromElement(element, "pesel"), element.getAttribute("plec"));
            patientsFromXml.add(p);

            System.out.println();
            System.out.println(node.getNodeName());
            System.out.println("Imie: " + returnFromElement(element, "imie"));
            System.out.println("Nazwisko: " + returnFromElement(element, "nazwisko"));
            System.out.println("Wiek: " + returnFromElement(element, "wiek"));
            System.out.println("Pesel: " + returnFromElement(element, "pesel"));
            System.out.println("Plec: " + element.getAttribute("plec"));
            System.out.println("Czy ma id? " + element.hasAttribute("id"));
        }

        return patientsFromXml;
    }

    private static String returnFromElement(Element element, String s) {
        return element.getElementsByTagName(s).item(0).getTextContent();
    }

    private static void createDocWithPatients(List<Patient> patientList) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element root = doc.createElement("pacjenci");
        doc.appendChild(root);

        for (Patient p : patientList) {
            Element patient = doc.createElement("pacjent");

            Element name = doc.createElement("imie");
            Element lastName = doc.createElement("nazwisko");
            Element age = doc.createElement("wiek");
            Element pesel = doc.createElement("pesel");
            Attr sex = doc.createAttribute("plec");

            name.appendChild(doc.createTextNode(p.name));
            lastName.appendChild(doc.createTextNode(p.lastName));
            age.appendChild(doc.createTextNode(p.age));
            pesel.appendChild(doc.createTextNode(p.pesel));
            sex.setValue(p.sex);

            patient.setAttributeNode(sex);
            patient.appendChild(name);
            patient.appendChild(lastName);
            patient.appendChild(age);
            patient.appendChild(pesel);

            root.appendChild(patient);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/main/java/pacjenci.xml"));
        transformer.transform(source, result);
    }

    private static void modifyXml() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File input = new File("src/main/java/pacjenci.xml");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(input);

        Node patients = doc.getFirstChild();
        Node patient = doc.getElementsByTagName("pacjent").item(0);

        NamedNodeMap attr = patient.getAttributes();
        Node sexAttr = attr.getNamedItem("plec");
        sexAttr.setTextContent(":)");

        NodeList list = patient.getChildNodes();

        Element e = (Element) list.item(0);
        e.setTextContent("Jakies Imie :D");
        e = (Element) list.item(1);
        e.setTextContent("Jakies Nazwisko :)");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/main/java/pacjenci2.xml"));
        transformer.transform(source, result);
    }
}

class Patient {
    String name;
    String lastName;
    String age;
    String pesel;
    String sex;

    public Patient(String name, String lastName, String age, String pesel, String sex) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.pesel = pesel;
        this.sex = sex;
    }
}