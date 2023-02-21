package parserForNegativeSample;

import org.eclipse.jdt.core.dom.CastExpression;

public class CastExprVisitor extends NodeVisitor{

    public boolean visit(CastExpression node){
        nodes.add(node);
        return true;
    }
}
