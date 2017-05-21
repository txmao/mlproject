'''
Created on Jan 27, 2017

@author: mdy
'''

from __future__ import division
from bTreeNode import bTreeNode
from csv_parser import csv_parser
from id3train import id3train

class treeVali :
    
    def __init__(self, path_valicsv, tRoot):
        vcsv = csv_parser(path_valicsv)
        
        self.vali_Dset = vcsv.set_data
        self.vali_Aset = vcsv.set_attr
        self.vali_Lset = vcsv.set_label
        
        self.vali_Dindex = vcsv.index_data
        self.vali_Aindex = vcsv.index_attr
        self.vali_Lindex = vcsv.index_label
        
        self.true_predicted_set = []
        
        self.Accura = self.calc_accura(self.vali_Lindex, tRoot)
        
        
    def calc_accura(self, Lindex, tRoot):
        for ind in Lindex :
            self.__predict__(Lindex, tRoot, ind)
            
        return len(self.true_predicted_set)/len(self.vali_Lindex)
            
    def __predict__(self, Lindex, tRoot, ind):
        if tRoot.n_isLeaf == 1 :
            if self.vali_Lset[ind] == tRoot.n_label :
                self.true_predicted_set.append(ind)
                
        else:
            if self.vali_Dset[ind][tRoot.n_attri] == 0 :
                self.__predict__(Lindex, tRoot.n_left, ind)
                
            else:
                self.__predict__(Lindex, tRoot.n_right, ind)
        
        
if __name__ == '__main__':
    
    t_cp = '/home/mdy/Desktop/decisionTree/data_sets1/training_set.csv'
    tR = id3train(t_cp)
    #tR.prtTree()
    
    v_cp = '/home/mdy/Desktop/decisionTree/data_sets1/validation_set.csv'
    tV = treeVali(v_cp, tR.bTree)
    print tV.true_predicted_set
    print tV.Accura
    #print tV.vali_Aset
    #print tV.vali_Dindex
    #print tV.vali_Lindex
    
    
    
    
    
    
    
    
    
    