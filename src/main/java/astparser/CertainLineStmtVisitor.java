package astparser;

import org.eclipse.jdt.core.dom.*;

public class CertainLineStmtVisitor extends ASTVisitor{

    private CompilationUnit cUnit = null;
    private int certainLine = -1;
    private int allStmtIndex = 0;
    private int certainStmtIndex = -1;
    private Statement certainStmt = null;

    public boolean visit(AssertStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(BreakStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(ConstructorInvocation node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(ContinueStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(DoStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(EmptyStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(EnhancedForStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(ExpressionStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(ForStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(IfStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(LabeledStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(ReturnStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(SuperConstructorInvocation node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(SwitchCase node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(SwitchStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(SynchronizedStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(ThrowStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(TryStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(TypeDeclarationStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(VariableDeclarationStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(WhileStatement node){
        return parseCertainLineStmt(node);
    }

    public boolean visit(Block node){
        return parseCertainLineStmt(node);
    }

    public boolean parseCertainLineStmt(Statement node){
        if(cUnit.getLineNumber(node.getStartPosition()) == certainLine){
            certainStmtIndex = allStmtIndex;
            certainStmt = node;
            return false;
        } else{
            if(cUnit.getLineNumber(node.getStartPosition() + node.getLength()) == certainLine){
                certainStmtIndex = allStmtIndex;
                certainStmt = node;
            }
            allStmtIndex += 1;
            return true;
        }
    }

    public void setCUnit(CompilationUnit cUnit){
        this.cUnit = cUnit;
    }

    public void setCertainLine(int certainLine){
        this.certainLine = certainLine;
    }

    public int getCertainStmtIndex() {
        return certainStmtIndex;
    }

    public Statement getCertainStmt(){
        return certainStmt;
    }
}
