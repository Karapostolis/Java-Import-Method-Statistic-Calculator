/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ptyxiaki;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    // Checks if the elements of the Array List you are giving as a parameter exist in the newList and if it doesn't it adds it in the newList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {

            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }
    
    
    // Initialise Static ArrayList arm
    static ArrayList<String> arm = new ArrayList<String>();
    
    // It gives the variable arm all the methods that exist in the Java file that you are parsing
    
    private static class MethodVisitor extends VoidVisitorAdapter
    {
        @Override
        public void visit(MethodCallExpr methodCall, Object arg)
        {


            arm.add(methodCall.getName().asString());

            List<Expression> args = methodCall.getArguments();
            if (args != null)
                handleExpressions(args);

        }
    

        private void handleExpressions(List<Expression> expressions)
        {
            for (Expression expr : expressions)
            {
                if (expr instanceof MethodCallExpr)
                    visit((MethodCallExpr) expr, null);
                else if (expr instanceof BinaryExpr)
                {
                    BinaryExpr binExpr = (BinaryExpr)expr;
                    handleExpressions(Arrays.asList(binExpr.getLeft(), binExpr.getRight()));
                }
            }
        }
        
    }
    
    
   
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

        public static int count(){
            //System.out.println("Number of methods: "+i);
            //System.out.println("Number of libraries: "+i2);
            return i;
        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    static ArrayList<String> Source_Paths = new ArrayList<String>();
   
    
    
    
    
    // Scans all the project and adds into the ArrayList Source_Paths all the paths of the Java files 
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
    
    // This method enables us to run cmd commands in windows
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
         
        
        
        
        
        
        //Gets the Source_Paths
        /*
        for (int i=0; i<Source_Paths.size();i++)
        {
            
            CompilationUnit cu3 = StaticJavaParser.parse(new File(Source_Paths.get(i)));
            System.out.println(cu3);
            
            VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
            methodNameVisitor.visit(cu, null);
            
            
        }
        count();*/
        
        
        
       
        
       
       CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        //combinedTypeSolver.add(new JavaParserTypeSolver(new File("C:\Users\mario\Desktop\JavaCodeMetricsCalculator-src_code_analyzer")));
        //combinedTypeSolver.add(new ReflectionTypeSolver());
        ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy(parserConfiguration).collect(Path.of("C:\\Netbeans_Project\\Ptyxiaki\\target\\lib\\sources"));

        for (SourceRoot sourceRoot : projectRoot.getSourceRoots()) {
    try {
        sourceRoot.tryToParse();
        List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
        System.out.println(compilationUnits);
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }
        }
        
        
     /*   
        
      try {
            // create class object
            Class classobj = Main.class;
  
            // get list of methods
            Method[] methods = classobj.getDeclaredMethods();
  
            // get the name of every method present in the list
            for (Method method : methods) {
  
                String MethodName = method.getName();
                System.out.println("Name of the method: "
                                   + MethodName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            */
     
     
     /*
     System.out.println("EDWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
     
    // getAllMethodsInHierarchy(Main.class);
     
     
     
     
     
     Method[] marios = getAllMethodsInHierarchy(Main.class);
     for(int i = 0;i<marios.length;i++)
     {
         System.out.println(marios[i].getName());
     }*/
     
     
     FileInputStream in = new FileInputStream("C:\\Netbeans_Project\\Ptyxiaki\\src\\main\\java\\com\\mycompany\\ptyxiaki\\Main.java");

        CompilationUnit cu5;
        try
        {
            cu5 = StaticJavaParser.parse(in);
        }
        finally
        {
            in.close();
        }
        new MethodVisitor().visit(cu5, null);
        ArrayList<String>
            newList=removeDuplicates(arm);
        int sum=0;
        for(int o=0; o<newList.size(); o++){
            System.out.println(newList.get(o));
            sum=o;
        }
     
        
        final File folder2 = new File(FILE_PATH4);
        listFilesForFolder(folder2);
        
        
         for (int i=0; i<Source_Paths.size();i++)
        {
            
            CompilationUnit cu3 = StaticJavaParser.parse(new File(Source_Paths.get(i)));
            //System.out.println(cu3);
            
            VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
            methodNameVisitor.visit(cu, null);
            
            
        }
        double AllMethodsCounter = count();
        
        
        double PLMI = (newList.size()/AllMethodsCounter)*100;
        
        System.out.println("The Percentage of Library Methods Invoked is: "+PLMI + " %");
     
    }
    
}


