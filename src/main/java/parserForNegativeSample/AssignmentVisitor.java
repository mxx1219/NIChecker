package parserForNegativeSample;

import org.eclipse.jdt.core.dom.Assignment;

public class AssignmentVisitor extends NodeVisitor{

    public boolean visit(Assignment node){
        nodes.add(node);
        return true;
    }
}
