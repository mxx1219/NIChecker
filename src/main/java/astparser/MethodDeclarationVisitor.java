package astparser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor{

    private int line = -1;
    private CompilationUnit cUnit = null;
    private MethodDeclaration methodDeclaration = null;

    public boolean visit(MethodDeclaration node){
        if(cUnit.getLineNumber(node.getStartPosition()) <= line
                && cUnit.getLineNumber(node.getStartPosition() + node.getLength()) >= line){
            methodDeclaration = node;
            return false;
        }
        return true;
    }

    public void setLineNumber(int line){
        this.line = line;
    }

    public void setCompilationUnit(CompilationUnit cUnit){
        this.cUnit = cUnit;
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }
}
