package parserForNegativeSample;

import org.eclipse.jdt.core.dom.ReturnStatement;

public class ReturnStmtVisitor extends NodeVisitor{

    public boolean visit(ReturnStatement node){
        nodes.add(node);
        return true;
    }
}
