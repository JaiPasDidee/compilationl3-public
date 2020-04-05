import sa.*;
import ts.Ts;
import ts.TsItemVar;

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
        node.tsItem = (tableGlobale.variables.containsKey(node.getNom())) ? tableGlobale.variables.get(node.getNom()) : tableLocale.variables.get(node.getNom());
        if(!(tableLocale != null && tableLocale.variables.containsKey(node.getNom()))) {
            if (!tableGlobale.variables.containsKey(node.getNom())) {
                throw new RuntimeException("Variable " + node.getNom() + " non déclarée");
            }
        }
        return super.visit(node);
    }

    @Override
    public Void visit(SaVarIndicee node) {
        node.tsItem = tableGlobale.variables.get(node.getNom());
        if(!tableGlobale.variables.containsKey(node.getNom())) throw new RuntimeException("La variable " + node.getNom() + " n'a pas été définie");
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecTab node) {
        String identif = node.getNom();
        if(tableLocale != null) throw new RuntimeException("On ne peut pas déclarer un tableau dans une fonction");
        if(tableGlobale.variables.containsKey(identif)) throw new RuntimeException("Le tableau " + identif + " a déjà été défini");
        tableGlobale.addVar(node.getNom(),node.getTaille());
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecFonc node) {
        String identif = node.getNom();
        tableLocale = new Ts();
        if(tableGlobale.fonctions.containsKey(identif)) throw new RuntimeException("Fonction " + identif + " déjà déclarée");
        int arg ;
        if(node.getVariable() == null) arg = 0;
        else arg = node.getVariable().length();
        tableGlobale.addFct(identif,arg , tableLocale, node);
        SaLDec lparam = node.getVariable();
        SaDec tete;

        if (node.getParametres() != null) {
            node.getParametres().accept(this);
        }
        while (lparam != null && (tete = lparam.getTete()) != null) {
            tableLocale.addParam(tete.getNom());
            lparam = lparam.getQueue();
        }

        node.getCorps().accept(this);
        tableLocale = null;
        return  null;
    }



    @Override
    public Void visit(SaDecVar node) {
        Ts table;
        if(tableLocale == null) table = tableGlobale;
        else table = tableLocale;
        if((table.variables.containsKey(node.getNom()))) throw new RuntimeException("Variable " + node.getNom() + " déjà déclarée");
        table.addVar(node.getNom(), 1);
        return super.visit(node);
    }


    @Override
    public Void visit(SaAppel node) {
        node.tsItem = tableGlobale.fonctions.get(node.getNom());
        if (!tableGlobale.fonctions.containsKey(node.getNom())) {
            throw new RuntimeException("La fonction " + node.getNom() + "n'a pas été définie");
        }
        return super.visit(node);
    }

    public Ts getTableGlobale() {
        return tableGlobale;
    }
}
