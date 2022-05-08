package javaCode;

import java.util.*;

public class RunSort {
    private Date now = null;
    private boolean showResult = true;

    public static void main(String[] args){
        RunSort runSort = new RunSort();
        runSort.run();
    }


    /**
     排序方法             最壞時間       平均時間         穩定    額外空間            備註說明
     氣泡排序Bubble       O(n^2)        O(n^2)         穩定     O(1)              n小比較好。
     選擇排序Selection    O(n^2)        O(n^2)         不穩定   O(1)               n小較好，部份排序好更好。
     插入排序Insertion    O(n^2)        O(n^2)         穩定     O(1)              大部份排序好比較好。
     快速排序Quick        O(n^2)        O(n logn)      不穩定   O(n)~O(logn)      在資料已排序好時會產生最差狀況。
     堆積排序Heap         O(n logn)     O(n logn)      不穩定   O(1)              依據heap root排列可分為大根堆或小根堆。
     薛爾排序shell        O(n^s),1<s<2  O(n (logn)^2)  穩定     O(1)              n小比較好。
     合併排序Merge        O(n logn)     O(n logn)      穩定     O(n)              常用於外部排序。
     基數排序Radix        O(k n)        O(n)~O(k n)    穩定     O(k + n)          n是排序元素個數，k是數字位數。

        *穩定是指若有相同大小的數，排列後前後順序不會變化
        @see <a href="http://spaces.isu.edu.tw/upload/18833/3/web/sorting.htm#_Toc229730276">參考教學</a>
     */
    private void run() {
        int[] nums = createRandomSeries(10, 1000);
//        nums = createRandomSeries(50000, 1000); showResult = false;

        print("Time/Space Complexity", nums);
        now = new Date();

        print("bubbleSort O(n^2)/O(n)", bubbleSort(nums.clone()));
        print("selectionSort O(n^2)/O(n)", selectionSort(nums.clone()));
        print("insertSort O(n^2)/O(n)", insertSort(nums.clone()));
        print("quickSort O(nlogn)/O(n)+", quickSort(nums.clone()));
        print("heapSort O(nlogn)/O(n)", heapSort(nums.clone()));
        print("shellSort O(n(logn)^2/O(n))", shellSort(nums.clone()));
        print("mergeSort O(nlogn)/O(n)", mergeSort(nums.clone()));
        print("radixSort O(kn)/O(k+n)", radixSort(nums.clone()));
    }

    /**
     * bubbleSort
     * 從左邊第一個開始兩兩比大小交換，每輪都會有一個數浮到水面最右邊
     * Time Complexity = O(n^2)
     * Space Complexity = O(n)
     * 穩定，n小比較好。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] bubbleSort(int[] nums){
        //跟後一個比較，所以到length - 1就好
        for(int i=0; i<nums.length - 1; i++){
            boolean swapFlag = false;//是否後面已經沒有需要交換，若沒有交換則提前退出迴圈
            for(int j=0; j<nums.length - 1 - i; j++){
                if(nums[j] > nums[j + 1]){
                    //swap
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    swapFlag = true;
                }
            }
            if(!swapFlag)//若沒有交換代表後面已經是排序好的，提前退出迴圈
                break;
        }
        return nums;
    }

    /**
     * selectionSort
     * 每輪掃描最小的數，直接丟到最左邊排序
     * Time Complexity = O(n^2)
     * Space Complexity = O(n)
     * 不穩定，適用情況少見，n小較好，部份排序好更好。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E9%80%89%E6%8B%A9%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] selectionSort(int[] nums){
        //只剩最後一個就直接排進來，所以到length - 1就好
        for(int i=0; i<nums.length - 1; i++){
            int min = i;//最小數的位子
            for(int j = i + 1; j<nums.length; j++){
                //找到最小的數的位子
                if(nums[min] > nums[j]){
                    min = j;
                }
            }
            //把最小的跟最左邊交換
            if(i != min){
                int temp = nums[i];
                nums[i] = nums[min];
                nums[min] = temp;
            }
        }
        return nums;
    }

    /**
     * insertSort
     * 相當於組撲克牌。每次往右找到新的數，就往左邊塞入大小適合的位子
     * Time Complexity = O(n^2)
     * Space Complexity = O(n)
     * 穩定，大部份排序好比較好。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] insertSort(int[] nums){
        //從1開始
        for(int i=1; i<nums.length; i++){
            int j = i - 1;
            int temp = nums[i];

            //往前找temp，若temp太小的時候，把temp往後左邊送
            while(j >= 0 && nums[j] > temp){
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = temp;//temp插入到比temp小的前一個
        }
        return nums;
    }

    /**
     * quickSort
     * 左指標往右尋找比基準大的，右指標往左尋找比基準小的，都找到之後交換
     * 指標相遇後，此時比基準值小的都會在左側，反之比基準大都會在右側
     * 指標的左右側在繼續做quickSort
     * Time Complexity = O(n logn)
     * Space Complexity = O(n)+ (依照演算法不同)
     * 不穩定，在資料已排序好時會產生最差狀況。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] quickSort(int[] nums){
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void quickSort(int[] nums, int left, int right){
        //設定離開遞迴的條件
        if(left < right) {
            int standard = nums[(left + right) / 2];//基準值，此為取中間
            int i = left - 1;
            int j = right + 1;
            while (true) {
                //左指標往右尋找比基準大的 ++i是先+
                while (standard > nums[++i]) ;

                //右指標往左尋找比基準小的 --j是先-
                while (standard < nums[--j]) ;
                if (i >= j)
                    break;

                //swap 遇到基準值時會交換基準值，並且會是最後一次while
                //相當於移動基準值位子，此時比基準值小的都會在左側，反之比基準大都會在右側
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }

            quickSort(nums, left, i - 1);//基準值左側遞迴
            quickSort(nums, j + 1, right);//基準值右側遞迴
        }
    }

    /**
     * heapSort
     * 先建立大(或小)根堆(largest heap)，此二元樹中parents nodes都會比child nodes來得大
     * 將最後一個node交換heap root(會變成數列中第一個)，從heap中移除這個node，之後重新對heap排列(heapify)
     *
     *               0
     *          1         2
     *       3     4   5     6
     *     7   8 9
     *
     * Time Complexity = O(n logn)
     * Space Complexity = O(n)
     * 不穩定，n小比較好。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] heapSort(int[] nums){
        //對所有有子節點的node進行處裡，由下往上處理節點
        for(int i = (nums.length / 2) - 1; i >= 0; i--){
            heapify(nums, i, nums.length);
        }

        //建立大根堆largest heap後，從最後一個node交換root，並將它排除
        for(int i = nums.length - 1; i > 0; i--){
            //把最後一個node跟最大(root)的交換
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            //i是逐漸減少的heap長度
            heapify(nums, 0, i);
        }
        return nums;
    }

    /**
     * 重新排列heap，較小數字進行下墜
     * @param nums 數列
     * @param i 檢查的節點所在位子
     * @param len heap的大小，不是數列的
     */
    public void heapify(int[] nums, int i, int len){
        int largest = i;//先假設最大就是節點本身
        int left = 2 * i + 1;//左邊child node的位子
        int right = 2 * i + 2;//右邊child node的位子

        //找出最大的節點是哪一個
        //並且left or right沒有超過len，是真實存在的
        if(len > left && nums[left] > nums[largest]){
            largest = left;
        }
        if(len > right && nums[right] > nums[largest]){
            largest = right;
        }

        //如果i本身不是最大的，把i跟最大的交換
        //並且檢查被換掉的largest是不是也比child nodes大
        if(i != largest){
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;

            heapify(nums, largest, len);//將數字進行下墜
        }
    }

    /**
     * shellSort
     * insertSort的強化，將數列先分組，每組先進行insertSort
     * 之後每一輪減少組數進行insertSort，直到最後只剩一組再insertSort
     * Time Complexity = O(n (logn)^2)
     * Space Complexity = O(n)
     * 穩定，依據heap root排列可分為大根堆或小根堆。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E5%B8%8C%E5%B0%94%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] shellSort(int[] nums){
        int group = 2;//這個是要分成幾個組做insert sort
        for(int gap = nums.length / group; gap > 0; gap /= group){

            //開始insert sort，其實就是把insert sort每次移動1，改成每次移動gap，相當於分組了
            for(int i=gap; i<nums.length; i++){
                int j = i - gap;//減回gap，第一次會減為1
                int temp = nums[i];

                //往前找temp，若temp太小的時候，把temp往後左邊送
                while(j >= 0 && temp < nums[j]){
                    //一次移動gap格
                    nums[j + gap] = nums[j];
                    j -= gap;
                }
                nums[j + gap] = temp;//一次移動gap格
            }
        }
        return nums;
    }

    /**
     * mergeSort
     * 先將數列二元拆分到最細，在合併的時候比較，將較小的數先放入新的合併數列
     * 合併的時候需有三個指標，leftStart表示左葉指標，rightStart表示右葉指標，k表示要合併之數列指標
     * 每次合併後須將合併後的數列(result)更新到原數列上
     *
     *           2 7 4 1
     *      2 7          4 1
     *    2     7      4     1
     *      2 7          1 4
     *          1 2 4 7
     *
     * Time Complexity = O(n logn)
     * Space Complexity = O(n)
     * 穩定，常用於外部排序。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] mergeSort(int[] nums){
        int[] result = new int[nums.length];

        //必須要傳入result，跟新的數列result也須跟著遞迴更新
        mergeSort(nums, result, 0, nums.length - 1);
        return nums;
    }
    public void mergeSort(int[] nums, int[] result, int start, int end){
        //設定當差分到等於自己時，不再繼續拆分
        if(start < end){
            int len = end - start;//本次merge的長度
            int mid = (len / 2) + start;//中間的位子=長度的一半+開始位子
            int leftStart = start;//左葉的開始與結束位子
            int leftEnd = mid;
            int rightStart = mid + 1;//右葉的開始與結束位子
            int rightEnd = end;

            //先講兩葉進行拆分
            mergeSort(nums, result, leftStart, leftEnd);
            mergeSort(nums, result, rightStart, rightEnd);

            /*
            開始進行合併，誰比較小誰先進行合併，並在該葉以及原始數列的指標++
            k++的同時左或右葉比較小的也要Start++
                nums = [496, 665, 911, 86, 303, 438]
                        ^k++

                left leaf = [496, 665, 911]   right leaf = [86, 303, 438]
                             ^leftStart                     ^rightStart++
             */
            int k = start;//最原始數列的位子指標
            while (leftStart <= leftEnd && rightStart <= rightEnd)//左右葉都還有數的情況
                result[k++] = nums[leftStart] < nums[rightStart] ? nums[leftStart++] : nums[rightStart++];
            while (leftStart <= leftEnd)//只剩左葉有數的情況
                result[k++] = nums[leftStart++];
            while (rightStart <= rightEnd)//只剩右葉有數的情況
                result[k++] = nums[rightStart++];

            //將調整好的result回歸到原數列
            for (k = start; k <= end; k++)
                nums[k] = result[k];
        }
    }

    /**
     * radixSort
     * 分成LSD(Least significant digital)或MSD(Most significant digital)，此為LSD
     * 從個位數開始，將數依序放入1~10的籃子，之後依序串回數列
     * 第二回合從十位數子開始，也依序依照十位數放入1~10的籃子，直到最後一位數
     *
     * [815, 215, 404, 263, 412]
     *  0   1   2   3   4   5   6   7   8   9
     *          412 263 404 815
     *                      215
     * =>[412, 263, 404, 815, 215]
     *
     *  0   1   2   3   4   5   6   7   8   9
     *  404 412                 263
     *      815
     *      215
     * =>[404, 412, 815, 215, 263]
     *
     *  0   1   2   3   4   5   6   7   8   9
     *          215     404             815
     *          263     412
     * =>[215, 263, 404, 412, 815]
     *
     * Time Complexity = O(k n)
     * Space Complexity = O(k + n)
     * 穩定，n是排序元素個數，k是數字位數。
     *
     * @see <a href="https://zh.wikipedia.org/zh-tw/%E5%9F%BA%E6%95%B0%E6%8E%92%E5%BA%8F"></a>
     * @param nums
     * @return
     */
    public int[] radixSort(int[] nums){
        //找出最大位數
        int max = 0;
        for(int num : nums){
            if(num > max){
                max = num;
            }
        }

        for(int exp = 1; max / exp > 0; exp *= 10){
            radixCount(nums, exp);
        }
        return nums;
    }

    /**
     * 依照位數放入1~10的籃子
     * @param nums 數列
     * @param exp 位數(1, 10, 100...)
     */
    public void radixCount(int nums[], int exp){
        int output[] = new int[nums.length];//回傳陣列
        int count[] = new int[10];//用來記錄每個位數出現的數字個數
        //使用以下的方法不用創建arraylist，透過預判count[i]的個數，也避免掉無用迴圈運算，使時間複雜度為O(kn)

        //初始化count[]
        for(int i=0; i<count.length; i++)
            count[i] = 0;

        //記錄每種位數的出現次數
        for (int num : nums)
            count[(num / exp) % 10]++;

        //累計紀錄出現的次數 位元越大累積越多
        /*
        ex:
        exp = 1
        nums[] = [815, 215, 404, 263, 412]
        count[] = [0, 0, 1, 2, 3, 5, 5, 5, 5, 5]
        */
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        //建立output，由最大開始
        for (int i = nums.length - 1; i >= 0; i--) {
            //找出nums[i]的位數
            int digit = (nums[i] / exp) % 10;

            //找出nums[i]放在的count，累積了多少
            int countVal = count[digit];

            //累積數量 - 1 = output要放的位子
            output[countVal - 1] = nums[i];

            //記得把count--，下次再遇到位子會往前
            count[digit]--;
        }

        //複製回nums，直接用nums = output會new一個nums不會變化方法外nums的值
        for (int i = 0; i < nums.length; i++)
            nums[i] = output[i];
    }



    /**
     * ---------------------------------------------------------------------------------------
     */

    /**
     * 產生隨機數列
     * @param length 長度
     * @param max 0 ~ max
     * @return
     */
    public int[] createRandomSeries(int length, int max) {
        int[] series = new int[length];
        Random random = new Random();
        for(int i=0; i<length; i++){
            series[i] = random.nextInt(max);
        }
        return series;
    }

    /**
     * 印出結果
     * @param str
     * @param series
     */
    public void print(String str, int[] series){
        StringBuffer sb = new StringBuffer();
        sb.append(leftPad(getDelay(), 7))
                .append(" | ")
                .append(leftPad(str, 28))
                .append(" | ");

        if(showResult)
            sb.append(Arrays.toString(series));
        System.out.println(sb.toString());
    }

    /**
     * 計算運行時間
     * @return
     */
    public String getDelay(){
        if(now == null)
            return "";

        Date temp = new Date();
        long delay = temp.getTime() - now.getTime();
        now = temp;
        return delay + "ms";
    }

    /**
     * 向左補滿空白
     * @param str
     * @param length
     * @return
     */
    public String leftPad(String str, int length) {
        if(str == null) {
            str = "";
        }
        if (str.length() > length) {
            return str;
        }
        String format = "%" + length + "s";
        char padChar = ' ';
        return String.format(format, str).replace(' ', padChar);
    }

    /* test log:
        createRandomSeries(100000, 1000)
                |        Time/Space Complexity |
        24750ms |       bubbleSort O(n^2)/O(n) |
         6736ms |    selectionSort O(n^2)/O(n) |
         1293ms |       insertSort O(n^2)/O(n) |
           21ms |     quickSort O(nlogn)/O(n)+ |
           28ms |       heapSort O(nlogn)/O(n) |
           27ms |  shellSort O(n(logn)^2/O(n)) |
           37ms |      mergeSort O(nlogn)/O(n) |
           38ms |       radixSort O(kn)/O(k+n) |

        createRandomSeries(1000, 100000)
                |        Time/Space Complexity |
           13ms |       bubbleSort O(n^2)/O(n) |
           28ms |    selectionSort O(n^2)/O(n) |
            7ms |       insertSort O(n^2)/O(n) |
            1ms |     quickSort O(nlogn)/O(n)+ |
            1ms |       heapSort O(nlogn)/O(n) |
            2ms |  shellSort O(n(logn)^2/O(n)) |
            1ms |      mergeSort O(nlogn)/O(n) |
            1ms |       radixSort O(kn)/O(k+n) |
     */
}
