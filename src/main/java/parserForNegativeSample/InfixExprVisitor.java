package parserForNegativeSample;

import org.eclipse.jdt.core.dom.InfixExpression;

public class InfixExprVisitor extends NodeVisitor{

    public boolean visit(InfixExpression node){
        nodes.add(node);
        return true;
    }
}
