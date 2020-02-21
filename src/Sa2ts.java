import sa.*;

public class Sa2ts extends SaDepthFirstVisitor <Void>{

    @Override
    public void defaultIn(SaNode node) {
        super.defaultIn(node);
    }

    @Override
    public Void visit(SaVarSimple node) {
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
        return super.visit(node);
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
