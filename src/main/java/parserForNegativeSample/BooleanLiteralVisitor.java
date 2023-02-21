package parserForNegativeSample;

import org.eclipse.jdt.core.dom.BooleanLiteral;

public class BooleanLiteralVisitor extends NodeVisitor{

    public boolean visit(BooleanLiteral node){
        nodes.add(node);
        return true;
    }
}
