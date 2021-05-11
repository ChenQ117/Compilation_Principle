package 实验二;

import java.util.*;

/**
 * @version v1.0
 * @ClassName: NonTerminalNode
 * @Description: 非终端节点
 * @Author: ChenQ
 * @Date: 2021/4/18 on 22:53
 */
public class NonTerminalNode extends Node {
    public Formula[] formulas;//该非终端节点的产生式
    public NonTerminalNode(String left, String right, Map<String,Node> nodeMap,Map<String,Boolean> produceMap){
        this.left = left;
        this.right = right;
        nodeMap.put(left,this);
        for (String key:produceMap.keySet()){
            if (key.equals(left)){
                produceNull = produceMap.get(key);
            }
        }
        if (left.equals("E")){
            follow.add("#");
        }
    }

    public Formula[] getFormulas() {
        return formulas;
    }

    public void setFormulas(Map<String,Node> map){
        String[] split = right.split("\\|");
        formulas = new Formula[split.length];
        for (int i=0;i<formulas.length;i++){
            formulas[i] = new Formula(this,split[i],map);
        }
    }
    @Override
    public Set<String> getFirst() {
        //如果first集合已经求过，则不需要再递归求解了，可直接返回
        if (!first.isEmpty()){
            return first;
        }
        Set<String>set = new HashSet<>();
        boolean flag = false;//记录第一个字符是否为空
        for (int i=0;i<formulas.length;i++){
            //判断第一个字符是否为终结符，是的话则加入first集合，并进行下一个推导式的判断
            if (formulas[i].rightNodes[0] instanceof TerminalNode){
                set.addAll(formulas[i].rightNodes[0].getFirst());
                if (formulas[i].rightNodes[0].equals("ε")){
                    flag = true;
                }
            }else {
                //如果第一个字符为非终结符，则加入
                set.addAll(formulas[i].rightNodes[0].getFirst());
                set.remove("ε");
                //如果第一个字符能推出空的话，则继续判断之后的字符能否推出空
                if (formulas[i].rightNodes[0].produceNull){
                    int j = 1;
                    for (; j<formulas[i].rightNodes.length; j++){
                        //当遇到终结符时，表明第i个产生式的first集已经全部考虑
                        if (formulas[i].rightNodes[j] instanceof TerminalNode){
//                        set.addAll(formulas[i].rightNodes[j].getFirst());
                            break;
                        }
                        //当当前的结点能推出空时，则first集应该包含当前结点，但不包含空
                        if (formulas[i].rightNodes[j].produceNull){
                            set.addAll(formulas[i].rightNodes[j].getFirst());
                            set.remove("ε");
                        }
                    }
                    //当所有加入first集的结点都能推出空时，需要把空加上
                    if (formulas[i].rightNodes[0].produceNull&&(j==formulas[i].rightNodes.length)){
                        set.add("ε");
                    }
                }
            }
        }
        if (flag){
            set.add("ε");
        }
        first = set;
        return set;
    }

    @Override
    public String toString() {
        return "NonTerminalNode{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                ", formulas=" + Arrays.toString(formulas) +
                ", produceNull=" + produceNull +
                '}';
    }

    public void setFollow(){
        if (left.equals("E")){
            follow.add("#");
        }
    }
    @Override
    public Set<String> getFollow(Map<Node,Formula[]> formulaMap) {
        if (!left.equals("E")&&!follow.isEmpty()){
            //如果之前求过follow集则不需要再求了，可以直接返回，E非终结符除外，因为E在初始化时follow加入了”#“
            return follow;
        }
        for (Node node:formulaMap.keySet()){
            Formula[] formulas = formulaMap.get(node);
            for (int i=0;i<formulas.length;i++){
                for (int j=0;j<formulas[i].rightNodes.length;j++){
                    //如果该推导式包含当前类结点
                    if (formulas[i].rightNodes[j].equals(this)){
                        //如果当前类为推导式的最后一个，则该follow集为推导式左部的follow集
                        if (j == formulas[i].rightNodes.length-1){
                            if (!node.equals(this)){
                                //该判断必须存在，否则会进入死递归，例如E1→+ T E1
                                follow.addAll(node.getFollow(formulaMap));
                            }
                        }else {
                            //如果当前类结点所在推导式的后面还有结点
                            if (formulas[i].rightNodes[j+1] instanceof TerminalNode){
                                //如果当前类结点的右边第一个为终结符，则follow集为该终结符
                                follow.addAll(formulas[i].rightNodes[j+1].getFollow(formulaMap));
                            }else {
                                follow.addAll(formulas[i].rightNodes[j+1].getFirst());
                                //如果该first包含空，则移除
                                if (formulas[i].rightNodes[j+1].getFirst().contains("ε")){
                                    follow.remove("ε");
                                }
                                //判断其后的结点是否能推出空，如果能则加入其后结点的first集并继续向后判断，直到不能推出空为止
                                int k = j+1;
                                for (;k<formulas[i].rightNodes.length-1;k++){
                                    if (formulas[i].rightNodes[k].produceNull){
                                        follow.addAll(formulas[i].rightNodes[k+1].getFirst());
                                    }else {
                                        break;
                                    }
                                }
                                //如果其后的所有结点都能推出空的话，则加入产生式左部的follow集
                                if (k == formulas[i].rightNodes.length-1&&formulas[i].rightNodes[k].produceNull){
                                    if (!node.equals(this)){
                                        //该判断必须存在，否则会进入死递归，例如E1→+ T E1
                                        follow.addAll(node.getFollow(formulaMap));
                                    }
                                }
                                //如果右边的第一个能推出空，则加上其follow集
                                /*if (formulas[i].rightNodes[j+1].produceNull){
                                    follow.addAll(formulas[i].rightNodes[j+1].getFollow(formulaMap));
                                }*/
                            }
                        }
                    }
                }
            }
        }
        //follow集里不含空
        follow.remove("ε");
        return follow;
    }

    @Override
    public Set<String> getSelect(Map<Node,Formula[]> formulaMap) {
        /*if (!select.isEmpty()){
            return select;
        }*/
        for (Node node:formulaMap.keySet()){
            Formula[] formulas = formulaMap.get(node);
            for (int i=0;i<formulas.length;i++){
                if (formulas[i].leftNode.equals(this)){
                    select.addAll(formulas[i].getSelect(formulaMap));
                }
            }
        }
        return select;
    }
}
