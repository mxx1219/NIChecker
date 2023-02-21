package common;

import astparser.MethodDeclarationVisitor;
import astparser.JavaDocVisitor;
import astparser.CertainLineStmtVisitor;
import astparser.CertainRangeStmtVisitor;
import astparser.AllStmtInMethodVisitor;
import org.eclipse.jdt.core.dom.*;
import parse.ASTUtils;
import parserForNegativeSample.*;

import java.io.File;
import java.util.*;

public class Main {

    private static final String srcDirLogFilePath = "../src_dir.txt";

    private static final Map<String, String> srcDirMap = new HashMap<>();
    private static final String suspiciousFilePath = "../SuspiciousCodePositions/";
    private static final String projectPath = "../projects/";
    private static final String outputD4JPath = "../output/";

    public static void main(String[] args){

        List<String> patterns = new ArrayList<>();
        patterns.add("InsertMissedStmt");
        patterns.add("InsertNullPointerChecker");
        patterns.add("MoveStmt");
        patterns.add("MutateConditionalExpr");
        patterns.add("MutateDataType");
        patterns.add("MutateLiteralExpr");
        patterns.add("MutateMethodInvExpr");
        patterns.add("MutateOperators");
        patterns.add("MutateReturnStmt");
        patterns.add("MutateVariable");
        patterns.add("RemoveBuggyStmt");

        if(args.length != 1){
            System.out.println("Usage: java -jar NIChecker-1.0-SNAPSHOT-jar-with-dependencies.jar <project_name>");
            System.exit(-1);
        }

        String version = args[0].trim();
        parseSrcDir();

        List<String> pattern_annotation = new ArrayList<>();
        StringBuilder pattern_annotation_string = new StringBuilder();
        int lineIndex = 1;
        System.out.printf("======%s======%n", version);
        String tmpSusFilePath = suspiciousFilePath + version + "/ranking.txt";
        File susFile = new File(tmpSusFilePath);
        if(! susFile.exists()) {
            System.out.println("Be excepted");
            return ;
        }
        File annoDir = new File(outputD4JPath  +  "/" + version + "/" + "anno_lines/");
        if (! annoDir.exists()){
            boolean status = annoDir.mkdirs();
            if(! status){
                System.exit(-1);
            }
        }
        List<String> susLines = Utils.readFileToList(susFile);
        for(String line: susLines){
            generateDataset(version, line, patterns, lineIndex, pattern_annotation);
            pattern_annotation_string.append(String.join(" ", pattern_annotation)).append("\n");
            pattern_annotation.clear();
            lineIndex++;
        }
        String outputFilePath = outputD4JPath  +  "/" + version + "/" + "pattern_annotation.txt";
        File outputFile = new File(outputFilePath);
        Utils.writeToFile(outputFile, pattern_annotation_string.toString());

    }

    public static void parseSrcDir(){
        File file = new File(srcDirLogFilePath);
        List<String> lines = Utils.readFileToList(file);
        for(String line: lines){
            srcDirMap.put(line.substring(0, line.indexOf(":")), line.substring(line.indexOf(":") + 1));
        }
    }

    public static void generateDataset(String version, String line, List<String> patterns, int lineIndex, List<String> pattern_annotation){
        System.out.println(line);
        String className = line.substring(0, line.indexOf("@"));
        int lineNumber = Integer.parseInt(line.substring(line.indexOf("@") + 1));
        String srcFilePath = projectPath + version + "/" + srcDirMap.get(version) + "/" + className.replace(".", "/")  +".java";
        File file = new File(srcFilePath);
        CompilationUnit cUnit = (CompilationUnit) Utils.genAST(file, ASTParser.K_COMPILATION_UNIT);
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        methodDeclarationVisitor.setCompilationUnit(cUnit);
        methodDeclarationVisitor.setLineNumber(lineNumber);
        cUnit.accept(methodDeclarationVisitor);
        MethodDeclaration methodDeclaration = methodDeclarationVisitor.getMethodDeclaration();

        if(methodDeclaration != null){
            JavaDocVisitor javaDocVisitor = new JavaDocVisitor();
            methodDeclaration.accept(javaDocVisitor);

            CertainLineStmtVisitor certainLineStmtVisitor = new CertainLineStmtVisitor();
            certainLineStmtVisitor.setCUnit(cUnit);
            certainLineStmtVisitor.setCertainLine(lineNumber);
            methodDeclaration.accept(certainLineStmtVisitor);
            int certainStmtIndex = certainLineStmtVisitor.getCertainStmtIndex();
            Statement certainStmt = certainLineStmtVisitor.getCertainStmt();
            if(certainStmtIndex == -1){
                CertainRangeStmtVisitor certainRangeStmtVisitor = new CertainRangeStmtVisitor();
                certainRangeStmtVisitor.setCUnit(cUnit);
                certainRangeStmtVisitor.setCertainLine(lineNumber);
                methodDeclaration.accept(certainRangeStmtVisitor);
                certainStmtIndex = certainRangeStmtVisitor.getCertainStmtIndex();
                certainStmt = certainRangeStmtVisitor.getCertainStmt();
            }
            if(certainStmtIndex <= 0){  // the certain statement is the method declaration block
                String outputFilePath = outputD4JPath  +  "/" + version + "/" + "anno_lines/" + String.valueOf(lineIndex) + ".txt";
                File outputFile = new File(outputFilePath);
                Utils.writeToFile(outputFile, "");
                for(int i = 0; i < patterns.size(); i++){
                    pattern_annotation.add("0");
                }
            }else{
                for(String pattern: patterns){
                    boolean satisfy = false;
                    switch (pattern) {
                        case "InsertMissedStmt":
                            pattern_annotation.add("1");
                            break;
                        case "InsertNullPointerChecker": {
                            AllExprVisitor visitor = new AllExprVisitor();
                            certainStmt.accept(visitor);
                            if (visitor.getNodes().size() > 0) {
                                for (ASTNode expr : visitor.getNodes()) {
                                    ASTNode parentNode = expr;
                                    while (parentNode != null && !(parentNode instanceof Statement)) {
                                        parentNode = parentNode.getParent();
                                    }
                                    if (parentNode != null) {
                                        if (parentNode.equals(certainStmt)) {
                                            satisfy = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MoveStmt":
                            pattern_annotation.add("1");
                            break;
                        case "MutateConditionalExpr": {
                            IfStmtVisitor visitor1 = new IfStmtVisitor();
                            certainStmt.accept(visitor1);
                            if (visitor1.getNodes().size() > 0 && visitor1.getNodes().contains(certainStmt)) {
                                satisfy = true;
                            }
                            if (!satisfy) {
                                WhileStmtVisitor visitor2 = new WhileStmtVisitor();
                                certainStmt.accept(visitor2);
                                if (visitor2.getNodes().size() > 0 && visitor2.getNodes().contains(certainStmt)) {
                                    satisfy = true;
                                }
                            }
                            if (!satisfy) {
                                ForStmtVisitor visitor3 = new ForStmtVisitor();
                                certainStmt.accept(visitor3);
                                if (visitor3.getNodes().size() > 0 && visitor3.getNodes().contains(certainStmt)) {
                                    satisfy = true;
                                }
                            }
                            if (!satisfy) {
                                DoStmtVisitor visitor4 = new DoStmtVisitor();
                                certainStmt.accept(visitor4);
                                if (visitor4.getNodes().size() > 0 && visitor4.getNodes().contains(certainStmt)) {
                                    satisfy = true;
                                }
                            }
                            if (!satisfy) {
                                CondInfixExprVisitor visitor5 = new CondInfixExprVisitor();
                                certainStmt.accept(visitor5);
                                if (visitor5.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor5.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MutateDataType": {
                            VariableDeclStmtVisitor visitor1 = new VariableDeclStmtVisitor();
                            certainStmt.accept(visitor1);
                            if (visitor1.getNodes().size() > 0 && visitor1.getNodes().contains(certainStmt)) {
                                satisfy = true;
                            }
                            if (!satisfy) {
                                VariableDeclExprVisitor visitor2 = new VariableDeclExprVisitor();
                                certainStmt.accept(visitor2);
                                if (visitor2.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor2.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                CastExprVisitor visitor3 = new CastExprVisitor();
                                certainStmt.accept(visitor3);
                                if (visitor3.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor3.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MutateLiteralExpr": {
                            BooleanLiteralVisitor visitor1 = new BooleanLiteralVisitor();
                            certainStmt.accept(visitor1);
                            if (visitor1.getNodes().size() > 0) {
                                for (ASTNode expr : visitor1.getNodes()) {
                                    ASTNode parentNode = expr;
                                    while (parentNode != null && !(parentNode instanceof Statement)) {
                                        parentNode = parentNode.getParent();
                                    }
                                    if (parentNode != null) {
                                        if (parentNode.equals(certainStmt)) {
                                            satisfy = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                CharacterLiteralVisitor visitor2 = new CharacterLiteralVisitor();
                                certainStmt.accept(visitor2);
                                if (visitor2.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor2.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                NumberLiteralVisitor visitor3 = new NumberLiteralVisitor();
                                certainStmt.accept(visitor3);
                                if (visitor3.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor3.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            } // except StringLiteralVisitor
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MutateMethodInvExpr": {
                            MethodInvVisitor visitor1 = new MethodInvVisitor();
                            certainStmt.accept(visitor1);
                            if (visitor1.getNodes().size() > 0) {
                                for (ASTNode expr : visitor1.getNodes()) {
                                    ASTNode parentNode = expr;
                                    while (parentNode != null && !(parentNode instanceof Statement)) {
                                        parentNode = parentNode.getParent();
                                    }
                                    if (parentNode != null) {
                                        if (parentNode.equals(certainStmt)) {
                                            satisfy = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                ClassInsCreationVisitor visitor2 = new ClassInsCreationVisitor();
                                certainStmt.accept(visitor2);
                                if (visitor2.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor2.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                ConstructorInvVisitor visitor3 = new ConstructorInvVisitor();
                                certainStmt.accept(visitor3);
                                if (visitor3.getNodes().size() > 0 && visitor3.getNodes().contains(certainStmt)) {
                                    satisfy = true;
                                }
                            }
                            if (!satisfy) {
                                SuperConstructorInvVisitor visitor4 = new SuperConstructorInvVisitor();
                                certainStmt.accept(visitor4);
                                if (visitor4.getNodes().size() > 0 && visitor4.getNodes().contains(certainStmt)) {
                                    satisfy = true;
                                }
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MutateOperators": {
                            InfixExprVisitor visitor1 = new InfixExprVisitor();
                            certainStmt.accept(visitor1);
                            if (visitor1.getNodes().size() > 0) {
                                for (ASTNode expr : visitor1.getNodes()) {
                                    ASTNode parentNode = expr;
                                    while (parentNode != null && !(parentNode instanceof Statement)) {
                                        parentNode = parentNode.getParent();
                                    }
                                    if (parentNode != null) {
                                        if (parentNode.equals(certainStmt)) {
                                            satisfy = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                AssignmentVisitor visitor2 = new AssignmentVisitor();
                                certainStmt.accept(visitor2);
                                if (visitor2.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor2.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!satisfy) {
                                InstanceofExprVisitor visitor3 = new InstanceofExprVisitor();
                                certainStmt.accept(visitor3);
                                if (visitor3.getNodes().size() > 0) {
                                    for (ASTNode expr : visitor3.getNodes()) {
                                        ASTNode parentNode = expr;
                                        while (parentNode != null && !(parentNode instanceof Statement)) {
                                            parentNode = parentNode.getParent();
                                        }
                                        if (parentNode != null) {
                                            if (parentNode.equals(certainStmt)) {
                                                satisfy = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MutateReturnStmt": {
                            ReturnStmtVisitor visitor = new ReturnStmtVisitor();
                            certainStmt.accept(visitor);
                            if (visitor.getNodes().size() > 0 && visitor.getNodes().contains(certainStmt)) {
                                satisfy = true;
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "MutateVariable": {
                            VariableVisitor visitor = new VariableVisitor();
                            certainStmt.accept(visitor);
                            if (visitor.getNodes().size() > 0) {
                                for (ASTNode expr : visitor.getNodes()) {
                                    ASTNode parentNode = expr;
                                    while (parentNode != null && !(parentNode instanceof Statement)) {
                                        parentNode = parentNode.getParent();
                                    }
                                    if (parentNode != null) {
                                        if (parentNode.equals(certainStmt)) {
                                            satisfy = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            pattern_annotation.add(satisfy ? "1" : "0");
                            break;
                        }
                        case "RemoveBuggyStmt":
                            pattern_annotation.add("1");
                            break;
                    }
                }
                String methodString = methodDeclaration.toString();
                ASTNode rootNode = Utils.genAST(methodString, ASTParser.K_CLASS_BODY_DECLARATIONS);
                ASTNode tmpNode = ASTUtils.getChildren(rootNode).get(1);
                if(tmpNode instanceof MethodDeclaration){
                    MethodDeclaration onlyMethodDecl = (MethodDeclaration) tmpNode;
                    AllStmtInMethodVisitor allStmtInMethodVisitor = new AllStmtInMethodVisitor();
                    onlyMethodDecl.accept(allStmtInMethodVisitor);
                    certainStmt = allStmtInMethodVisitor.getStatements().get(certainStmtIndex);
                    int certainLine = Utils.parseLineNumber(onlyMethodDecl.toString(), certainStmt.getStartPosition());
                    String outputFilePath = outputD4JPath  + "/" + version + "/" + "anno_lines/" + String.valueOf(lineIndex) + ".txt";
                    File outputFile = new File(outputFilePath);
                    String[] stringSlices = onlyMethodDecl.toString().split("\n");
                    stringSlices[certainLine - 1] = " rank2fixstart " + stringSlices[certainLine - 1] + " rank2fixend ";
                    String resultString = String.join("\n", stringSlices);
                    Utils.writeToFile(outputFile, resultString);
                }
            }
        }else{
            String outputFilePath = outputD4JPath  + "/" + version + "/" + "anno_lines/" + lineIndex + ".txt";
            File outputFile = new File(outputFilePath);
            Utils.writeToFile(outputFile, "");
            for(int i = 0; i < patterns.size(); i++){
                pattern_annotation.add("0");
            }
        }
    }
}
