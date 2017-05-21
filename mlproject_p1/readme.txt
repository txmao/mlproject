$ Heart Disease Diagnosis Prediction, Using Weka $
 PART I

# File Description
 -mlproject_p1/src
  HD_Reader.java, read the dataset processed.hungarian.data
  HD_Arff.java, write all .arff and .csv data files used in the experiment
  thda.java*, used to write files, please specify path inside
  exp1.java*, use classifiers naively, on train and test set, used to compare with id3 in mlproject_p2
  exp2.java*, 10 fold cross validation precision report, on both original and binarized dataset
  exp3.java*, attribute ranking information
  (note that you need to provide dataset file path and classpath of weka to run the * file)

# classifiers used
 id3 (in project 2)
 c4.5
 logistic regression
 neural networks
 support vector machine
 bagging
 boosting

# References
 https://archive.ics.uci.edu/ml/datasets/Heart+Disease
 https://archive.ics.uci.edu/ml/datasets/Statlog+(Heart)