import java.io.File;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;

/**
 * create instances for Weka
 * & write to .arff format
 */
public class HD_Arff {
	//weka instances, can be used in implementation
	public Instances hdinstances;
	
	public HD_Arff (String rdpath, String wtpath) {
		//read from HD_Reader and write to .arff
		HD_Reader hdr = new HD_Reader(rdpath);
		double[][] rd_data = hdr.data2.clone();
		/*
		 * 11 attributes used, including 1 classAttribute
		 */
		Attribute age = new Attribute("age");
		Attribute sex = new Attribute("sex");
		Attribute cp = new Attribute("cp");
		Attribute thresbps = new Attribute("thresbps");
		Attribute chol = new Attribute("chol");
		Attribute fbs = new Attribute("fbs");
		Attribute restecg = new Attribute("restecg");
		Attribute thalach = new Attribute("thalach");
		Attribute exang = new Attribute("exang");
		Attribute oldpead = new Attribute("oldpeak");
		//class
		FastVector cfv = new FastVector(2);
		cfv.addElement("0");
		cfv.addElement("1");
		Attribute num = new Attribute("num", cfv);
		//total
		FastVector attrList = new FastVector(11);
		attrList.addElement(age);
		attrList.addElement(sex);
		attrList.addElement(cp);
		attrList.addElement(thresbps);
		attrList.addElement(chol);
		attrList.addElement(fbs);
		attrList.addElement(restecg);
		attrList.addElement(thalach);
		attrList.addElement(exang);
		attrList.addElement(oldpead);
		attrList.addElement(num);
		//
		Instances myIns = new Instances("hungarian-heart-disease", attrList, rd_data.length);
		myIns.setClassIndex(10);
		//populate
		for (int i=0; i<rd_data.length; i++) {
			Instance InsAtI = new Instance(11);
			for (int j=0; j<rd_data[i].length; j++) {
				if (j!=rd_data[i].length-1) {
					InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
				} else {
					if (rd_data[i][j]==0) {
						InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
					} else {
						InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
					}
				}
				//InsAtI.setValue((Attribute)attrList.elementAt(j), rd_data[i][j]);
			}
			myIns.add(InsAtI);
		}
		/*
		 * write to .arff
		 */
		
		ArffSaver afs = new ArffSaver();
		afs.setInstances(myIns);
		try {
			afs.setFile(new File(wtpath));
			afs.writeBatch();
		} catch (Exception e) {
			System.out.println("conert heart disease data to .arff file error");
			e.printStackTrace();
		}
		
		this.hdinstances = myIns;
		/*
		 * write to csv
		 */
		CSVSaver css = new CSVSaver();
		css.setInstances(myIns);
		try {
			String csvp = wtpath.substring(0, wtpath.length()-4)+"csv";
			css.setFile(new File(csvp));
			css.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * second version
		 */
		Attribute a1 = new Attribute("age");
		
		FastVector fv2 = new FastVector(2);
		fv2.addElement("1");
		fv2.addElement("0");
		Attribute a2 = new Attribute("sex", fv2);
		
		FastVector fv3 = new FastVector(5);
		fv3.addElement("4");
		fv3.addElement("3");
		fv3.addElement("2");
		fv3.addElement("1");
		fv3.addElement("0");// wcnmb, 0 stands for no pain
		Attribute a3 = new Attribute("cp", fv3);
		
		Attribute a4 = new Attribute("threstbps");
		
		Attribute a5 = new Attribute("chol");
		
		FastVector fv6 = new FastVector(2);
		fv6.addElement("1");
		fv6.addElement("0");
		Attribute a6 = new Attribute("fbs", fv6);
		
		FastVector fv7 = new FastVector(3);
		fv7.addElement("2");
		fv7.addElement("1");
		fv7.addElement("0");
		Attribute a7 = new Attribute("restecg", fv7);
		
		Attribute a8 = new Attribute("thalach");
		
		FastVector fv9 = new FastVector(2);
		fv9.addElement("1");
		fv9.addElement("0");
		Attribute a9 = new Attribute("exang", fv9);
		
		Attribute a10 = new Attribute("oldpeak");
		
		FastVector fv14 = new FastVector(2);
		fv14.addElement("1");
		fv14.addElement("0");
		Attribute a14 = new Attribute("num", fv14);
		
		FastVector tt2 = new FastVector(11);
		tt2.addElement(a1);
		tt2.addElement(a2);
		tt2.addElement(a3);
		tt2.addElement(a4);
		tt2.addElement(a5);
		tt2.addElement(a6);
		tt2.addElement(a7);
		tt2.addElement(a8);
		tt2.addElement(a9);
		tt2.addElement(a10);
		tt2.addElement(a14);
		
		Instances inss2 = new Instances("hungarian-heart-disease-2", tt2, rd_data.length);
		for (int i=0; i<rd_data.length; i++) {
			Instance insi = new Instance(11);
			for (int j=0; j<rd_data[i].length; j++) {
				insi.setValue((Attribute)tt2.elementAt(j), rd_data[i][j]);
			}
			inss2.add(insi);
		}
		
		ArffSaver sv2 = new ArffSaver();
		sv2.setInstances(inss2);
		try {
			sv2.setFile(new File(wtpath));
			sv2.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//save for id3, train, vali, test
		int test_size = hdr.DataSize/10;
		int train_size = hdr.DataSize - test_size;
		int vali_size = train_size / 5;
		train_size = hdr.DataSize - vali_size - test_size;
		//3 csv path
		String train_path = wtpath.substring(0, wtpath.length()-14) + "hd_train.csv";
		String test_path = wtpath.substring(0, wtpath.length()-14) + "hd_test.csv";
		String vali_path = wtpath.substring(0, wtpath.length()-14) + "hd_vali.csv";
		//
		double[][] bindata = hdr.data4;
		//
		//////////////
		Attribute age1 = new Attribute("age");
		Attribute sex1 = new Attribute("sex");
		Attribute cp1 = new Attribute("cp");
		Attribute trestbps1 = new Attribute("trestbps");
		Attribute chol1 = new Attribute("chol");
		Attribute fbs1 = new Attribute("fbs");
		Attribute restecg1 = new Attribute("restecg");
		Attribute thalach1 = new Attribute("thalach");
		Attribute exang1 = new Attribute("exang");
		Attribute oldpeak1 = new Attribute("oldpeak");
		FastVector num1fv = new FastVector(2);
		num1fv.addElement("1");
		num1fv.addElement("0");
		Attribute num1 = new Attribute("num", num1fv);
		FastVector train1 = new FastVector(11);
		train1.addElement(age1);
		train1.addElement(sex1);
		train1.addElement(cp1);
		train1.addElement(trestbps1);
		train1.addElement(chol1);
		train1.addElement(fbs1);
		train1.addElement(restecg1);
		train1.addElement(thalach1);
		train1.addElement(exang1);
		train1.addElement(oldpeak1);
		train1.addElement(num1);
		Instances instrain = new Instances("hd_train", train1, train_size);
		
		////////////
		Attribute age2 = new Attribute("age");
		Attribute sex2 = new Attribute("sex");
		Attribute cp2 = new Attribute("cp");
		Attribute trestbps2 = new Attribute("trestbps");
		Attribute chol2 = new Attribute("chol");
		Attribute fbs2 = new Attribute("fbs");
		Attribute restecg2 = new Attribute("restecg");
		Attribute thalach2 = new Attribute("thalach");
		Attribute exang2 = new Attribute("exang");
		Attribute oldpeak2 = new Attribute("oldpeak");
		FastVector num2fv = new FastVector(2);
		num2fv.addElement("1");
		num2fv.addElement("0");
		Attribute num2 = new Attribute("num", num2fv);
		
		FastVector vali2 = new FastVector(11);
		vali2.addElement(age2);
		vali2.addElement(sex2);
		vali2.addElement(cp2);
		vali2.addElement(trestbps2);
		vali2.addElement(chol2);
		vali2.addElement(fbs2);
		vali2.addElement(restecg2);
		vali2.addElement(thalach2);
		vali2.addElement(exang2);
		vali2.addElement(oldpeak2);
		vali2.addElement(num2);
		Instances insvali = new Instances("hd_vali", vali2, vali_size);
		
		//////////
		Attribute age3 = new Attribute("age");
		Attribute sex3 = new Attribute("sex");
		Attribute cp3 = new Attribute("cp");
		Attribute trestbps3 = new Attribute("trestbps");
		Attribute chol3 = new Attribute("chol");
		Attribute fbs3 = new Attribute("fbs");
		Attribute restecg3 = new Attribute("restecg");
		Attribute thalach3 = new Attribute("thalach");
		Attribute exang3 = new Attribute("exang");
		Attribute oldpeak3 = new Attribute("oldpeak");
		FastVector num3fv = new FastVector(2);
		num3fv.addElement("1");
		num3fv.addElement("0");
		Attribute num3 = new Attribute("num", num3fv);
		
		FastVector test3 = new FastVector(11);
		test3.addElement(age3);
		test3.addElement(sex3);
		test3.addElement(cp3);
		test3.addElement(trestbps3);
		test3.addElement(chol3);
		test3.addElement(fbs3);
		test3.addElement(restecg3);
		test3.addElement(thalach3);
		test3.addElement(exang3);
		test3.addElement(oldpeak3);
		test3.addElement(num3);
		Instances instest = new Instances("hd_test", test3, test_size);
		
		for (int i=0; i<bindata.length; i++) {
			Instance insyi = new Instance(11);
			if (i<train_size) {
				for (int j=0; j<bindata[i].length; j++) {
					insyi.setValue((Attribute)train1.elementAt(j), bindata[i][j]);
				}
				instrain.add(insyi);
			} else {
				if (i<train_size+vali_size) {
					for (int j=0; j<bindata[i].length; j++) {
						insyi.setValue((Attribute)vali2.elementAt(j), bindata[i][j]);
					}
					insvali.add(insyi);
				} else {
					for (int j=0; j<bindata[i].length; j++) {
						insyi.setValue((Attribute)test3.elementAt(j), bindata[i][j]);
					}
					instest.add(insyi);
				}
			}
		}
		
		ArffSaver btrain = new ArffSaver();
		btrain.setInstances(instrain);
		try {
			String pbt = wtpath.substring(0, wtpath.length()-14) + "hd_train.arff";
			btrain.setFile(new File(pbt));
			btrain.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArffSaver btest = new ArffSaver();
		btest.setInstances(instest);
		try {
			String pbtt = wtpath.substring(0, wtpath.length()-14) + "hd_test.arff";
			btest.setFile(new File(pbtt));
			btest.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CSVSaver sv_train = new CSVSaver();
		sv_train.setInstances(instrain);
		try {
			sv_train.setFile(new File(train_path));
			sv_train.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CSVSaver sv_vali = new CSVSaver();
		sv_vali.setInstances(insvali);
		try {
			sv_vali.setFile(new File(vali_path));
			sv_vali.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CSVSaver sv_test = new CSVSaver();
		sv_test.setInstances(instest);
		try {
			sv_test.setFile(new File(test_path));
			sv_test.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Attribute age4 = new Attribute("age");
		Attribute sex4 = new Attribute("sex");
		Attribute cp4 = new Attribute("cp");
		Attribute trestbps4 = new Attribute("trestbps");
		Attribute chol4 = new Attribute("chol");
		Attribute fbs4 = new Attribute("fbs");
		Attribute restecg4 = new Attribute("restecg");
		Attribute thalach4 = new Attribute("thalach");
		Attribute exang4 = new Attribute("exang");
		Attribute oldpeak4 = new Attribute("oldpeak");
		FastVector num4fv = new FastVector(2);
		num4fv.addElement("1");
		num4fv.addElement("0");
		Attribute num4 = new Attribute("num", num4fv);
		FastVector tt = new FastVector(11);
		tt.addElement(age4);
		tt.addElement(sex4);
		tt.addElement(cp4);
		tt.addElement(trestbps4);
		tt.addElement(chol4);
		tt.addElement(fbs4);
		tt.addElement(restecg4);
		tt.addElement(thalach4);
		tt.addElement(exang4);
		tt.addElement(oldpeak4);
		tt.addElement(num4);
		Instances instt = new Instances("hd_binary_form", tt, hdr.DataSize);
		
		for (int i=0; i<bindata.length; i++) {
			Instance mm = new Instance(11);
			for (int j=0; j<bindata[i].length; j++) {
				mm.setValue((Attribute)tt.elementAt(j), bindata[i][j]);
			}
			instt.add(mm);
		}
		
		ArffSaver nas1 = new ArffSaver();
		nas1.setInstances(instt);
		try {
			String npp = wtpath.substring(0, wtpath.length()-14) + "hungarianB.arff";
			nas1.setFile(new File(npp));
			nas1.writeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
