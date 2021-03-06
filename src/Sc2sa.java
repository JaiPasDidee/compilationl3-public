import sa.*;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {

    private SaNode returnValue;

    public SaNode getRoot() {
        return returnValue;
    }

    private <T> T retrieveNode(Node node) {
        if(node == null)
            return null;
        node.apply(this);
        return (T)returnValue;
    }

    @Override
    public void caseAEgalExp2(AEgalExp2 node) {
        SaExp op1 = retrieveNode(node.getExp2());
        SaExp op2 = retrieveNode(node.getExp3());
        this.returnValue = new SaExpEqual(op1,op2);
    }

    @Override
    public void caseAPlusExp3(APlusExp3 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp3().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp4().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpAdd(op1,op2);
    }

    @Override
    public void caseADecvarldecfoncProgramme(ADecvarldecfoncProgramme node) {
        SaLDec op1 = retrieveNode(node.getOptdecvar());
        SaLDec op2 = retrieveNode(node.getListedecfonc());
        this.returnValue = new SaProg(op1, op2);
    }

    @Override
    public void caseALdecfoncProgramme(ALdecfoncProgramme node) {
        SaLDec op1 = retrieveNode(node.getListedecfonc());
        this.returnValue = new SaProg(null, op1);
    }

    @Override
    public void caseADecvarldecvarListedecvar(ADecvarldecvarListedecvar node) {
        SaDec op1 = null;
        SaLDec op2 = null;
        node.getDecvar().apply(this);
        op1 = (SaDec) this.returnValue;
        node.getListedecvarbis().apply(this);
        op2 = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(op1, op2);
    }

    @Override
    public void caseADecvarListedecvar(ADecvarListedecvar node) {
        SaDec op1 = null;
        node.getDecvar().apply(this);
        op1 = (SaDec) this.returnValue;
        this.returnValue = new SaLDec(op1, null);
    }

    @Override
    public void caseADecvarldecvarListedecvarbis(ADecvarldecvarListedecvarbis node) {
        SaDec op1 = null;
        SaLDec op2 = null;
        node.getDecvar().apply(this);
        op1 = (SaDec) this.returnValue;
        node.getListedecvarbis().apply(this);
        op2 = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(op1, op2);
    }

    @Override
    public void caseADecvarListedecvarbis(ADecvarListedecvarbis node) {
        SaDec op1 = null;
        node.getDecvar().apply(this);
        op1 = (SaDec) this.returnValue;
        this.returnValue = new SaLDec(op1, null);
    }

    @Override
    public void caseADecvarentierDecvar(ADecvarentierDecvar node) {
        String nom = node.getIdentif().getText();
        this.returnValue = new SaDecVar(nom);
    }

    @Override
    public void caseADecvartableauDecvar(ADecvartableauDecvar node) {
        returnValue = new SaDecTab(node.getIdentif().getText(), Integer.parseInt(node.getNombre().getText()));
    }

    @Override
    public void caseALdecfoncrecListedecfonc(ALdecfoncrecListedecfonc node) {
        super.caseALdecfoncrecListedecfonc(node);
        SaDecFonc op1 = null;
        SaLDec op2 = null;
        node.getDecfonc().apply(this);
        op1 = (SaDecFonc) this.returnValue;
        node.getListedecfonc().apply(this);
        op2 = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(op1, op2);
    }

    @Override
    public void caseADecvarinstrDecfonc(ADecvarinstrDecfonc node) {
        super.caseADecvarinstrDecfonc(node);
        String op1 = node.getIdentif().getText();
        SaInst op4 = retrieveNode(node.getInstrbloc());
        SaLDec op3 =  retrieveNode(node.getListeparam());
        SaLDec op2 = retrieveNode(node.getOptdecvar());
        this.returnValue = new SaDecFonc(op1,op2,op3,op4);
    }


    @Override
    public void caseAInstrDecfonc(AInstrDecfonc node) {
        String op1 = node.getIdentif().getText();
        SaLDec op2 = retrieveNode(node.getListeparam());
        SaInst op3 = retrieveNode(node.getInstrbloc());
        this.returnValue = new SaDecFonc(op1,null, op2, op3);
    }


    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        node.getListedecvar().apply(this);
        SaLDec op1 = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(op1.getTete(), op1.getQueue());
    }

    @Override
    public void caseAInstraffect(AInstraffect node) {
        this.returnValue = new SaInstAffect(retrieveNode(node.getVar()), retrieveNode(node.getExp()));
    }


    @Override
    public void caseAInstrbloc(AInstrbloc node) {
        SaLInst op1 = null;
        node.getListeinst().apply(this);
        op1 = (SaLInst) this.returnValue;
        this.returnValue = new SaInstBloc(op1);
    }

    @Override
    public void caseALdecfoncfinalListedecfonc(ALdecfoncfinalListedecfonc node) {
        this.returnValue = null;
    }

    @Override
    public void caseALinstfinalListeinst(ALinstfinalListeinst node) {
        this.returnValue = null;
    }

    @Override
    public void caseALinstrecListeinst(ALinstrecListeinst node) {
        SaInst op1 = retrieveNode(node.getInstr());
        SaLInst op2 = retrieveNode(node.getListeinst());
        this.returnValue = new SaLInst(op1,op2);
    }

    @Override
    public void caseAAvecsinonInstrsi(AAvecsinonInstrsi node) {
        SaExp op1 = retrieveNode(node.getExp());
        SaInstBloc op2 = retrieveNode(node.getInstrbloc());
        SaInst op3 = retrieveNode(node.getInstrsinon());
        this.returnValue = new SaInstSi(op1,op2, op3);
    }

    @Override
    public void caseASanssinonInstrsi(ASanssinonInstrsi node) {
        SaExp op1 = retrieveNode(node.getExp());
        SaInstBloc op2 = retrieveNode(node.getInstrbloc());
        this.returnValue = new SaInstSi(op1,op2, null);
    }

    @Override
    public void caseAInstrtantque(AInstrtantque node) {
        SaExp op1 = null;
        SaInst op2 = null;
        node.getExp().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getInstrbloc().apply(this);
        op2 = (SaInst) this.returnValue;
        this.returnValue = new SaInstTantQue(op1, op2);
    }

    @Override
    public void caseAInstrretour(AInstrretour node) {
        SaExp op1 = null;
        node.getExp().apply(this);
        op1 = (SaExp) this.returnValue;
        this.returnValue = new SaInstRetour(op1);    }

    @Override
    public void caseAInstrecriture(AInstrecriture node) {
        SaExp op1 = null;
        node.getExp().apply(this);
        op1 = (SaExp) this.returnValue;
        this.returnValue = new SaInstEcriture(op1);
    }

    @Override
    public void caseAOuExp(AOuExp node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp1().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpOr(op1,op2);
    }

    @Override
    public void caseAEtExp1(AEtExp1 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp1().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp2().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpAnd(op1,op2);
    }


    @Override
    public void caseAInfExp2(AInfExp2 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp2().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp3().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpInf(op1,op2);
    }

    @Override
    public void caseAMoinsExp3(AMoinsExp3 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp3().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp4().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpSub(op1,op2);
    }

    @Override
    public void caseAFoisExp4(AFoisExp4 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp4().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp5().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpMult(op1,op2); ;
    }

    @Override
    public void caseADiviseExp4(ADiviseExp4 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp4().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp5().apply((this));
        op2 = (SaExp) this.returnValue;
        this.returnValue = new SaExpDiv(op1,op2);
    }

    @Override
    public void caseANonExp5(ANonExp5 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp5().apply(this);
        op1 = (SaExp) this.returnValue;
        this.returnValue = new SaExpNot(op1);
    }

    @Override
    public void caseANombreExp6(ANombreExp6 node) {
        this.returnValue = new SaExpInt(Integer.parseInt(node.getNombre().getText()));
    }

    @Override
    public void caseAVarExp6(AVarExp6 node) {
        SaVar op1 = null;
        node.getVar().apply(this);
        op1 = (SaVar) this.returnValue;
        this.returnValue = new SaExpVar(op1);
    }

    @Override
    public void caseALireExp6(ALireExp6 node) {
        this.returnValue = new SaExpLire();
    }

    @Override
    public void caseAVartabVar(AVartabVar node) {
        SaExp op1 = retrieveNode(node.getExp());
        this.returnValue = new SaVarIndicee(node.getIdentif().getText(), op1);
    }

    @Override
    public void caseAVarsimpleVar(AVarsimpleVar node) {
        this.returnValue = new SaVarSimple(node.getIdentif().getText());
    }

    @Override
    public void caseARecursifListeexp(ARecursifListeexp node) {
        SaExp op1 = retrieveNode(node.getExp());
        SaLExp op2 = retrieveNode(node.getListeexpbis());
        this.returnValue = new SaLExp(op1,op2);
    }

    @Override
    public void caseAFinalListeexp(AFinalListeexp node) {
        SaExp op1 = retrieveNode(node.getExp());
        this.returnValue = new SaLExp(op1, null);
;    }

    @Override
    public void caseAFinalListeexpbis(AFinalListeexpbis node) {
        SaExp op1 = retrieveNode(node.getExp());
        this.returnValue = new SaLExp(op1, null);
    }

    @Override
    public void caseARecursifListeexpbis(ARecursifListeexpbis node) {
        SaExp op1 = retrieveNode(node.getExp());
        SaLExp op2 = retrieveNode(node.getListeexpbis());
        this.returnValue = new SaLExp(op1,op2);
    }

    @Override
    public void caseAAvecparamAppelfct(AAvecparamAppelfct node) {
        String nom = node.getIdentif().getText();
        SaLExp op1 = retrieveNode(node.getListeexp());
        this.returnValue = new SaAppel(nom, op1);
    }

    @Override
    public void caseAAppelfctExp6(AAppelfctExp6 node) {
        SaAppel op1 = retrieveNode(node.getAppelfct());
        this.returnValue = new SaExpAppel(op1);
    }

    @Override
    public void caseASansparamAppelfct(ASansparamAppelfct node) {
        String nom = node.getIdentif().getText();
        this.returnValue = new SaAppel(nom, null);
    }

    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        this.returnValue = null;
    }
}
