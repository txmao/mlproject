import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class exp2 {
	public static void main(String args[]) {
		String p1 = "/home/mdy/workspace/mlproject_p1/dataset/hungarian.arff";
		String p2 = "/home/mdy/workspace/mlproject_p1/dataset/hungarianB.arff";
		try {
			DataSource ds1 = new DataSource(p1);
			Instances ins1 = ds1.getDataSet();
			ins1.setClassIndex(ins1.numAttributes()-1);
			
			DataSource ds2 = new DataSource(p2);
			Instances ins2 = ds2.getDataSet();
			ins2.setClassIndex(ins2.numAttributes()-1);
			
			J48 mj48 = new J48();
			Logistic mlr = new Logistic();
			MultilayerPerceptron mnn = new MultilayerPerceptron();
			mnn.setHiddenLayers("9");
			SMO msvm = new SMO();
			DecisionStump mds = new DecisionStump();
			Bagging bg = new Bagging();
			bg.setClassifier(mds);
			AdaBoostM1 bst = new AdaBoostM1();
			bst.setClassifier(mds);
			
			System.out.println("@ Use Original Data @");
			Evaluation ev1 = new Evaluation(ins1);
			
			ev1.crossValidateModel(mj48, ins1, 10, new Random(1));
			System.out.println("10fldvali J48 Precission: "+ev1.pctCorrect());
			
			ev1.crossValidateModel(mlr, ins1, 10, new Random(1));
			System.out.println("10fldvali Logistice Regression Precission: "+ev1.pctCorrect());
			
			ev1.crossValidateModel(mnn, ins1, 10, new Random(1));
			System.out.print("10fldvali Neural Network Precission: "+ev1.pctCorrect());
			System.out.println(", Hidden Layer Number: "+mnn.getHiddenLayers());
			
			ev1.crossValidateModel(msvm, ins1, 10, new Random(1));
			System.out.println("10fldvali SVM Precission: "+ev1.pctCorrect());
			
			ev1.crossValidateModel(bg, ins1, 10, new Random(1));
			System.out.println("10fldvali Bagged Decision Stump Precission "+ev1.pctCorrect());
			
			ev1.crossValidateModel(bst, ins1, 10, new Random(1));
			System.out.println("10fldvali Bossted Decision Stump Precission "+ev1.pctCorrect());
			
			System.out.println("@ Use Binarized Data @");
			Evaluation ev2 = new Evaluation(ins2);
			
			ev2.crossValidateModel(mj48, ins2, 10, new Random(1));
			System.out.println("10fldvali J48 Precission: "+ev2.pctCorrect());
			
			ev2.crossValidateModel(mlr, ins2, 10, new Random(1));
			System.out.println("10fldvali Logistice Regression Precission: "+ev2.pctCorrect());
			
			ev2.crossValidateModel(mnn, ins2, 10, new Random(1));
			System.out.print("10fldvali Neural Network Precission: "+ev2.pctCorrect());
			System.out.println(", Hidden Layer Number: "+mnn.getHiddenLayers());
			
			ev2.crossValidateModel(msvm, ins2, 10, new Random(1));
			System.out.println("10fldvali SVM Precission: "+ev2.pctCorrect());
			
			ev2.crossValidateModel(bg, ins2, 10, new Random(1));
			System.out.println("10fldvali Bagged Decision Stump Precission "+ev2.pctCorrect());
			
			ev2.crossValidateModel(bst, ins2, 10, new Random(1));
			System.out.println("10fldvali Bossted Decision Stump Precission "+ev2.pctCorrect());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
