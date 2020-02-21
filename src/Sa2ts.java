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
        if(!tableGlobale.variables.containsKey(node.getNom())) throw new RuntimeException("La variable " + node.getNom() + " n'a pas été définie");
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecTab node) {
        String identif = node.getNom();
        if(tableLocale != null){

        }
        tableGlobale.addVar(node.getNom(),node.getTaille());
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecFonc node) {
        String identif = node.getNom();
        tableLocale = new Ts();
        if(tableGlobale.fonctions.containsKey(identif)){
            throw new RuntimeException("Fonction " + identif + " déjà déclarée");
        }
        int arg ;
        if(node.getParametres() == null) arg = 0;
        else arg = node.getParametres().length();
        tableGlobale.addFct(identif,arg , new Ts(), node);
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
        if(!tableGlobale.fonctions.containsKey(node.getNom())) throw new RuntimeException("La fonction " + node.getNom() + " n'a pas été déclarée.");
        if(tableGlobale.fonctions.get(node.getNom()).nbArgs != node.getArguments().length()) throw new RuntimeException("Le nombre d'argument de la fonction "+ node.getNom() + " est invalide.");
        if (!tableGlobale.fonctions.containsKey("main")) throw new RuntimeException("Pas de fonction main");
        return super.visit(node);
    }

    public Ts getTableGlobale() {
        return tableGlobale;
    }
}
