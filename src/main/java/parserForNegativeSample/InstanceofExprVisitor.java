package parserForNegativeSample;

import org.eclipse.jdt.core.dom.InstanceofExpression;

public class InstanceofExprVisitor extends NodeVisitor{

    public boolean visit(InstanceofExpression node){
        nodes.add(node);
        return true;
    }
}
