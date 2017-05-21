import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class exp3 {
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
			
			System.out.println("@ (Un-Binarized) Attribute Ranking Report:");
			AttributeSelection as1 = new AttributeSelection();
			InfoGainAttributeEval iae1 = new InfoGainAttributeEval();
			Ranker rk1 = new Ranker();
			rk1.setNumToSelect(ins1.numAttributes()-1);
			as1.setEvaluator(iae1);
			as1.setSearch(rk1);
			as1.SelectAttributes(ins1);
			System.out.println(as1.toResultsString());
			
			System.out.println("@ (Binarized) Attribute Ranking Report:");
			AttributeSelection as2 = new AttributeSelection();
			InfoGainAttributeEval iae2 = new InfoGainAttributeEval();
			Ranker rk2 = new Ranker();
			rk2.setNumToSelect(ins2.numAttributes()-1);
			as2.setEvaluator(iae2);
			as2.setSearch(rk2);
			as2.SelectAttributes(ins2);
			System.out.println(as2.toResultsString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
