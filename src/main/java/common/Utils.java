package common;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AST;


import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static ASTNode genAST(String source, int type){
        ASTParser astParser = ASTParser.newParser(AST.JLS4);
        Map<String, String> options = JavaCore.getOptions();
        JavaCore.setComplianceOptions(JavaCore.VERSION_1_4, options);
        astParser.setCompilerOptions(options);  
        astParser.setSource(source.toCharArray());
        astParser.setKind(type);
        astParser.setResolveBindings(true);
        return astParser.createAST(null);
    }

    public static ASTNode genAST(File file, int type){
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null){
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return genAST(stringBuffer.toString(), type);
    }

    public static List<String> readFileToList(File file){
        List<String> fileList = new ArrayList<>();
        String line;
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null){
                fileList.add(line);
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return fileList;
    }

    public static void writeToFile(File file, String content){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static int parseLineNumber(String methodString, int stmtStartPos){
        methodString = methodString.substring(0, stmtStartPos);
        int enterCount = 0;
        char[] chars = methodString.toCharArray();
        for(char character: chars) {
            if (character == '\n') {
                enterCount++;
            }
        }
        return enterCount + 1;
    }
}
