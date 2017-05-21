'''
Created on Jan 28, 2017

@author: mdy
'''
from id3train import id3train
from bTreeNode import bTreeNode
from treeVali import treeVali
import copy
import numpy as np

class prunTree:
    
    def __init__(self, L, K, id3, vali_path):
        #get un leaf list
        id3.get_unandis_leaf_index(id3.bTree)
        self.operate_node_list = id3.un_leaf_index
        self.vali_path = vali_path
        
        self.tmp_list = []
        
        self.rst_Tree = self.prunLK(L, K, id3, self.vali_path)
        
        
        
        
    def prunLK(self, L, K, id3, vali_path):
        
        Dbest = copy.deepcopy(id3)
        #Dbest.prtTree()
        
        for i in range(L) :
            #Dbest = copy.deepcopy(id3)
            #
            Dp = copy.deepcopy(id3)
            
            if K == 1:
                M = 1
            else:
                M = np.random.randint(1, K)
            #
            for j in range(M):
                #visit the tree to get the index_list of non-leaf node
                #restore
                #construct
                #deep copy
                self.tmp_list = []
                self.record_nonleaf(Dp.bTree)
                use_list = copy.deepcopy(self.tmp_list)
                #print len(use_list) # sometimes delete to zero
                
                if len(use_list) != 0 :
                    N = len(use_list)
                    if N-1 > 0 :
                        P = np.random.randint(0, N-1)
                    else:
                        P = 0
                    #P = np.random.randint(0, N-1)
                    #print P
                    Pind = use_list[P]
                    Dp.prunP(Dp.bTree, Pind)
                else:
                    pass # if delete to zero, do nothing
                
            Dbest_vali = treeVali(vali_path, Dbest.bTree)
            Dp_vali = treeVali(vali_path, Dp.bTree)
            
            if Dbest_vali.Accura < Dp_vali.Accura :
                Dbest = copy.deepcopy(Dp)
                
                
        
        
        
        
        return Dbest
    
    
    def record_nonleaf(self, Root):
        if Root == None :
            return
        else:
            if Root.n_isLeaf != 1 :
                self.tmp_list.append(Root.n_inde)
                self.record_nonleaf(Root.n_left)
                self.record_nonleaf(Root.n_right)
            else:
                return
            
            
    '''            
    def prunP(self, Root, Pind):
        if Root == None :
            return
        else:
            if Root.n_inde == Pind :
                Root.n_isLeaf = 1
                Root.n_attri = -1
                Root.n_left = None
                Root.n_right = None
                #Root.n_inde
                #Root.n_label
            else:
                self.prunP(Root.n_left, Pind)
                self.prunP(Root.n_right, Pind)
    '''            
                
                
            
       
        
        
    
if __name__ == '__main__':
    cp1 = '/home/mdy/Desktop/decisionTree/data_sets1/training_set.csv'
    cp = '/home/mdy/Desktop/decisionTree/data_sets1/validation_set.csv'
    tr = id3train(cp1)
    pt = prunTree(20,100,tr,cp)
    pt.rst_Tree.prtTree()
    
    v0 = treeVali(cp, tr.bTree)
    v1 = treeVali(cp, pt.rst_Tree.bTree)
    print v0.Accura
    print v1.Accura
    
    
    
    
    
    
    