package 实验二;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @version v1.0
 * @ClassName: Node
 * @Description: 节点
 * @Author: ChenQ
 * @Date: 2021/4/18 on 22:53
 */
public abstract class Node {
    public String left;
    public String right;
    public boolean produceNull;//能否推导出‘ε’
    public Set<String> first = new HashSet<>();
    public Set<String> follow = new HashSet<>();
    public Set<String> select = new HashSet<>();
    public abstract Set<String> getFirst();
    public abstract Set<String> getFollow(Map<Node,Formula[]> formulaMap);
    public abstract Set<String> getSelect(Map<Node,Formula[]> formulaMap);
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
