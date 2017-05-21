import java.util.Arrays;

public class thdr {
	public static void main(String args[]) {
		String pth = "/home/mdy/workspace/mlproject_p1/dataset/processed.hungarian.data";
		HD_Reader hdr = new HD_Reader(pth);
		System.out.println(hdr.raw1.get(2));
		System.out.println(hdr.raw2.get(2));
		System.out.println(hdr.DataSize);
		System.out.println(Arrays.toString(hdr.noneCnt));
		System.out.println(hdr.delInd);
		System.out.println(hdr.raw3.get(2));
		System.out.println(Arrays.toString(hdr.noneCnt1));
		System.out.println(Arrays.toString(hdr.classcnt));
		System.out.println(Arrays.toString(hdr.data1[2]));
		System.out.println(Arrays.toString(hdr.data2[2]));
		for (int k : hdr.none_to_ave.keySet()) {
			System.out.print(k+": ");
			System.out.println(Arrays.toString(hdr.none_to_ave.get(k)));
		}
		System.out.println(Arrays.toString(hdr.data3[2]));
		System.out.println(Arrays.toString(hdr.data4[2]));
	}

}
