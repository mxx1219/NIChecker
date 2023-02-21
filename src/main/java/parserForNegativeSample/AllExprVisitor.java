package parserForNegativeSample;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class AllExprVisitor extends ASTVisitor{

    private final List<ASTNode> nodes = new ArrayList<>();

    public boolean visit(Annotation node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ArrayAccess node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ArrayCreation node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ArrayInitializer node){
        nodes.add(node);
        return true;
    }

    public boolean visit(Assignment node){
        nodes.add(node);
        return true;
    }

    public boolean visit(BooleanLiteral node){
        nodes.add(node);
        return true;
    }

    public boolean visit(CastExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(CharacterLiteral node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ClassInstanceCreation node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ConditionalExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(CreationReference node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ExpressionMethodReference node){
        nodes.add(node);
        return true;
    }

    public boolean visit(FieldAccess node){
        nodes.add(node);
        return true;
    }

    public boolean visit(InfixExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(InstanceofExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(LambdaExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(MethodInvocation node){
        nodes.add(node);
        return true;
    }

    public boolean visit(MethodReference node){
        nodes.add(node);
        return true;
    }

    public boolean visit(Name node){
        nodes.add(node);
        return true;
    }

    public boolean visit(NullLiteral node){
        nodes.add(node);
        return true;
    }

    public boolean visit(NumberLiteral node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ParenthesizedExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(PostfixExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(PrefixExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(StringLiteral node){
        nodes.add(node);
        return true;
    }

    public boolean visit(SuperFieldAccess node){
        nodes.add(node);
        return true;
    }

    public boolean visit(SuperMethodInvocation node){
        nodes.add(node);
        return true;
    }

    public boolean visit(SuperMethodReference node){
        nodes.add(node);
        return true;
    }

    public boolean visit(SwitchExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(ThisExpression node){
        nodes.add(node);
        return true;
    }

    public boolean visit(TypeLiteral node){
        nodes.add(node);
        return true;
    }

    public boolean visit(TypeMethodReference node){
        nodes.add(node);
        return true;
    }

    public boolean visit(VariableDeclarationExpression node){
        nodes.add(node);
        return true;
    }

    public List<ASTNode> getNodes() {
        return nodes;
    }
}
