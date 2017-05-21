'''
Created on Jan 28, 2017

@author: mdy
'''

from id3train import id3train
from treeVali import treeVali
from prunTree import prunTree
import sys

def test():
    
    L = int(sys.argv[1])
    K = int(sys.argv[2])
    
    training_set = str(sys.argv[3])
    validation_set = str(sys.argv[4])
    test_set = str(sys.argv[5])
    
    to_print = str(sys.argv[6])
    
    #igorvi = int(sys.argv[7]) #0 for IG, and 1 for VI
    
    
    #4 tree
    original_tree_ig = id3train(training_set, 0)
    original_tree_vi = id3train(training_set, 1)
    
    pruned_tree_ig = prunTree(L, K, original_tree_ig, validation_set)
    pruned_tree_vi = prunTree(L, K, original_tree_vi, validation_set)
    
    #test
    v0_ig = treeVali(test_set, original_tree_ig.bTree)
    v1_ig = treeVali(test_set, pruned_tree_ig.rst_Tree.bTree)
    
    v0_vi = treeVali(test_set, original_tree_vi.bTree)
    v1_vi = treeVali(test_set, pruned_tree_vi.rst_Tree.bTree)
    
    #if yes print
    if to_print == 'yes' :
        print '########## original tree (using information gain heuristic) printed bellow ##########'
        original_tree_ig.prtTree()
        print '########## pruned tree (using information gain heuristic) printed bellow ##########'
        pruned_tree_ig.rst_Tree.prtTree()
        
        print '########## original tree (using variance impurity heuristic) printed bellow ##########'
        original_tree_vi.prtTree()
        print '########## pruned tree (using variance impurity heuristic) printed bellow #########'
        pruned_tree_vi.rst_Tree.prtTree()
        
    #accuracy information
    print '### Accuracy Information Reported Bellow: ###'
    print 'original tree (information gain heuristic) prediction accuracy on test set: ' + str(v0_ig.Accura)
    print 'pruned tree (information gain heuristic) prediction accuracy on test set: ' + str(v1_ig.Accura)
    print 'original tree (variance impurity heuristic) prediction accuracy on test set: ' + str(v0_vi.Accura)
    print 'pruned tree (variance impurity heuristic) prediction accuracy on test set: ' + str(v1_vi.Accura)
    
    '''
    10
    5
    '/home/mdy/Desktop/decisionTree/data_sets1/training_set.csv'
    '/home/mdy/Desktop/decisionTree/data_sets1/validation_set.csv'
    '/home/mdy/Desktop/decisionTree/data_sets1/test_set.csv'
    'yes'
    '''
    
    
if __name__ == '__main__' :
    test()
    
    
    
    
    
    
    