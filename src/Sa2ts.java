import sa.*;
import ts.Ts;

public class Sa2ts extends SaDepthFirstVisitor <Void>{

    Ts tableGlobale;
    Ts tableLocale;

    public Sa2ts(SaNode root) {
        tableGlobale = new Ts();
        root.accept(this);
    }

    @Override
    public void defaultIn(SaNode node) {
        super.defaultIn(node);
    }

    @Override
    public Void visit(SaVarSimple node) {
        if(!tableLocale.variables.containsKey(node.getNom())) {
            if (!tableGlobale.variables.containsKey(node.getNom())) {
                throw new RuntimeException("Variable " + node.getNom() + " non déclarée");
            }
        }
        return super.visit(node);
    }

    @Override
    public Void visit(SaVarIndicee node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecTab node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecFonc node) {
        node.
    }

    @Override
    public Void visit(SaDecVar node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaAppel node) {
        return super.visit(node);
    }
}
