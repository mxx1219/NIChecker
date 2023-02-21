package parserForNegativeSample;

import org.eclipse.jdt.core.dom.ConstructorInvocation;

public class ConstructorInvVisitor extends NodeVisitor{

    public boolean visit(ConstructorInvocation node){
        nodes.add(node);
        return true;
    }
}
