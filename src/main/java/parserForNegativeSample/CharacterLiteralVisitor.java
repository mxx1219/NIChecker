package parserForNegativeSample;

import org.eclipse.jdt.core.dom.CharacterLiteral;

public class CharacterLiteralVisitor extends NodeVisitor{

    public boolean visit(CharacterLiteral node){
        nodes.add(node);
        return true;
    }
}
