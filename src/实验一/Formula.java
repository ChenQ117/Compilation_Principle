package 实验一;

import 实验二.ElementCollection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @version v1.0
 * @ClassName: Formula
 * @Description: 每一个产生式类
 * @Author: ChenQ
 * @Date: 2021/4/18 on 17:26
 */
public class Formula {
    public String left;//产生式的左部
    public String right;//产生式的右部
    public RightNode[] rightNodes;//产生式右部，当一个非终结符有多个右部时，将其拆成单个
    public boolean produceNull;//能否推导出‘ε’

    public Set<Character> first = new HashSet<>();
    public Set<Character> follow = new HashSet<>();
    public Set<Character> select = new HashSet<>();

    public Formula(String left, String right){
        this.left = left;
        this.right = right;
        String[] strings = right.split("\\|");
        rightNodes = new RightNode[strings.length];
        for (int i=0;i<rightNodes.length;i++){
            rightNodes[i] = new RightNode(strings[i]);
        }
    }

    public Set<Character> getFirst(Formula[] formulas){
        for (int i=0;i<formulas.length;i++){
            Formula formula = formulas[i];
            if (formula.left.equals(this.left)){
                for (int j=0;j<formula.rightNodes.length;j++){
                    //判断右部的第一个字符是否为终结符或者‘ε’，是的话则加入first集合
                    if (formula.rightNodes[j].rightArray[0].length()==1){
                        char ch = formula.rightNodes[j].rightArray[0].charAt(0);
                        if (isTerminalCharOrNull(ch)){
                            first.add(ch);
                        }
                    }

                }
            }
        }
        return first;
    }

    public Set<Character> getFirst(Formula formula){
        /*if (S.length()==1&&isTerminalCharOrNull(S.charAt(0))){
            Set<Character> set = new HashSet<>();
            set.add(S.charAt(0));
            return set;
        }*/
        for (int i=0;i<formula.rightNodes.length;i++){
            for(int j = 0;j<formula.rightNodes[i].rightArray.length;j++){
//                if (formula.rightNodes[i].rightArray[j])

            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "Formula{" +
                "left='" + left + '\'' +
                //", right='" + right + '\'' +
                ", rightNodes=" + Arrays.toString(rightNodes) +
                //", first=" + Arrays.toString(first) +
                //", follow=" + Arrays.toString(follow) +
                //", select=" + Arrays.toString(select) +
                '}';
    }

    //产生式右部的单个字符
    class RightNode{
        String[] rightArray;
        RightNode(String s){
            rightArray = s.split("\\s+");
        }

        @Override
        public String toString() {
            return "RightNode{" +
                    "rightArray=" + Arrays.toString(rightArray) +
                    '}';
        }
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
