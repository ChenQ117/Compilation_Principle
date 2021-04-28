package 实验二;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @version v1.0
 * @ClassName: Utils
 * @Description: 读取文件
 * @Author: ChenQ
 * @Date: 2021/4/18 on 16:09
 */
public class Utils {
    public static final String directory = "Txt/实验二文件";
    public static final String file_1 = "/grammer.txt";
    private Utils(){};
    private static Utils instance;
    public static Utils getInstance(){
        if (instance==null){
            instance = new Utils();
        }
        return instance;
    }
    public String[] readFile(String path){
        String[] strings = null;
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader bfr = null;
            bfr = new BufferedReader(new FileReader(path));
            String s = "";
            while ((s = bfr.readLine()) != null) {
                sb.append(s+"→");
            }
            s = "" + sb;
            strings = s.split("→");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    //根据串首元素和栈顶元素找出其对应的表达式
    public Node[] getAnalysisChart(char ch,Node topNode,Map<String,Node> nodeMap,Map<Node,Formula[]> formulaMap){
        Node[] formulaNode = null;
        for (String left:nodeMap.keySet()){
            //如果遍历的结点为当前栈顶元素结点
            if (left.equals(topNode.left)){
                Node node = nodeMap.get(left);
                if (node instanceof NonTerminalNode){
                    NonTerminalNode nonTerminalNode = (NonTerminalNode) node;
                    //获取当前栈顶元素结点的相关产生式
                    Formula[] formulas = nonTerminalNode.getFormulas();
                    for (int i=0;i<formulas.length;i++){
                        String chs = ch+"";
                        //如果该产生式的select集包含串首元素，则对应的表达式就是该产生式
                        if (formulas[i].getSelect(formulaMap).contains(chs)){
                            formulaNode = formulas[i].rightNodes;
                        }
                    }
                }
            }
        }
        return formulaNode;
    }
    //输出分析过程
    public void printProcess(Stack<Node> stack,char[] chars,int i){
        System.out.print("分析栈中元素：");
        for (Node node1 : stack){
            System.out.print(node1.left+" ");
        }
        System.out.print("    剩余输入串：");
        for (int k = i;k<chars.length;k++){
            System.out.print(chars[k]);
        }
    }
    public void analysis(String s, Map<String,Node> nodeMap,Map<Node,Formula[]> formulaMap){
        char[] chars = s.toCharArray();
        Stack<Node> stack = new Stack();
        Node node = new TerminalNode("#");
        stack.push(node);

        //将起始符加入栈
        for (String left:nodeMap.keySet()){
            if (left.equals("E")){
                stack.push(nodeMap.get(left));
            }
        }
        int i = 0;
        while (i<chars.length){
            //判断栈顶元素是否等于串首元素，是的话则弹出栈顶元素，串首元素向后移一个
            if (stack.peek().left.charAt(0) == chars[i]){
                printProcess(stack,chars,i);
                System.out.println("   "+chars[i]+"匹配");
                stack.pop();
                i++;
            }else {
                //不相等的话则找该输入串首元素和栈顶元素在分析表中对应的表达式
                Node[] analysisChart = getAnalysisChart(chars[i], stack.peek(), nodeMap, formulaMap);
                printProcess(stack,chars,i);
                Node popNode = stack.pop();//弹出栈顶元素

                //输出推导所用产生式
                System.out.print("        推导所用产生式："+popNode.left+"→");
                for (int j = 0;j<analysisChart.length;j++){
                    System.out.print(analysisChart[j].left+" ");
                }
                System.out.println();

                //如果不是产生空的产生式则将产生式入栈
                if (analysisChart!=null&& !analysisChart[0].left.equals("ε")){
                    //将分析表对应的表达式压入栈
                    for (int j = analysisChart.length-1;j>=0;j--){
                        stack.push(analysisChart[j]);
                    }
                }
            }
        }
    }
}
