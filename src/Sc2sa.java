import sa.SaExp;
import sa.SaExpAdd;
import sa.SaExpEqual;
import sa.SaNode;
import sc.analysis.DepthFirstAdapter;
import sc.node.AAppelfctExp6;
import sc.node.AEgalExp2;
import sc.node.APlusExp3;

public class Sc2sa extends DepthFirstAdapter {

    private SaNode returnValue;

    public SaNode getRoot() {
        return returnValue;
    }

    @Override
    public void caseAAppelfctExp6(AAppelfctExp6 node) {

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


}
