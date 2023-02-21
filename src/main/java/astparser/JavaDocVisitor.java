package astparser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;

public class JavaDocVisitor extends ASTVisitor{

    public boolean visit(Javadoc node){
        node.delete();
        return false;
    }
}
