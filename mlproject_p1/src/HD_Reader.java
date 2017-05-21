import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * read the heart disease data from .data file
 * 
 */
public class HD_Reader {
	public ArrayList<String> raw1 = new ArrayList<String>();
	public ArrayList<ArrayList<String>> raw2 = new ArrayList<ArrayList<String>>();
	public int DataSize;
	public int[] noneCnt;
	public ArrayList<Integer> delInd = new ArrayList<Integer> ();
	public ArrayList<ArrayList<String>> raw3 = new ArrayList<ArrayList<String>>();
	public int[] noneCnt1;//after filter, how many attributes contains "?"
	//
	int[] classcnt = new int[2];
	public double[][] data1;
	public double[][] data2;//store the final used data
	public HashMap<Integer, double[]> none_to_ave = new HashMap<Integer, double[]> ();
	public double[][] data3;//data after random shuffle
	public double[][] data4;//data3->binary form, use class average
	
	public HD_Reader(String f_path) {
		try {
			//write raw1
			BufferedReader bfr = new BufferedReader(new FileReader(f_path));
			String ln;
			while ( (ln=bfr.readLine()) != null ) {
				this.raw1.add(ln);
			}
			bfr.close();
			//size of the dataset
			this.DataSize = this.raw1.size();
			//write raw2
			for (int i=0; i<this.raw1.size(); i++) {
				ArrayList<String> as1 = new ArrayList<String>();
				String li = this.raw1.get(i);
				String[] liS = li.split(",");
				for (int j=0; j<liS.length; j++) {
					as1.add(liS[j]);
				}
				this.raw2.add(as1);
			}
			//noneCnt, ? number for every attribute;
			this.noneCnt = new int[this.raw2.get(0).size()];
			for (int i=0; i<this.raw2.size(); i++) {
				for (int j=0; j<this.raw2.get(i).size(); j++) {
					if (this.raw2.get(i).get(j).equals("?")) {
						this.noneCnt[j] += 1;
					}
				}
			}
			//if too many missing value, then will not be used (set 50%)
			for (int i=0; i<this.noneCnt.length; i++) {
				double d1 = (double) this.noneCnt[i];
				double d2 = (double) this.DataSize;
				if (d1/d2 > 0.5) {
					this.delInd.add(i);
				}
			}
			for (int i=0; i<this.raw2.size(); i++) {
				ArrayList<String> asi_n = new ArrayList<String>();
				ArrayList<String> asi_o = this.raw2.get(i);
				for (int j=0; j<asi_o.size(); j++) {
					if (!this.delInd.contains(j)) {
						asi_n.add(asi_o.get(j));
					}
				}
				this.raw3.add(asi_n);
			}
			this.noneCnt1 = new int[this.raw3.get(0).size()];
			for (int i=0; i<this.raw3.size(); i++) {
				for (int j=0; j<this.raw3.get(i).size(); j++) {
					if (this.raw3.get(i).get(j).equals("?")) {
						this.noneCnt1[j] += 1;
					}
					if (j==this.raw3.get(i).size()-1) {
						if (this.raw3.get(i).get(j).equals("0"))
							this.classcnt[0] += 1;
						else
							this.classcnt[1] += 1;
						
					}
				}
			}
			this.data1 = new double[this.DataSize][this.raw3.get(0).size()];
			for (int i=0; i<this.data1.length; i++) {
				for (int j=0; j<this.data1[i].length; j++) {
					if (this.raw3.get(i).get(j).equals("?")) {
						this.data1[i][j] = -9;
					} else {
						this.data1[i][j] = Double.parseDouble(this.raw3.get(i).get(j));
					}
				}
			}
			/*
			 * do initialization, assign average value based on class
			 */
			this.doinit();
			this.rdmshuffle();
			this.dobinarize();
		} catch (Exception e) {
			System.out.println("read heart disease file error");
			e.printStackTrace();
		}
	}
	
	public void doinit () {
		ArrayList<Integer> indexofnone = new ArrayList<Integer>();
		for (int i=0; i<this.noneCnt1.length; i++)
			if (this.noneCnt1[i]!=0)
				indexofnone.add(i);
		
		for (int k : indexofnone) {
			double sum0 = 0;
			double sum1 = 0;
			for (int i=0; i<this.data1.length; i++) {
				if (this.data1[i][this.data1[i].length-1]==0) {
					sum0 += this.data1[i][k];
				} else {
					sum1 += this.data1[i][k];
				}
			}
			double d0 = Math.round(sum0/this.classcnt[0]);
			double d1 = Math.round(sum1/this.classcnt[1]);
			double[] tpk = {d0, d1};
			this.none_to_ave.put(k, tpk);
		}
		this.data2 = new double[this.data1.length][this.data1[0].length];
		for (int i=0; i<this.data1.length; i++) {
			for (int j=0; j<this.data1[i].length; j++) {
				if (this.data1[i][j]==-9) {
					if (this.data1[i][this.data1[i].length-1] == 0) {
						this.data2[i][j] = none_to_ave.get(j)[0];
					} else {
						this.data2[i][j] = none_to_ave.get(j)[1];
					}
				} else {
					this.data2[i][j] = this.data1[i][j];
				}
			}
		}
	}
	
	public void rdmshuffle() {
		this.data3 = new double[this.data2.length][this.data2[0].length];
		ArrayList<double[]> cpdata2 = new ArrayList<double[]>(Arrays.asList(this.data2));
		Collections.shuffle(cpdata2);
		for (int i=0; i<this.data3.length; i++) {
			this.data3[i] = cpdata2.get(i);
		}
	}
	
	public void dobinarize() {
		//data3->binary
		this.data4 = new double[this.data3.length][this.data3[0].length];
		HashMap<Integer, double[]> five_ave = new HashMap<Integer, double[]> ();
		double[] f1 = {0, 0};
		double[] f4 = {0, 0};
		double[] f5 = {0, 0};
		double[] f8 = {0, 0};
		double[] fa = {0, 0};
		for (int i=0; i<this.data3.length; i++) {
			//1, 4, 5, 8, 10, need to convert to binary
			if (this.data3[i][this.data3[i].length-1]==0) {
				f1[0] += this.data3[i][0];
				f4[0] += this.data3[i][3];
				f5[0] += this.data3[i][4];
				f8[0] += this.data3[i][7];
				fa[0] += this.data3[i][9];
			} else {
				f1[1] += this.data3[i][0];
				f4[1] += this.data3[i][3];
				f5[1] += this.data3[i][4];
				f8[1] += this.data3[i][7];
				fa[1] += this.data3[i][9];
			}
		}
		int c0 = this.classcnt[0];
		int c1 = this.classcnt[1];
		//
		double[] d1ave = {f1[0]/c0, f1[1]/c1};
		double[] d4ave = {f4[0]/c0, f4[1]/c1};
		double[] d5ave = {f5[0]/c0, f5[1]/c1};
		double[] d8ave = {f8[0]/c0, f8[1]/c1};
		double[] daave = {fa[0]/c0, fa[1]/c1};
		five_ave.put(0, d1ave);
		five_ave.put(3, d4ave);
		five_ave.put(4, d5ave);
		five_ave.put(7, d8ave);
		five_ave.put(9, daave);
		//
		for (int i=0; i<this.data4.length; i++) {
			for (int j=0; j<this.data4[i].length; j++) {
				if (five_ave.keySet().contains(j)) {
					if (this.data3[i][this.data3[i].length-1]==0) {
						if (this.data3[i][j]>five_ave.get(j)[0]) {
							this.data4[i][j] = 1;
						} else {
							this.data4[i][j] = 0;
						}
					} else {
						if (this.data3[i][j]>five_ave.get(j)[1]) {
							this.data4[i][j] = 1;
						} else {
							this.data4[i][j] = 0;
						}
					}
				} else {
					this.data4[i][j] = this.data3[i][j];
				}
			}
		}
		for (int i=0; i<this.data4.length; i++) {
			if (this.data4[i][2]!=0) {
				this.data4[i][2] = 1;
			}
		}
	}

}
