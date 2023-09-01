package sortMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SortMethod {
    public int[][] merge(int[][] intervals) {
//        也可以Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });
        ArrayList<int[]> merged = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int l = intervals[i][0];
            int r = intervals[i][1];
            //没有.length只有.size(只有一维数组有.length)
            //使用.get方法，而非merged[下标]
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < l) {
                merged.add(new int[]{l, r});
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(r, merged.get(merged.size() - 1)[1]);
            }
        }
        //toArray方法可以将一个List转成数组,需要一个同类型数组的原型来指定返回数组的类型
        return merged.toArray(new int[0][]);

    }
}
