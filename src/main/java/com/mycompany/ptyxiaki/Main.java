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
import java.util.ArrayList;
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
    
    
    
    
    
    //statik metablites stous metrites gia methodous kai bibliothikes
         public  static int i=0,i2=0;
        //metrame to plithos toon methodon kai toon biblhothikon

        private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        i++;
        //System.out.println("Method Name Printed: " + md.getName()); 
        }
        @Override
        public void visit(ImportDeclaration md, Void arg) {
        super.visit(md, arg);
        i2++;
        //System.out.println("Library Name Printed: " + md.getName());
        }
 }
        //emafanizoume to plhthos ton methodon kai ton bibliothikon

        public static void count(){
            System.out.println("Number of methods: "+i);
            System.out.println("Number of libraries: "+i2);
        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    static ArrayList<String> Source_Paths = new ArrayList<String>();
   
    
    
    
    
    
    public static void listFilesForFolder(final File folder2) {

    for (final File fileEntry : folder2.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {

            if(test(fileEntry.getName(), ".java")){
                Source_Paths.add(fileEntry.getPath());
               //System.out.println(fileEntry.getPath());
            }

        }
    }
}

    public static boolean test(String a, String b) {
    if (a.length() > 5) {
        a = a.substring(a.length() - 5);
        if (b.length() > 4) {
            b = b.substring(b.length() - 5);
        }
    }
    return a.equals(b);
}
    
    
    
    
  


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
    
    
    
    
    
    private static final String FILE_PATH = "E:\\randomStuff\\Ptyxiaki\\Code\\src\\main\\java\\calculator\\MetricsFilter.java";
    
    
    private static final String FILE_PATH2="E:/Mathimata/Netbeens_Projects/Ptyxiaki/src/main/java/com/mycompany/ptyxiaki/Example.java";
    
    private static final String FILE_PATH3="C:\\Netbeans_Project\\mavenproject1";
    
     private static final String FILE_PATH4="C:\\Netbeans_Project\\Ptyxiaki\\target\\lib\\sources";
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        // TODO code application logic here
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
       
        
        
        
        CompilationUnit cu1 = StaticJavaParser.parse(new File(FILE_PATH2));
        
        NodeList<ImportDeclaration> a = cu1.getImports();
     
        NUL(a);
        
        printPomDependencies(FILE_PATH3);
        
        
        
        
        
        
        
        

        String CWD = System.getProperty("user.dir");
        
        String cmd_Command="cd " + FILE_PATH3+" & mvn dependency:copy-dependencies -DexcludeTransitive -DoutputDirectory="+CWD+"\\target\\lib";
        
        CMD(cmd_Command);
        
        String cmd2 ="dir /a:-d /s /b "+CWD + "\\target\\lib"+" | find /c \":\" ";
        
        CMD(cmd2);
        
        String cmd3 = "cd "+FILE_PATH3 + " & mvn dependency:copy-dependencies -Dclassifier=sources -DexcludeTransitive -DoutputDirectory="+CWD+"\\target\\lib\\sources";
        
        CMD(cmd3);
        
        File folder = new File(FILE_PATH4);
        File[] listOfFiles = folder.listFiles();


        for (int i = 0; i < listOfFiles.length; i++) {
            String cmd4="cd "+FILE_PATH4 +" & jar xf "+listOfFiles[i].getName();
            CMD(cmd4);
            //

        }
        
        
        final File folder2 = new File(FILE_PATH4);
        listFilesForFolder(folder2);
        
        
        
        
        
        //Gets the Source_Paths
        
        for (int i=0; i<Source_Paths.size();i++)
        {
            
            CompilationUnit cu3 = StaticJavaParser.parse(new File(Source_Paths.get(i)));
            //System.out.println(cu3);
            
            VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
            methodNameVisitor.visit(cu, null);
            
            
        }
        count();
        
        
        
        
        
        
        
              
    }
    
}


