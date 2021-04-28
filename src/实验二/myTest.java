package 实验二;

import org.junit.Test;

import java.util.*;

/**
 * @version v1.0
 * @ClassName: myTest
 * @Description: 测试类
 * @Author: ChenQ
 * @Date: 2021/4/18 on 16:20
 */
public class myTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Utils utils = Utils.getInstance();
        String path = Utils.directory+Utils.file_1;
        String[] strings = utils.readFile(path);
        NonTerminalNode[] nonTerminalNodes = new NonTerminalNode[strings.length/2];
        Map<String,Node> nodeMap = new HashMap<>();//map的作用是用来存储所实例化的所有节点对象，保证当前只有这些结点对象

        //初始化各结点是否能推出空
        Map<String,Boolean> produceMap = new HashMap<>();//用来初始化各非终端结点是否可以推导出‘ε’
        produceMap.put("E",false);
        produceMap.put("E1",true);
        produceMap.put("T",false);
        produceMap.put("T1",true);
        produceMap.put("F",false);

        //生成所有非终结符
        int k=0;
        for (int i=0;i<strings.length;i+=2){
            nonTerminalNodes[k++] = new NonTerminalNode(strings[i],strings[i+1],nodeMap,produceMap);
        }

        //获得所有表达式
        Map<Node,Formula[]> formulaMap = new HashMap<>();//存储所有表达式
        for (int i = 0;i<nonTerminalNodes.length;i++){
            nonTerminalNodes[i].setFormulas(nodeMap);
            formulaMap.put(nonTerminalNodes[i],nonTerminalNodes[i].getFormulas());
        }

        //求first集
        System.out.println("============First=============");
        for (int i = 0;i<nonTerminalNodes.length;i++){
            Set<String> first = nonTerminalNodes[i].getFirst();
            System.out.println("First("+nonTerminalNodes[i].left+")="+first);
        }
        //求follow集
        System.out.println("============Follow=============");
        for (int i = 0;i<nonTerminalNodes.length;i++){
            Set<String> follow = nonTerminalNodes[i].getFollow(formulaMap);
            System.out.println("Follow("+nonTerminalNodes[i].left+")="+follow);
        }

        //初始化每个产生式的select集，此步必须在完成求first集和follow集之后
        System.out.println("============Select=============");
        for (Node node : formulaMap.keySet()){
            Formula[] formulas = formulaMap.get(node);
            for (int i=0;i<formulas.length;i++){
                Set<String> formulasSelect = formulas[i].getSelect(formulaMap);
                System.out.println("Select("+formulas[i].leftNode.left+"→"+formulas[i].right+")="+formulasSelect);
            }
        }

        System.out.println("请输入需要分析的句子");
        String ss = sc.next();//i+i*i#  (i+i)*i#
        utils.analysis(ss,nodeMap,formulaMap);

    }
}
