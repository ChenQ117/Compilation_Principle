package 实验三;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName: Test
 * @Description: abbcde的分析过程
 * @Author: ChenQ
 * @Date: 2021/5/10 on 18:25
 */
public class Test {
    public static void main(String[] args) {
        /*Map<String,String> formulaMap = new HashMap<>();
        formulaMap.put("aAcBe","S");
        formulaMap.put("Ab","A");
        formulaMap.put("b","A");
        formulaMap.put("d","B");*/
        String[][] formulaArray = {{"S","aAcBe"},{"A","Ab"},{"A","b"},{"B","d"}};
        analysis(" abbcde#",formulaArray);
    }
    public static void analysis(String string,String[][] formulaArray){
        char[] chars = string.toCharArray();
        String[] stack = new String[string.length()];
        stack[0] = "#";
        List<StringBuilder> temps = new ArrayList<>();
        int i=1;
        int top = 1;
        while (top>0&&i<string.length()){
            StringBuilder temp = new StringBuilder();
            boolean flag = false;
            String s ="";
            //获得左边的分析栈
            for (int j=0;j<top;j++){
                s += stack[j];
            }
            temp.append(s);

            //获得右边的表达式
            s = "";
            for (int j=i;j<string.length();j++){
                s+= chars[j]+"";
            }
            temp.append("\t\t"+s);

            if (top>1){
                for (int j=1;j<top;j++){
                    s = "";
                    for (int k=j;k<top;k++){
                        s+=stack[k];
                        boolean flag_2 = false;
                        int t=0;
                        for (;t<formulaArray.length;t++){
                            if (formulaArray[t][1].equals(s)){
                                top-=s.length();
                                top++;
                                stack[top-1]=formulaArray[t][0];
                                temp.append("\t\t规约"+formulaArray[t][0]+"->"+formulaArray[t][1]);
                                flag = true;
                                flag_2 = true;
                            }
                        }
                        if (flag_2){
                            break;
                        }
                    }
                }

            }
            if (!flag){
                if (stack[top-1].equals("S")){
                    temp.append("\t\t接受");
                    temps.add(temp);
                    break;
                }
                String str = chars[i++]+"";
                stack[top++] = str;
                temp.append("\t\t移进");
            }
            temps.add(temp);
        }

        //输出分析结果
        for (StringBuilder builder:temps){
            System.out.println(builder);
        }
    }
}
