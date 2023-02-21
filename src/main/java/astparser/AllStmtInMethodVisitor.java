package astparser;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class AllStmtInMethodVisitor extends ASTVisitor{

    private final List<Statement> statements = new ArrayList<>();

    public boolean visit(AssertStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(BreakStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(ConstructorInvocation node){
        statements.add(node);
        return true;
    }

    public boolean visit(ContinueStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(DoStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(EmptyStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(EnhancedForStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(ExpressionStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(ForStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(IfStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(LabeledStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(ReturnStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(SuperConstructorInvocation node){
        statements.add(node);
        return true;
    }

    public boolean visit(SwitchCase node){
        statements.add(node);
        return true;
    }

    public boolean visit(SwitchStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(SynchronizedStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(ThrowStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(TryStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(TypeDeclarationStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(VariableDeclarationStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(WhileStatement node){
        statements.add(node);
        return true;
    }

    public boolean visit(Block node){
        statements.add(node);
        return true;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
