package parserForNegativeSample;

import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

public class SuperConstructorInvVisitor extends NodeVisitor{

    public boolean visit(SuperConstructorInvocation node){
        nodes.add(node);
        return true;
    }
}
