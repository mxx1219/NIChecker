package parserForNegativeSample;

import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class VariableDeclStmtVisitor extends NodeVisitor{

    public boolean visit(VariableDeclarationStatement node){
        nodes.add(node);
        return true;
    }
}
