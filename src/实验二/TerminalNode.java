package 实验二;

import java.util.Map;
import java.util.Set;

/**
 * @version v1.0
 * @ClassName: TerminalNode
 * @Description: 终端节点
 * @Author: ChenQ
 * @Date: 2021/4/18 on 22:53
 */
public class TerminalNode extends Node {
    public TerminalNode(String left, Map<String,Node> map){
        this.left = left;
        produceNull = false;
        first.add(left);
        follow.add(left);
        select.add(left);
        map.put(left,this);
    }
    public TerminalNode(String left){
        this.left = left;
        produceNull = false;
    }

    @Override
    public String toString() {
        return "TerminalNode{" +
                "s='" + left + '\'' +
                ", produceNull=" + produceNull +
                ", first=" + first +
                '}';
    }

    @Override
    public Set<String> getFirst() {
        return first;
    }

    @Override
    public Set<String> getFollow(Map<Node,Formula[]> formulaMap) {
        return follow;
    }

    @Override
    public Set<String> getSelect(Map<Node,Formula[]> formulaMap) {
        return select;
    }
}
