import c3a.*;
import sa.*;
import ts.Ts;
import ts.TsItemFct;

public class  Sa2c3a extends SaDepthFirstVisitor <C3aOperand>{

    private C3a c3a;
    private Ts tableGlobale;
    private Ts tableLocale;

    public Sa2c3a(SaNode root, Ts tableGlobale){
        c3a = new C3a();
        this.tableGlobale = tableGlobale;
        root.accept(this);
    }

    @Override
    public C3aOperand visit(SaExp node) {
        return node.accept(this);
    }

    @Override
    public C3aOperand visit(SaExpInt node) {
        return new C3aConstant(node.getVal());
    }

    @Override
    public C3aOperand visit(SaExpVar node) {
        return node.getVar().accept(this);
    }

    @Override
    public C3aOperand visit(SaInstEcriture node) {
        SaExp op1 = node.getArg();
        c3a.ajouteInst(new C3aInstWrite(op1.accept(this), ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        C3aLabel suite = c3a.newAutoLabel();
        C3aLabel test = c3a.newAutoLabel();
        c3a.addLabelToNextInst(test);
        C3aOperand tq = node.getTest().accept(this);
        c3a.ajouteInst(new C3aInstJumpIfEqual(tq, c3a.False, suite, ""));
        node.getFaire().accept(this);
        c3a.ajouteInst(new C3aInstJump(test, ""));
        c3a.addLabelToNextInst(suite);
        return null;
    }

    @Override
    public C3aOperand visit(SaLInst node) {
        node.getTete().accept(this);
        if(node.getQueue() != null) node.getQueue().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        tableLocale = tableGlobale.getTableLocale(node.getNom());
        c3a.ajouteInst(new C3aInstFBegin(tableGlobale.getFct(node.getNom()), "Declaration de fonction"));
        node.getCorps().accept(this);
        c3a.ajouteInst((new C3aInstFEnd("")));
        tableLocale = null;
        return null;
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        SaVar left = node.getLhs();
        SaExp right = node.getRhs();
        c3a.ajouteInst(new C3aInstAffect(left.accept(this), right.accept(this), ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaLDec node) {
        node.getTete().accept(this);
        if(node.getQueue() != null) node.getQueue().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaVarSimple node) {
        node.tsItem = tableGlobale.getVar(node.getNom());
        return new C3aVar(node.tsItem, c3a.False);
    }

    @Override
    public C3aOperand visit(SaAppel node) {
        C3aTemp temp = c3a.newTemp();
        node.getArguments().accept(this);
        C3aFunction fonction = new C3aFunction(tableGlobale.getFct(node.getNom()));
        c3a.ajouteInst(new C3aInstCall(fonction,null,"appel de fonction"));
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpAppel node) {
        C3aTemp temp = c3a.newTemp();
        C3aTemp temp2 = c3a.newTemp();
        node.getVal().getArguments().accept(this);
        C3aFunction fonction = new C3aFunction(tableGlobale.getFct(node.getVal().getNom()));
        c3a.ajouteInst(new C3aInstCall(fonction,temp,"appel de fonction"));
        c3a.ajouteInst(new C3aInstAffect(temp, temp2,"affectation de l'appel"));
        return temp2;
    }

    @Override
    public C3aOperand visit(SaExpAdd node) {
        C3aTemp temp = c3a.newTemp();
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        c3a.ajouteInst(new C3aInstAdd(op1.accept(this), op2.accept(this),temp,""));
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpSub node) {
        C3aTemp temp = c3a.newTemp();
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        c3a.ajouteInst(new C3aInstSub(op1.accept(this), op2.accept(this),temp,""));
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpMult node) {
        C3aTemp temp = c3a.newTemp();
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        c3a.ajouteInst(new C3aInstMult(op1.accept(this), op2.accept(this),temp,""));
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpDiv node) {
        C3aTemp temp = c3a.newTemp();
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        c3a.ajouteInst(new C3aInstDiv(op1.accept(this), op2.accept(this),temp,""));
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpInf node) {
        C3aTemp temp = c3a.newTemp();
        C3aLabel e1 = c3a.newAutoLabel();
        C3aLabel e2 = c3a.newAutoLabel();
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.True,""));
        c3a.ajouteInst(new C3aInstJumpIfLess(node.getOp1().accept(this),node.getOp2().accept(this),e2,""));
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.False,""));
        c3a.addLabelToNextInst(e2);
        c3a.ajouteInst(new C3aInstJumpIfEqual(temp, c3a.False, e1, ""));
        c3a.addLabelToNextInst(e1);
        return temp;
     }

    @Override
    public C3aOperand visit(SaExpEqual node) {
        C3aTemp temp = c3a.newTemp();
        C3aLabel e1 = c3a.newAutoLabel();
        C3aLabel e2 = c3a.newAutoLabel();
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.True,""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(node.getOp1().accept(this),node.getOp2().accept(this),e2,""));
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.False,""));
        c3a.addLabelToNextInst(e2);
        c3a.ajouteInst(new C3aInstJumpIfEqual(temp, c3a.False, e1, ""));
        c3a.addLabelToNextInst(e1);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpAnd node) {
        C3aLabel faux = c3a.newAutoLabel();
        C3aLabel vrai = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstJumpIfEqual(node.getOp1().accept(this), c3a.False, vrai, ""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(node.getOp2().accept(this), c3a.False, vrai, ""));
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp, ""));
        c3a.ajouteInst(new C3aInstJump(faux, ""));
        c3a.addLabelToNextInst(vrai);
        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));
        c3a.addLabelToNextInst(faux);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpOr node) {
        //c'est la même manière que And sauf qu'on utilise false comme point de départ et pas true
        C3aLabel faux = c3a.newAutoLabel();
        C3aLabel vrai = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstJumpIfNotEqual(node.getOp1().accept(this), c3a.False, vrai, ""));
        c3a.ajouteInst(new C3aInstJumpIfNotEqual(node.getOp2().accept(this), c3a.False, vrai, ""));
        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));
        c3a.ajouteInst(new C3aInstJump(faux, ""));
        c3a.addLabelToNextInst(vrai);
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp, ""));
        c3a.addLabelToNextInst(faux);

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpNot node) {
        //TODO c'est la merde
        C3aTemp temp = c3a.newTemp();
        C3aLabel e1 = c3a.newAutoLabel();
        C3aLabel e2 = c3a.newAutoLabel();
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.True,""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(node.getOp1().accept(this),node.getOp1().accept(this),e2,""));
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.False,""));
        c3a.addLabelToNextInst(e2);
        c3a.ajouteInst(new C3aInstJumpIfEqual(temp, c3a.False, e1, ""));
        c3a.addLabelToNextInst(e1);
        return temp; //new C3aConstant(!(node.getOp1().accept(this)));;
    }

    @Override
    public C3aOperand visit(SaExpLire node) {
        C3aTemp tmp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstRead(tmp, ""));
        return tmp;
    }

    @Override
    public C3aOperand visit(SaInstSi node) {
        C3aLabel sinon = c3a.newAutoLabel();
        C3aLabel fin = c3a.newAutoLabel();

        SaInst instSinon = node.getSinon();
        if (instSinon == null) {
            c3a.ajouteInst(new C3aInstJumpIfEqual(node.getTest().accept(this), c3a.False, fin, "si sans else"));
            node.getAlors().accept(this);
        } else {
            c3a.ajouteInst(new C3aInstJumpIfEqual(node.getTest().accept(this), c3a.False, sinon, "si avec else"));
            node.getAlors().accept(this);
            c3a.ajouteInst(new C3aInstJump(fin, ""));
            c3a.addLabelToNextInst(sinon);
            instSinon.accept(this);
        }

        c3a.addLabelToNextInst(fin);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        c3a.ajouteInst(new C3aInstReturn(node.getVal().accept(this),"return"));
        return null;
    }

    @Override
    public C3aOperand visit(SaLExp node) {
        if(node.getQueue() != null) node.getQueue().accept(this);
        c3a.ajouteInst(new C3aInstParam(node.getTete().accept(this),"Paramètres"));
        return null;
    }

    @Override
    public C3aOperand visit(SaVarIndicee node) {
        return super.visit(node);
    }

    public C3a getC3a() {
        return c3a;
    }
}
