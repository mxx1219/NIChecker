package parserForNegativeSample;

import org.eclipse.jdt.core.dom.DoStatement;

public class DoStmtVisitor extends NodeVisitor{

    public boolean visit(DoStatement node){
        nodes.add(node);
        return true;
    }
}
