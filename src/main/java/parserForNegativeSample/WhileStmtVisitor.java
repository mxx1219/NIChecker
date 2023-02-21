package parserForNegativeSample;

import org.eclipse.jdt.core.dom.WhileStatement;

public class WhileStmtVisitor extends NodeVisitor{

    public boolean visit(WhileStatement node){
        nodes.add(node);
        return true;
    }
}
