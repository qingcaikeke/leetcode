import java.util.Arrays;

public class MySort {
    public static void main(String[] args) {
        int[] nums = new int[]{6,5,4,3,2,1};
        mergeSort(nums);
        System.out.println(Arrays.toString(nums));
    }
    static void swap(int[] nums,int a,int b){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    /**
     * 快速排序
     * 选最后一个元素，小的放前面，大的放后面(需要两个指针，一个用于遍历，一个用于记录比她小的元素的位置)
     * 需要递归：时间（nlog(n)）空间（log(n)）
     * 划分时间复杂度o(n),因为要遍历整个数组，分割后每次排序log(n),因为每次减半
     */
    public static void quickSort1(int[] nums,int left,int right){
        if(left>=right){
            return;
        }
        int index = partition(nums,left,right);
        //前闭后闭
        quickSort1(nums,left,index-1);
        quickSort1(nums,index+1,right);
    }
    //每次处理的是一部分数组，不传left和right的话需要拷贝数组片段
    public static int partition(int[] nums,int left,int right){
        int pivot = nums[right];
        //1.当前元素大于基准，分区指示器不动，遍历指示器+1
        //2.当前元素小于基准，分区指示器和当前元素交换，分区指示器+1，遍历指示器+1
        int parIndex = left-1;
        //妙处：初始化为-1，先+1再交换，
        // 初始化为0先交换，再+1的话，需要返回parIndex-1;
        for(int i=left;i<=right;i++){
            if(nums[i]<=pivot){
                parIndex++;
                swap(nums,i,parIndex);
            }
        }
        return parIndex;
    }

    /**
     * 冒泡排序，时间o（n^2）,空间o(1)
     */
    public static void bubbleSort(int[] nums){
        int n = nums.length;
        //一次循环可以排号一个元素
        for(int i=0;i<n;i++){
            int end = n-1-i;//已经有i个排好了
            //每次比较当前元素与下一个元素，把更大的挪到后面
            for(int j=0;j<end;j++){
                if(nums[j]>nums[j+1]){
                    swap(nums,j,j+1);
                }
            }
        }
    }

    /**
     * 归并排序:两个函数，一个递归，一个合并两个有序数组
     * 时间（nlogn，每一层合并的时间为n，递归深度logn）空间（nlogn：每一层n，深度logn）
     */
    public static void mergeSort(int[] nums){
        if(nums.length <2) return;
        int mid = nums.length /2;
        int[] left = Arrays.copyOfRange(nums,0,mid); //包含头(0)不包含尾mid
        int[] right = Arrays.copyOfRange(nums,mid, nums.length);
        mergeSort(left); //传入一个数组后做两件事 1.拆分成左右 2. 左右合并再赋值到传入的那个数组
        mergeSort(right);
        //分到最小后调用merge合并作为新的left传入
        merge(nums,left,right);
    }

    public static int[] merge(int[] nums,int[] nums1,int[] nums2){
        int m = nums1.length,n = nums2.length;
        int i=0,j=0,k=0;
        while (i<m&&j<n){
            if(nums1[i]<=nums2[j]) nums[k++] = nums1[i++];
            else nums[k++] = nums2[j++];
        }
        while (i<m){
            nums[k++] = nums1[i++];
        }
        while (j<n){
            nums[k++] = nums2[j++];
        }
        return nums;
    }
    /**
     * 堆排序
     */

    /**
     * 插入排序
     */





}
