package parserForNegativeSample;

import org.eclipse.jdt.core.dom.NumberLiteral;

public class NumberLiteralVisitor extends NodeVisitor{

    public boolean visit(NumberLiteral node){
        nodes.add(node);
        return true;
    }
}
