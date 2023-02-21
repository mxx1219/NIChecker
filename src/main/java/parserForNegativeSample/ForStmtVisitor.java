package parserForNegativeSample;

import org.eclipse.jdt.core.dom.ForStatement;

public class ForStmtVisitor extends NodeVisitor{

    public boolean visit(ForStatement node){
        nodes.add(node);
        return true;
    }
}
