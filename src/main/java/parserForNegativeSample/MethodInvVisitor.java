package parserForNegativeSample;

import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvVisitor extends NodeVisitor{

    public boolean visit(MethodInvocation node){
        nodes.add(node);
        return true;
    }
}
