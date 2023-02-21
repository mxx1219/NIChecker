package parserForNegativeSample;

import org.eclipse.jdt.core.dom.IfStatement;

public class IfStmtVisitor extends NodeVisitor{

    public boolean visit(IfStatement node){
        nodes.add(node);
        return true;
    }
}
