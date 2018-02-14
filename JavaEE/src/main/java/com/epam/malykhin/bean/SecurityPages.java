package com.epam.malykhin.bean;

import com.epam.malykhin.database.entity.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by Serhii_Malykhin on 12/29/2016.
 */
public class SecurityPages {
    private static final String CONSTRAINT = "constraint";
    private static final String URL_PATTERN = "url-pattern";
    private static final String ROLE = "role";
    private Map<String, Roles> security;

    public void parser(String pathFileName) {
        try {
            Document doc = getDocument(pathFileName);
            doc.getDocumentElement().normalize();
            security = new HashMap<>();
            startFilling(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document getDocument(String pathFileName) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(pathFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(inputFile);
    }

    private void startFilling(Document doc) {
        NodeList nList = doc.getElementsByTagName(CONSTRAINT);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            fillNodeElements(nNode, temp, nList);
        }
    }

    private void fillNodeElements(Node nNode, int temp, NodeList nList) {
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            String url = eElement.getElementsByTagName(URL_PATTERN).item(0).getTextContent();
            Roles newRole = new Roles();
            security.put(url, newRole);
            fillRoles(eElement, temp, nList, newRole);
        }
    }

    private void fillRoles(Element eElement, int temp, NodeList nList, Roles roles) {
        NodeList roleList = eElement.getElementsByTagName(ROLE);
        for (int i = 0; i < roleList.getLength(); i++) {
            Node roleNode = nList.item(temp);
            Element roleElement = (Element) roleNode;
            roles.add(Integer.parseInt(roleElement.getElementsByTagName(ROLE).item(i).getTextContent()));
        }
    }

    public boolean checkSecurityRole(String page, User user) {
        Iterator<Map.Entry<String, Roles>> iterator = security.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Roles> secure = iterator.next();
            int roleId = user.getRoleId();
            if (page.matches(secure.getKey()) && secure.getValue().getRoles().contains(roleId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContainPageInSecurity(String page) {
        Iterator<Map.Entry<String, Roles>> iterator = security.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Roles> secure = iterator.next();
            if (page.matches(secure.getKey())) {
                return true;
            }
        }
        return false;
    }

    public boolean isSecurityRole(User user) {
        Iterator<Map.Entry<String, Roles>> iterator = security.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Roles> secure = iterator.next();
            int roleId = isNull(user) ? -1 : user.getRoleId();
            if (secure.getValue().getRoles().contains(roleId)) {
                return true;
            }
        }
        return false;
    }
}
