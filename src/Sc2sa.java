import sa.*;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {

    private SaNode returnValue;

    public SaNode getRoot() {
        return returnValue;
    }

    @Override
    public void caseAAppelfctExp6(AAppelfctExp6 node) {
        //TODO supp ?
        super.caseAAppelfctExp6(node);
    }

    @Override
    public void caseAEgalExp2(AEgalExp2 node) {
        SaExp op1 = null;
        SaExp op2 = null;
        node.getExp2().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp3().apply((this));
        op2 = (SaExp) this.returnValue;
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
        SaLDec op1 = null;
        SaLDec op2 = null;
        node.getOptdecvar().apply(this);
        op1 = (SaLDec) this.returnValue;
        node.getListedecfonc().apply(this);
        op2 = (SaLDec) this.returnValue;
        this.returnValue = new SaProg(op1, op2);
    }

    @Override
    public void caseALdecfoncProgramme(ALdecfoncProgramme node) {
        SaLDec op1 = null;
        node.getListedecfonc().apply(this);
        op1 = (SaLDec) this.returnValue;
        this.returnValue = new SaProg(op1);
        //TODO a refaire jure ???
    }

    @Override
    public void caseAOptdecvar(AOptdecvar node) {
        SaLDec op1 = null;
        SaInst op2 = null;
        node.getListedecvar().apply(this);
        op1 = (SaLDec) this.returnValue;
        this.returnValue = new Sa;
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
        SaDecVar op1 = null;
        node.getDecvar().apply(this);
        op1 = (SaDecVar) this.returnValue;
        this.returnValue = new SaLDec(op1, null);
    }

    @Override
    public void caseADecvarldecvarListedecvarbis(ADecvarldecvarListedecvarbis node) {
        SaDecVar op1 = null;
        SaLDec op2 = null;
        node.getDecvar().apply(this);
        op1 = (SaDecVar) this.returnValue;
        node.getListedecvarbis().apply(this);
        op2 = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(op1, op2);
    }

    @Override
    public void caseADecvarListedecvarbis(ADecvarListedecvarbis node) {
        SaDecVar op1 = null;
        node.getDecvar().apply(this);
        op1 = (SaDecVar) this.returnValue;
        this.returnValue = new SaLDec(op1, null);
    }

    @Override
    public void caseADecvarentierDecvar(ADecvarentierDecvar node) {
        super.caseADecvarentierDecvar(node);
    }

    @Override
    public void caseADecvartableauDecvar(ADecvartableauDecvar node) {
        super.caseADecvartableauDecvar(node);
    }

    @Override
    public void caseALdecfoncrecListedecfonc(ALdecfoncrecListedecfonc node) {
        super.caseALdecfoncrecListedecfonc(node);
    }

    @Override
    public void caseALdecfoncfinalListedecfonc(ALdecfoncfinalListedecfonc node) {
        super.caseALdecfoncfinalListedecfonc(node);
    }

    @Override
    public void caseADecvarinstrDecfonc(ADecvarinstrDecfonc node) {
        super.caseADecvarinstrDecfonc(node);
    }

    @Override
    public void caseAInstrDecfonc(AInstrDecfonc node) {
        super.caseAInstrDecfonc(node);
    }

    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        super.caseASansparamListeparam(node);
    }

    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        super.caseAAvecparamListeparam(node);
    }

    @Override
    public void caseAInstraffectInstr(AInstraffectInstr node) {
        super.caseAInstraffectInstr(node);
    }

    @Override
    public void caseAInstrblocInstr(AInstrblocInstr node) {
        super.caseAInstrblocInstr(node);
    }

    @Override
    public void caseAInstrsiInstr(AInstrsiInstr node) {
        SaExp op1 = null;
        SaInst op2 = null;
        SaInst op3 = null;
        node.getInstrsi().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getInstrbloc().apply(this);
        op2 = (SaInst) this.returnValue;
        this.returnValue = new SaInstTantQue(op1, op2);
    }

    @Override
    public void caseAInstrtantqueInstr(AInstrtantqueInstr node) {
        super.caseAInstrtantqueInstr(node);
    }

    @Override
    public void caseAInstrappelInstr(AInstrappelInstr node) {
        super.caseAInstrappelInstr(node);
    }

    @Override
    public void caseAInstrretourInstr(AInstrretourInstr node) {
        super.caseAInstrretourInstr(node);
    }

    @Override
    public void caseAInstrecritureInstr(AInstrecritureInstr node) {
        super.caseAInstrecritureInstr(node);
    }

    @Override
    public void caseAInstrvideInstr(AInstrvideInstr node) {
        super.caseAInstrvideInstr(node);
    }

    @Override
    public void caseAInstraffect(AInstraffect node) {
        super.caseAInstraffect(node);
    }

    @Override
    public void caseAInstrbloc(AInstrbloc node) {
        SaLInst op1 = null;
        node.getListeinst().apply(this);
        op1 = (SaLInst) this.returnValue;
        this.returnValue = new SaInstBloc(op1);
    }

    @Override
    public void caseALinstrecListeinst(ALinstrecListeinst node) {
        super.caseALinstrecListeinst(node);
    }

    @Override
    public void caseALinstfinalListeinst(ALinstfinalListeinst node) {
        super.caseALinstfinalListeinst(node);
    }

    @Override
    public void caseAAvecsinonInstrsi(AAvecsinonInstrsi node) {
        super.caseAAvecsinonInstrsi(node);
    }

    @Override
    public void caseASanssinonInstrsi(ASanssinonInstrsi node) {
        super.caseASanssinonInstrsi(node);
    }

    @Override
    public void caseAInstrsinon(AInstrsinon node) {
        super.caseAInstrsinon(node);
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
    public void caseAInstrappel(AInstrappel node) {
        super.caseAInstrappel(node);
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
    public void caseAInstrvide(AInstrvide node) {
        super.caseAInstrvide(node);
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
        super.caseANombreExp6(node);
    }

    @Override
    public void caseAVarExp6(AVarExp6 node) {
        SaVar op1 = null;
        node.getVar().apply(this);
        op1 = (SaVar) this.returnValue;
        this.returnValue = new SaExpVar(op1);
    }

    @Override
    public void caseAParenthesesExp6(AParenthesesExp6 node) {
        super.caseAParenthesesExp6(node);
    }

    @Override
    public void caseALireExp6(ALireExp6 node) {
        super.caseALireExp6(node);
    }

    @Override
    public void caseAVartabVar(AVartabVar node) {
        super.caseAVartabVar(node);
    }

    @Override
    public void caseAVarsimpleVar(AVarsimpleVar node) {
        super.caseAVarsimpleVar(node);
    }

    @Override
    public void caseARecursifListeexp(ARecursifListeexp node) {
        super.caseARecursifListeexp(node);
    }

    @Override
    public void caseAFinalListeexp(AFinalListeexp node) {
        super.caseAFinalListeexp(node);
    }

    @Override
    public void caseAFinalListeexpbis(AFinalListeexpbis node) {
        super.caseAFinalListeexpbis(node);
    }

    @Override
    public void caseARecursifListeexpbis(ARecursifListeexpbis node) {
        super.caseARecursifListeexpbis(node);
    }

    @Override
    public void caseAAvecparamAppelfct(AAvecparamAppelfct node) {
        super.caseAAvecparamAppelfct(node);
    }

    @Override
    public void caseASansparamAppelfct(ASansparamAppelfct node) {
        super.caseASansparamAppelfct(node);
    }
}
