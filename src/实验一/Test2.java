package 实验一;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @version v1.0
 * @ClassName: Test
 * @Description:
 * @Author: ChenQ
 * @Date: 2021/3/31 on 10:27
 */
public class Test2 {
    public static void main(String[] args) {
        One one = new One("Txt/实验一文件/KeyWord.txt","Txt/实验一文件/OperatorWord.txt","Txt/实验一文件/BoundarySymbolWord.txt","Txt/实验一文件/IdentifyWord.txt","Txt/实验一文件/NumberWord.txt");
        String[] strings = one.readTxt("Txt/实验一文件/TextFile2.txt");
        for (String key :strings){
            System.out.println(key);
        }
        for (String s:strings){

            String keyword_key = one.isWord(s, one.keyword_map);
            if (keyword_key.equals("-1")){
                String operatorword_key = one.isWord(s, one.operatorword_map);
                if (operatorword_key.equals("-1")){
                    String boundaryword_key = one.isWord(s, one.boundaryword_map);
                    if (boundaryword_key.equals("-1")){
                        String identifyword_key = one.isIdentifyWord(s);
                        if (identifyword_key.equals("-1")){
                            String numberword_key = one.isNumberWord(s);
                            if (numberword_key.equals("-1")){
                                if (!s.isEmpty()){
                                    System.out.println("错误字符，无法识别"+s.length()+" "+s);
                                }
                            }else {
                                System.out.println("常数："+numberword_key+":"+s);
                            }
                        }else {
                            System.out.println("标识符："+identifyword_key+":"+s);
                        }
                    }else {
                        System.out.println("界符："+boundaryword_key+":"+s);
                    }
                }else {
                    System.out.println("操作符："+operatorword_key+":"+s);
                }
            }else {
                System.out.println("保留字: "+keyword_key+":"+s);
            }
        }
    }
    @Test
    public void testIdentityword(){
//        One one = new One();
        String s = "const a = 9 ; var in8 = 10 ; const b =76;";
        String[] split = s.split("[+]");
//        String[] strings = one.mySplit(s);
        for (String key :split){
            System.out.println(key);
        }
    }
    @Test
    public void test2(){
        String s = "+";
        System.out.println(s.length());
    }
    @Test
    public void test3(){
        String regex;
        String s = "+";
        if (s.length()==1){
            regex = "["+s+"]";
        }else {
            regex = s;
        }
        System.out.println(regex);
        String content = "const a = 9 ; var in8 = 10 ; const b =76;";
        String n[] = content.split(regex);
        for (String key :n){
            System.out.println(key);
        }
    }

    @Test
    public void test4(){
        String regex = "\\s+";
        System.out.println(regex);
        String content = "q <= m/n;\n" +
                "       r >= m-q*n;\n" +
                "       m =n;\n" +
                "       n > r;";
//        One one = new One();
//        String contract = one.contract(content, one.operatorword_map);
//        System.out.println(contract);
        //判断“<=" ">=" ":="的情况
        /*if(map.get(key).equals("=")){
                        if(s[i].endsWith("<")||s[i].endsWith(">")||s[i].endsWith(":")){
                            content +=(s[i]+map.get(key)+" ");
                            continue;
                        }
                    }*/
        /*if (((i+1)<s.length)&&(map.get(key).equals("<")||map.get(key).equals(">"))&&(s[i+1].startsWith("="))){
                        content += (s[i]+" "+map.get(key)+s[i+1]+" ");
                        i++;
                        continue;
                    }*/
    }
}
