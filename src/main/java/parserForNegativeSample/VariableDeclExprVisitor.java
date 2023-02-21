package parserForNegativeSample;

import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

public class VariableDeclExprVisitor extends NodeVisitor{

    public boolean visit(VariableDeclarationExpression node){
        nodes.add(node);
        return true;
    }
}
