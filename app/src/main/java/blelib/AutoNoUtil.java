package blelib;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/4/20 09:38
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class AutoNoUtil {

    public static LinkedList<Integer> linkedAutoNoList = new LinkedList<>();

    /**
     * 添加 AutoNo
     *
     * @param autoNo
     * @param linkedListSize
     */
    public static void addAutoNo(int autoNo, int linkedListSize) {
        if (linkedAutoNoList.size() == linkedListSize)
            linkedAutoNoList.removeFirst();
        linkedAutoNoList.addLast(autoNo);
    }

    /**
     * 找出最好的 AutoNo
     *
     * @param autoNoCountThreshold
     * @return
     */
    public static int getBestAutoNo(int linkedListSize, int autoNoCountThreshold) {
        int bestAutoNo = 0;
        if (linkedAutoNoList.size() == linkedListSize) {
            HashMap<Integer, Integer> countMap = new HashMap<>();
            for (Integer integer : linkedAutoNoList) {
                int count = countMap.containsKey(integer) ? countMap.get(integer) : 0;
                count++;
                if (count == autoNoCountThreshold) {
                    bestAutoNo = integer;
                    linkedAutoNoList.clear();
                    break;
                } else {
                    countMap.put(integer, count);
                }
            }
        }
        return bestAutoNo;
    }

}
