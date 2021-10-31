/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ptyxiaki;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author akisg
 */


public class Main {
  


    public static void NUL( NodeList<ImportDeclaration> c)
        {
            
            for (int j=0; j < c.size(); j++) 
            {
                for(int k=0; k < c.size(); k++)
                {
                    
                    if (c.get(j).equals(c.get(k)) && j!=k)
                       
                    {
                        
                        c.get(k).remove();
                        
                    }
                    
                }
            }
            
            
            System.out.println("The number of Imports are: "+c.size());
            
        }
    
    
    
    
    
    
    public static void printPomDependencies(String File_Path) throws IOException, SAXException, ParserConfigurationException {
    // pom relative to your project directory
    final File pomFile = new File(File_Path+"/pom.xml");

    DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    org.w3c.dom.Document doc = dBuilder.parse(pomFile);
    doc.getDocumentElement().normalize();
    final org.w3c.dom.NodeList dependencyNodes = doc.getElementsByTagName("dependency");

    for (int i = 0; i < dependencyNodes.getLength(); i++) 
    {
        final Node n = dependencyNodes.item(i);

        final org.w3c.dom.NodeList list = n.getChildNodes();

        System.out.println("----------------------------------");
        
        for (int j = 0; j < list.getLength(); j++) 
        {
            final Node n2 = list.item(j);
            // do not print empty text nodes or others...
            if (n2.getNodeType() != Node.ELEMENT_NODE) continue;


            System.out.println(n2.getNodeName() + ":" + n2.getTextContent());

        }
    }
} 
    
    
    public static void CMD(String cmd)
    {
        String[] command =
	    {
	        "cmd",
	    };
	    Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
	                new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
	                PrintWriter stdin = new PrintWriter(p.getOutputStream());
	                stdin.println(cmd);
	                stdin.close();
	                p.waitFor();
	    	} catch (Exception e) {
	 		e.printStackTrace();
		}
    }
    
    
    
    
    
    private static final String FILE_PATH = "E:\\randomStuff\\Ptyxiaki\\Κωδικας\\src\\main\\java\\calculator\\MetricsFilter.java";
    
    
    private static final String FILE_PATH2="E:/Mathimata/Netbeens_Projects/Ptyxiaki/src/main/java/com/mycompany/ptyxiaki/Example.java";
    
    private static final String FILE_PATH3="C:\\Netbeans_Project\\mavenproject1";
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        // TODO code application logic here
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
       
        
        
        
        CompilationUnit cu1 = StaticJavaParser.parse(new File(FILE_PATH2));
        
        NodeList<ImportDeclaration> a = cu1.getImports();
     
        NUL(a);
        
        printPomDependencies(FILE_PATH3);
        
        
        
        
        
        
        
        

        String CWD = System.getProperty("user.dir");
        
        String cmd_Command="cd " + FILE_PATH3+" | mvn dependency:copy-dependencies -DoutputDirectory="+CWD+"\\target\\lib";
        
        CMD(cmd_Command);
        
        String cmd2 ="dir /a:-d /s /b "+CWD + "\\target\\lib"+" | find /c \":\" ";
        
        CMD(cmd2);
                
    }
    
}


