package com.roncoo.pay.app.reconciliation.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SortUtil {
	
	public static void main(String[] args) throws IOException {
		
//		print();
		
//		int [] array = {4,12,6,3,1,2,67,44,32,34,56,31,35};
//		QuickSort(array, 0, array.length-1);
//		System.out.println(Arrays.toString(array));
		
		
		
//		List<RpTradePaymentRecordV2> array = new ArrayList<RpTradePaymentRecordV2>();
//		RpTradePaymentRecordV2  temp = new RpTradePaymentRecordV2();
//		temp.setMerchantOrderNo("111111");
//		array.add(temp);
//		
//		temp = new RpTradePaymentRecordV2();
//		temp.setMerchantOrderNo("222222");
//		array.add(temp);
//		
//		temp = new RpTradePaymentRecordV2();
//		temp.setMerchantOrderNo("333333");
//		array.add(temp);
//		
//		temp = new RpTradePaymentRecordV2();
//		temp.setMerchantOrderNo("212121");
//		array.add(temp);
//		
//		temp = new RpTradePaymentRecordV2();
//		temp.setMerchantOrderNo("444444");
//		array.add(temp);
//		
//		temp = new RpTradePaymentRecordV2();
//		temp.setMerchantOrderNo("121212");
//		array.add(temp);
//		System.out.println(array.toString());
//		
//		RpTradePaymentRecordV2[] strings = new RpTradePaymentRecordV2[array.size()];
//
//		array.toArray(strings);
//		
//		QuickSort(strings, 0, array.size()-1);
//		
//		System.out.println(Arrays.toString(strings));
//
	   List<RpTradePaymentRecordV2> array = new ArrayList<RpTradePaymentRecordV2>();

	   long startTime = System.currentTimeMillis();
       String line ;
       try {
    		RpTradePaymentRecordV2  temp;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:/bigdata_1.txt")));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);//10M缓存
//            FileWriter fw = new FileWriter(outputFile);
            String [] list;
            while (in.ready()) {
            	line = in.readLine();
//            	TimeUnit.MILLISECONDS.sleep(1);
                list = line.split("\\|");
                temp = new RpTradePaymentRecordV2();
        		temp.setMerchantOrderNo(list[0]);
        		temp.setPayerUserNo(list[1]);
        		temp.setMerchantNo(list[2]);
        		temp.setMerchantOrderNo(list[3]);
        		temp.setOrderAmount(list[4]);
        		array.add(temp);
//        		System.out.println(temp.toString());
            }
            in.close();
//            fw.flush();
//            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
       System.out.println("文件读取完成");
       
        RpTradePaymentRecordV2[] result = new RpTradePaymentRecordV2[array.size()];
        array.toArray(result);
		QuickSort(result, 0, array.size()-1);
		
		System.out.println("文件排序完成，写入磁盘");

		try {
			int i = 0;
			FileWriter fw = new FileWriter("C:/bigdataSort.txt");
			while(i<result.length){
				 fw.append(result[i++].getMerchantOrderNo()+"\n");
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    long endTime = System.currentTimeMillis();
	    System.out.println("耗费时间： " + (endTime - startTime) + " ms");
	    
	    //263M数据排序   耗费时间： 19169 ms
	    //351M数据排序   耗费时间： 57558 ms
	}
	
	
	

    private static void print() throws IOException {
		// TODO Auto-generated method stub
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("C:/bigdataSort.txt")));
        BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);//10M缓存
//        FileWriter fw = new FileWriter(outputFile);
        String [] list;
        String line ;
        while (in.ready()) {
        	line = in.readLine();
        	System.out.println(line);
        }
	}




	public static RpTradePaymentRecordV2[] QuickSort(RpTradePaymentRecordV2[] array, int start, int end) {
    	
        if (array.length < 1 || start < 0 || end >= array.length || start > end) {
            return null;
        }
        int smallIndex = partition(array, start, end);
        if (smallIndex > start) {
        	QuickSort(array, start, smallIndex - 1);
        }
        if (smallIndex < end) {
        	QuickSort(array, smallIndex + 1, end);
        }
    	return array;
    }
	

    public static int partition(RpTradePaymentRecordV2[] array, int start, int end) {
    	
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        swap(array, pivot, end);
        for (int i = start; i <= end; i++) {
            if (array[i].compareTo(array[end])<=0) {
                smallIndex++;
                if (i > smallIndex) {
                    swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    	
    }
    

    public static void swap(RpTradePaymentRecordV2[] array, int i, int j) {
    	RpTradePaymentRecordV2 temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
	
	 /**
     * 快速排序方法
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int[] QuickSort(int[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end >= array.length || start > end) {
            return null;
        }
        int smallIndex = partition(array, start, end);
        if (smallIndex > start) {
            QuickSort(array, start, smallIndex - 1);
        }
        if (smallIndex < end) {
            QuickSort(array, smallIndex + 1, end);
        }
        return array;
    }

    /**
     * 快速排序算法——partition
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        swap(array, pivot, end);
        for (int i = start; i <= end; i++) {
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }

    /**
     * 交换数组内两个元素
     *
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}

class RpTradePaymentRecordV2 implements Comparable<RpTradePaymentRecordV2>{
	
	

    /** 银行订单号 **/
    private String bankOrderNo;

    /** 付款方编号 **/
    private String payerUserNo;

    /** 商户编号 **/
    private String merchantNo;
    

    /** 商户订单号 **/
    private String merchantOrderNo;
    

    /** 订单金额 **/
    private String orderAmount;


	public String getBankOrderNo() {
		return bankOrderNo;
	}


	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}


	public String getPayerUserNo() {
		return payerUserNo;
	}


	public void setPayerUserNo(String payerUserNo) {
		this.payerUserNo = payerUserNo;
	}


	public String getMerchantNo() {
		return merchantNo;
	}


	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}


	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}


	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}


	public String getOrderAmount() {
		return orderAmount;
	}


	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}


	@Override
	public String toString() {
		return "RpTradePaymentRecordV2 [bankOrderNo=" + bankOrderNo + ", payerUserNo=" + payerUserNo + ", merchantNo="
				+ merchantNo + ", merchantOrderNo=" + merchantOrderNo + ", orderAmount=" + orderAmount + "]";
	}


	@Override
	public int compareTo(RpTradePaymentRecordV2 o) {
		// TODO Auto-generated method stub
		return this.getMerchantOrderNo().compareTo(o.getMerchantOrderNo());
	}
    
    
}
