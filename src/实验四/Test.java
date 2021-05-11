package 实验四;

import 实验一.One;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @version v1.0
 * @ClassName: Test
 * @Description: 文法翻译
 * @Author: ChenQ
 * @Date: 2021/5/11 on 16:09
 */
public class Test {
    public static void main(String[] args) {
        One one = new One();
        Stack<String> stackBound = new Stack<>();//存花括号
        Stack<AnaFormula> anaFormulaStack = new Stack<>();//存未完全确定的四元式
        List<AnaFormula> anaFormulas=new ArrayList<>();//用于存储所有的四元式
        int op = 0;
        String[] strings = one.readTxt("Txt/实验四文件/code.txt");
        for (String s:strings){
            System.out.println(s);
        }
        for (int i=0;i<strings.length;i++){
            if (strings[i].equals("while")){
                String operate ="";
                for (int j=i+1;j<strings.length;j++){
                    //花括号进栈，花括号用于判断while循环跳转的截止位置
                    if (strings[j].equals("(")){
                        AnaFormula anaFormula = new AnaFormula(op,"j"+strings[j+2],strings[j+1],strings[j+4],"?");
                        anaFormulaStack.push(anaFormula);
                        j=j+4;
                        anaFormulas.add(anaFormula);
                        op++;
                        continue;
                    }
                    if (!strings[j].equals("}")){
                        int k=j;
                        //截取出一行代码，以“；”作为截取符
                        for (int t=j+1;t<strings.length;t++){
                            if (strings[t].equals(";")){
                                k=t;
                                break;
                            }
                        }
                        System.out.println("============="+strings[j]);
                        //判断截取的这一行代码是否为赋值语句
                        if (k!=j){
                            int index=0;
                            for (int t=j+1;t<k;t++){
                                if (strings[t].equals("=")){
                                    index=t;
                                    break;
                                }
                            }
                            //如果是运算赋值语句
                            if (index!=0){
                                String[] subStrings = Arrays.copyOfRange(strings, index + 1, k);
                                Stack<String> argStack = new Stack<>();//操作数栈
                                op = Test.jisuan(argStack,subStrings, one, op, anaFormulas);
                                //完成赋值语句的识别
                                AnaFormula anaFormula = new AnaFormula(op,strings[index],argStack.peek(),"_",strings[j+2]);
                                op++;
                                anaFormulas.add(anaFormula);
                                j = k;//第k个符号为“；”，不需要再考虑了
                                continue;
                            }else {
                                //如果不是运算赋值语句
                                int t=j+1;
                                for (;t<k;t++){
                                    if (strings[t].equals("++")){
                                        break;
                                    }
                                }
                                //如果t不等于k，则为自增表达式,识别i++语句
                                if (t!=k){
                                    String result = "T"+anaFormulas.size();
                                    AnaFormula anaFormula = new AnaFormula(op,"+",strings[j],"1",result);
                                    op++;
                                    anaFormulas.add(anaFormula);
                                    AnaFormula anaFormula1 = new AnaFormula(op,"=",result,"_",strings[j]);
                                    op++;
                                    anaFormulas.add(anaFormula1);
                                    j=j+3;
                                    continue;
                                }
                            }

                        }
                    }
                    if (strings[j].equals("}")){
                        //从后往前识别
                        int t;
                        for (t=anaFormulas.size()-1;t>=0;t--){
                            if (anaFormulas.get(t).result.equals("?")){
                                break;
                            }
                        }
                        if (t!=-1){
                            AnaFormula anaFormula = new AnaFormula(op,"j","_","_",String.valueOf(anaFormulas.get(t).op));
                            op++;
                            anaFormulas.add(anaFormula);
                            anaFormulas.get(t).setResult(String.valueOf(op));
                        }
                    }
                }
            }
        }
        show(anaFormulas);
    }
    public static int jisuan(Stack<String> argStack,String[] strings,One one,int op,List<AnaFormula> anaFormulas) {
        Stack<String> operatorStack = new Stack<>();//运算符栈
        for (int i = 0; i < strings.length; i++) {
            //判断当前字符是否为运算符，是的话则加入运算符栈
            String operator_key = one.isWord(strings[i], one.operatorword_map);
            if (!operator_key.equals("-1")) {
                operatorStack.push(strings[i]);
            } else if (!strings[i].equals(" ")) {
                argStack.push(strings[i]);
            }
        }
        while (!operatorStack.empty()) {
            //出栈一个运算符
            String oper = operatorStack.peek();
            operatorStack.pop();
            //出栈两个操作数
            String agr2 = argStack.peek();
            argStack.pop();
            String agr1 = argStack.peek();
            argStack.pop();
            String result = "T" + anaFormulas.size();
            AnaFormula anaFormula = new AnaFormula(op, oper, agr1, agr2, result);
            op++;
            argStack.push(result);//将运算结果加入操作数栈
            anaFormulas.add(anaFormula);
        }
        return op;
    }
    public static void show(List<AnaFormula> anaFormulas){
        for (AnaFormula anaFormula:anaFormulas){
            System.out.println(anaFormula);
        }
    }
}
