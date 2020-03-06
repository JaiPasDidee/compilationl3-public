import c3a.*;
import sa.*;
import sc.node.Node;
import ts.Ts;

public class Sa2c3a extends SaDepthFirstVisitor <C3aOperand>{

    private C3a c3a;
    private Ts table;

    public Sa2c3a(SaNode root, Ts table){
        c3a = new C3a();
        this.table = table;
        root.accept(this);
    }

    @Override
    public C3aOperand visit(SaProg node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaDecTab node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExp node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpInt node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpVar node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaInstEcriture node) {
        SaExp op1 = node.getArg();
        c3a.ajouteInst(new C3aInstWrite(op1.accept(this), ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        //TODO on a pas fini il faut des temporaires sans doute et aussi c'est la merde
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
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        c3a.ajouteInst(new C3aInstFBegin(node.tsItem, ""));
        node.getCorps().accept(this);
        c3a.ajouteInst((new C3aInstFEnd("")));
        return null;
    }

    @Override
    public C3aOperand visit(SaDecVar node) {
        return super.visit(node);
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
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaVarSimple node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaAppel node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpAppel node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpAdd node) {
        C3aTemp temp = c3a.newTemp();
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        c3a.ajouteInst(new C3aInstAdd(op1.accept(this), op2.accept(this),temp,""));
        return temp;    }

    @Override
    public C3aOperand visit(SaExpSub node) {
        C3aTemp temp = c3a.newTemp();
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        c3a.ajouteInst(new C3aInstSub(op1.accept(this), op2.accept(this),temp,""));
        return temp;    }

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
        C3aTemp temp = c3a.newTemp();
        C3aLabel e1 = c3a.newAutoLabel();
        C3aLabel e2 = c3a.newAutoLabel();
        c3a.ajouteInst(new C3aInstJumpIfEqual(node.getOp1().accept(this),c3a.False,e1,""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(node.getOp2().accept(this),c3a.False,e1,""));
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.True,""));
        c3a.ajouteInst(new C3aInstJump(e2,""));
        c3a.addLabelToNextInst(e1);
        c3a.ajouteInst(new C3aInstAffect(temp,c3a.False,""));
        c3a.addLabelToNextInst(e2);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpOr node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpNot node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpLire node) {
        c3a.ajouteInst(new C3aInstRead(node.accept(this), ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaInstBloc node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaInstSi node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaLExp node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaVarIndicee node) {
        return super.visit(node);
    }

    public C3a getC3a() {
        return c3a;
    }
}
