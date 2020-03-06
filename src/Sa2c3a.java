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
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaLInst node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaDecVar node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        C3aTemp temp = c3a.newTemp();
        SaVar left = node.getLhs();
        SaExp right = node.getRhs();
        c3a.ajouteInst(new C3aInstAffect(left.accept(this), right.accept(this), ""));
        return temp;
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
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpEqual node) {
        return super.visit(node);
    }

    @Override
    public C3aOperand visit(SaExpAnd node) {
        return super.visit(node);
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
        return super.visit(node);
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
