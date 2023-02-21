package parserForNegativeSample;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public class ClassInsCreationVisitor extends NodeVisitor{

    public boolean visit(ClassInstanceCreation node){
        nodes.add(node);
        return true;
    }
}