package parserForNegativeSample;

import org.eclipse.jdt.core.dom.SimpleName;
import parse.ASTUtils;

public class VariableVisitor extends NodeVisitor{

    public boolean visit(SimpleName node){
        if(ASTUtils.isVariable(node)) {
            nodes.add(node);
        }
        return true;
    }
}
