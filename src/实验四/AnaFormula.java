package 实验四;

/**
 * @version v1.0
 * @ClassName: AnaFormula
 * @Description: 四元式
 * @Author: ChenQ
 * @Date: 2021/5/11 on 20:38
 */
public class AnaFormula {
    int op;
    String operate;
    String arg1;
    String arg2;
    String result;

    public AnaFormula(int op, String operate, String arg1, String arg2, String result) {
        this.op = op;
        this.operate = operate;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return op+" ( " + operate +" ,"+ arg1 +" ,"+ arg2 +" ,"+ result + " )";
    }
}
