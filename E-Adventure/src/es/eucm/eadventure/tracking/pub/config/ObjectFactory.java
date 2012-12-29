//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.6 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.12.29 a las 05:39:40 PM CET 
//


package es.eucm.eadventure.tracking.pub.config;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.eucm.eadventure.tracking.pub.config package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Frequency_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "frequency");
    private final static QName _Path_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "path");
    private final static QName _Value_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "value");
    private final static QName _Class_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "class");
    private final static QName _Url_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "url");
    private final static QName _MainClass_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "main-class");
    private final static QName _Enabled_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "enabled");
    private final static QName _A_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "a");
    private final static QName _Name_QNAME = new QName("http://e-adventure.e-ucm.es/eadventure-tracking-schema", "name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.eucm.eadventure.tracking.pub.config
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TrackingConfig }
     * 
     */
    public TrackingConfig createTrackingConfig() {
        return new TrackingConfig();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link Service }
     * 
     */
    public Service createService() {
        return new Service();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "frequency", defaultValue = "5000")
    public JAXBElement<Long> createFrequency(Long value) {
        return new JAXBElement<Long>(_Frequency_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "path")
    public JAXBElement<String> createPath(String value) {
        return new JAXBElement<String>(_Path_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "class")
    public JAXBElement<String> createClass(String value) {
        return new JAXBElement<String>(_Class_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "url")
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "main-class")
    public JAXBElement<String> createMainClass(String value) {
        return new JAXBElement<String>(_MainClass_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "enabled", defaultValue = "false")
    public JAXBElement<Boolean> createEnabled(Boolean value) {
        return new JAXBElement<Boolean>(_Enabled_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "a")
    public JAXBElement<Object> createA(Object value) {
        return new JAXBElement<Object>(_A_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-adventure.e-ucm.es/eadventure-tracking-schema", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

}
