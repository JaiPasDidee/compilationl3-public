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
        if(!tableLocale.variables.containsKey(node.getNom()) && tableLocale != null) {
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
        String identif = node.getNom()
        if(tableLocale != null){

        }

        return super.visit(node);
    }

    @Override
    public Void visit(SaDecFonc node) {
        String identif = node.getNom();
        tableLocale = new Ts();
        if(tableGlobale.fonctions.containsKey(identif)){
            throw new RuntimeException("Fonction " + identif + " déjà déclarée");
        }
        tableGlobale.addFct(identif, node.getParametres().length(), new Ts(), node);
        tableLocale = null;
        return  super.visit(node);
    }



    @Override
    public Void visit(SaDecVar node) {
        Ts porte = node.tsItem.portee;
        if(porte.variables.containsKey(node.getNom()) || (tableLocale.fonctions.containsKey(node.getNom()))) throw new RuntimeException("Variable " + node.getNom() + " déjà déclarée");
        porte.addVar(node.getNom(), 1);
        return super.visit(node);
    }

    @Override
    public Void visit(SaAppel node) {
        return super.visit(node);
    }
}
