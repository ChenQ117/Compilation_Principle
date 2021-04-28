package 实验二;

import java.util.*;

/**
 * @version v1.0
 * @ClassName: Formula
 * @Description: 每一个产生式类
 * @Author: ChenQ
 * @Date: 2021/4/18 on 17:26
 */
public class Formula {

    //产生式右部的单个字符
    NonTerminalNode leftNode;//产生式的左边
    String right;
    Node[] rightNodes;
    Set<String> select = new HashSet<>();
    Formula(NonTerminalNode leftNode, String s, Map<String,Node> map){
        this.leftNode = leftNode;
        this.right = s;
        String[] split = s.split("\\s+");
        rightNodes = new Node[split.length];
        for (int i=0;i<rightNodes.length;i++){
            for (String key:map.keySet()){
                if (split[i].equals(key)){
                    rightNodes[i] = map.get(key);
                }
            }
            if (rightNodes[i] == null){
                rightNodes[i] = new TerminalNode(split[i],map);
            }
        }
    }

    public Set<String> getSelect(Map<Node,Formula[]> formulaMap) {
        if (!select.isEmpty()){
            return select;
        }
        select.addAll(rightNodes[0].getFirst());
        if (rightNodes[0].produceNull){
            select.addAll(rightNodes[0].getFollow(formulaMap));
        }
        if (rightNodes[0].left.equals("ε")){
            select.addAll(leftNode.getFollow(formulaMap));
        }
        select.remove("ε");
        return select;
    }

    public void setSelect(Set<String> select) {
        this.select = select;
    }

    //判断是否为终结符
    public boolean isTerminalChar(char  ch){
        if (ch>='a'&&ch<='z'){
            return true;
        }
        return false;
    }
    //判断是否为‘ε’
    public boolean isNullChar(char ch){
        if (ch == 'ε'){
            return true;
        }
        return false;
    }
    //判断是否为first元素可能集合
    public boolean isTerminalCharOrNull(char ch){
        return (isTerminalChar(ch)|isNullChar(ch)|ElementCollection.isFirstElement(ch));
    }
}
