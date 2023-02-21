package parse;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class ASTUtils {

    public static List<ASTNode> getChildren(ASTNode node){
        List<ASTNode> childNodes = new ArrayList<>();
        List<Object> listProperty = node.structuralPropertiesForType();
        for (Object o : listProperty) {
            StructuralPropertyDescriptor propertyDescriptor = (StructuralPropertyDescriptor) o;
            if (propertyDescriptor instanceof ChildListPropertyDescriptor) {
                ChildListPropertyDescriptor childListPropertyDescriptor = (ChildListPropertyDescriptor) propertyDescriptor;
                Object children = node.getStructuralProperty(childListPropertyDescriptor);
                List<ASTNode> childrenNodes = (List<ASTNode>) children;
                for (ASTNode childNode : childrenNodes) {
                    if (childNode instanceof Javadoc) {
//                        System.out.println("Delete Javadoc Node!");
                        childNode.delete();
                        continue;
                    }
                    if (childNode == null) {
                        continue;
                    }
                    childNodes.add(childNode);
                }
            } else if (propertyDescriptor instanceof ChildPropertyDescriptor) {
                ChildPropertyDescriptor childPropertyDescriptor = (ChildPropertyDescriptor) propertyDescriptor;
                Object child = node.getStructuralProperty(childPropertyDescriptor);
                ASTNode childNode = (ASTNode) child;
                if (childNode instanceof Javadoc) {
                    childNode.delete();
                    continue;
                }
                if (childNode == null) {
                    continue;
                }
                childNodes.add(childNode);
            }
        }
        return childNodes;
    }

    public static boolean isVariable(SimpleName node){
        boolean ifInStmt = false;
        ASTNode parent = node.getParent();
        while(parent != null){
            if(parent instanceof Statement){
                ifInStmt = true;
                break;
            }
            parent = parent.getParent();
        }
        if(ifInStmt){
            if(node.getParent() instanceof MethodInvocation) {
                String property = node.getLocationInParent().getId();
                if (! property.equals("name")){
                    return !node.isDeclaration();
                }
            } else{
                if(! (node.getParent() instanceof Type)){
                    return !node.isDeclaration();
                }
            }
        }
        return false;
    }
}
