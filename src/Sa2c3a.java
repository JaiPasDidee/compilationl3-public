import c3a.C3a;
import c3a.C3aOperand;
import sa.SaDepthFirstVisitor;
import sa.SaNode;

public class Sa2c3a extends SaDepthFirstVisitor <C3aOperand>{

    private C3a c3a;
    public Sa2c3a(SaNode root){
        c3a = new C3a();
        root.accept(this);
    }

}
