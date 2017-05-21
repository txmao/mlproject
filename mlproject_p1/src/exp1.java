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

public class exp1 {
	public static void main(String args[]) {
		String p3 = "/home/mdy/workspace/mlproject_p1/dataset/hd_train.arff";
		String p4 = "/home/mdy/workspace/mlproject_p1/dataset/hd_test.arff";
		try {
			
			DataSource ds3 = new DataSource(p3);
			Instances ins3 = ds3.getDataSet();
			ins3.setClassIndex(ins3.numAttributes()-1);
			
			DataSource ds4 = new DataSource(p4);
			Instances ins4 = ds4.getDataSet();
			ins4.setClassIndex(ins4.numAttributes()-1);
			
			Evaluation eval = new Evaluation(ins3);
			
			J48 mj48 = new J48();
			mj48.setUnpruned(false);
			mj48.buildClassifier(ins3);
			eval.evaluateModel(mj48, ins4);
			System.out.println("J48 Precission: "+eval.pctCorrect());
			
			Logistic mlr = new Logistic();
			mlr.buildClassifier(ins3);
			eval.evaluateModel(mlr, ins4);
			System.out.println("Logistice Regression Precission: "+eval.pctCorrect());
			
			SMO msvm = new SMO();
			msvm.buildClassifier(ins3);
			eval.evaluateModel(msvm, ins4);
			System.out.println("SVM Precission: "+eval.pctCorrect());
			
			MultilayerPerceptron mnn = new MultilayerPerceptron();
			mnn.setHiddenLayers("9");
			mnn.buildClassifier(ins3);
			eval.evaluateModel(mnn, ins4);
			System.out.print("Neural Network Precission: "+eval.pctCorrect());
			System.out.println(", Hidden Layer Size: "+mnn.getHiddenLayers());
			
			Bagging bg1 = new Bagging();
			DecisionStump myds1 = new DecisionStump();
			bg1.setClassifier(myds1);
			bg1.buildClassifier(ins3);
			eval.evaluateModel(bg1, ins4);
			System.out.println("Bagged Decision Stump Precission: "+eval.pctCorrect());
			
			AdaBoostM1 bst = new AdaBoostM1();
			DecisionStump myds2 = new DecisionStump();
			bst.setClassifier(myds2);
			bst.buildClassifier(ins3);
			eval.evaluateModel(bst, ins4);
			System.out.println("Boosted Decision Stump Precission: "+eval.pctCorrect());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
