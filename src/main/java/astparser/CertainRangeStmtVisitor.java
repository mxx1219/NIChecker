package astparser;

import org.eclipse.jdt.core.dom.*;

public class CertainRangeStmtVisitor extends ASTVisitor{

    private CompilationUnit cUnit = null;
    private int certainLine = -1;
    private int allStmtIndex = 0;
    private int certainStmtIndex = -1;
    private int minRangeSize = Integer.MAX_VALUE;
    private Statement certainStmt = null;

    public boolean visit(AssertStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(BreakStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(ConstructorInvocation node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(ContinueStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(DoStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(EmptyStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(EnhancedForStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(ExpressionStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(ForStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(IfStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(LabeledStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(ReturnStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(SuperConstructorInvocation node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(SwitchCase node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(SwitchStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(SynchronizedStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(ThrowStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(TryStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(TypeDeclarationStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(VariableDeclarationStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(WhileStatement node){
        return parseCertainRangeStmt(node);
    }

    public boolean visit(Block node){
        return parseCertainRangeStmt(node);
    }

    public boolean parseCertainRangeStmt(Statement node){
        int startLine = cUnit.getLineNumber(node.getStartPosition());
        int endLine = cUnit.getLineNumber(node.getStartPosition() + node.getLength());
        int range = endLine - startLine;
        if(cUnit.getLineNumber(node.getStartPosition()) < certainLine
                && cUnit.getLineNumber(node.getStartPosition() + node.getLength()) > certainLine){
            if(range < minRangeSize){
                minRangeSize = range;
                certainStmtIndex = allStmtIndex;
                certainStmt = node;
            }
        }
        allStmtIndex += 1;
        return true;
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
