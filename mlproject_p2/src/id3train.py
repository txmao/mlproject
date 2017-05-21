'''
Created on Jan 25, 2017

@author: mdy
'''

from __future__ import division 
from csv_parser import csv_parser #file->class
from bTreeNode import bTreeNode
import math
import sys

class id3train:
    
    
    def __init__(self, training_csv_path, ig_or_vi):
        #using parser
        csvp = csv_parser(training_csv_path)
        #set
        self.set_data = csvp.set_data
        self.set_attr = csvp.set_attr
        self.set_label = csvp.set_label
        #print self.set_attr
        #print self.set_label
        #index
        self.index_data = csvp.index_data
        self.index_attr = csvp.index_attr
        self.index_label = csvp.index_label
        
        #record n_ind of bTreeNode
        self.node_cnt = -1
        self.node_indx = []
        
        #record used attribute index in the tree
        self.used_attri = []
        
        #train
        self.bTree=self.id3(self.index_data, self.index_label, self.index_attr, ig_or_vi)
        
        self.un_leaf_index = []
        self.is_leaf_index = []
        
        
    def get_unandis_leaf_index(self, bTree):
        if bTree == None :
            return
        else:
            if bTree.n_isLeaf == 1 :
                self.is_leaf_index.append(bTree.n_inde)
            else:
                self.un_leaf_index.append(bTree.n_inde)
                
        self.get_unandis_leaf_index(bTree.n_left)
        self.get_unandis_leaf_index(bTree.n_right)
            
        
        
    
    
    
    
    def id3(self, index_data, index_label, index_attr, ig_or_vi):
        
        #creat root
        Root = bTreeNode()
        self.node_cnt += 1
        #most common value of example data
        common_value = 0
        label_len = len(index_label)
        label_sum = 0
        
        for ind in index_label:
            label_sum += self.set_label[ind]
            
        if label_sum>(label_len/2) :
            common_value = 1
            
        else:
            common_value = 0
            
        #print common_value
        
        Root.n_label = common_value # a label assigned to every tree node
        Root.n_inde = self.node_cnt
        self.node_indx.append(Root.n_inde)
        #base
        if label_sum == 0 or label_sum == label_len or len(index_attr) == 0 :
            #Root.n_label = common_value
            Root.n_isLeaf = 1
            return Root
        
        #otherwise
        # there are two heuristic can be choosen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if ig_or_vi == 0 :
            A = self.bestAttr(index_data, index_label, index_attr)
        else:
            A = self.VI_Best(index_label, index_attr)
        #A = self.bestAttr(index_data, index_label, index_attr) # if n_label==-1, then not leaf
        #A = self.VI_Best(index_label, index_attr)
        #print A
        
        if A!=-1 :
            Root.n_attri = A
            self.used_attri.append(A)
        
        new_attr = []
        for ind in index_attr:
            if ind!= A :
                new_attr.append(ind)
        
        # if A is None, then it must be last step so just set index_attr to []        
        new_left = []
        new_right = []
        if A!= -1 :
            for i in index_label :
                if self.set_data[i][A]==0 :
                    new_left.append(i)
                else:
                    new_right.append(i)
                
        #print new_left
        #print new_right
        
        for i in range(2):
            if i==0 :
                if len(new_left)==0 :
                    nn = bTreeNode()
                    self.node_cnt += 1
                    Root.n_left = nn
                    #Root.n_isLeaf = 1
                    nn.n_isLeaf = 1
                    nn.n_label = common_value
                    nn.n_inde = self.node_cnt
                    self.node_indx.append(nn.n_inde)
                else:
                    Root.n_left = self.id3(new_left, new_left, new_attr, ig_or_vi)
                    
            
            if i==1 :
                if len(new_right)==0 :
                    nn = bTreeNode()
                    self.node_cnt += 1
                    Root.n_right = nn
                    #Root.n_isLeaf = 1
                    nn.n_isLeaf = 1
                    nn.n_label = common_value
                    nn.n_inde = self.node_cnt
                    self.node_indx.append(nn.n_inde)
                else:
                    Root.n_right = self.id3(new_right, new_right, new_attr, ig_or_vi)
                
        
        
        #return
        return Root
        
        
        
    def bestAttr(self, index_data, index_label, index_attr):
        
        #infoGain
        maxGain = 0
        maxInd = -1
        for indx in index_attr:
            #print indx
            tmp = self.infoGain(index_label, indx)
            if maxGain<tmp:
                maxGain = tmp
                maxInd = indx
                
            
        return maxInd
    
    
    def entropy (self, index_label):
        #calc
        num_label_total = len(index_label)
        num_label_1 = 0
        num_label_0 = 0
        for ind in index_label:
            if self.set_label[ind]==1 :
                num_label_1 += 1
            
            else:
                num_label_0 += 1
          
        posi = num_label_1/num_label_total
        nega = num_label_0/num_label_total
        
        if posi == 0 or nega == 0 :
            entro = 0
        else:
            entro = -posi*math.log(posi)-nega*math.log(nega)
        
        #print entro    
        return entro
    
    
    def entropy_attr(self, index_label, ind):
        
        n00 = 0
        n01 = 0
        n10 = 0
        n11 = 0
        
        for i in index_label:
            if self.set_data[i][ind] == 0:
                if self.set_label[i] == 0:
                    n00 = n00 + 1
                else:
                    n01 = n01 + 1
            else:
                if self.set_label[i] == 0:
                    n10 = n10 + 1
                else:
                    n11 = n11 + 1
                    
        #print n00, n01, n10, n11
        
        n0 = n00 + n01
        n1 = n10 + n11
        n = n0 + n1
        
        if ( (n00==0) and (n01==0) and (n10==0) and (n11==0) ):
            return 0
        
        if ( (n00==0) and (n01==0) and (n10==0) and (n11!=0) ):
            return 0
        
        if ( (n00==0) and (n01==0) and (n10!=0) and (n11==0) ):
            return 0
        
        if ( (n00==0) and (n01==0) and (n10!=0) and (n11!=0) ):
            return (n1/n)*( -(n10/n1)*math.log(n10/n1) - (n11/n1)*math.log(n11/n1) )
        
        
        
        if ( (n00==0) and (n01!=0) and (n10==0) and (n11==0) ):
            return 0
        
        if ( (n00==0) and (n01!=0) and (n10==0) and (n11!=0) ):
            return 0
        
        if ( (n00==0) and (n01!=0) and (n10!=0) and (n11==0) ):
            return 0
        
        if ( (n00==0) and (n01!=0) and (n10!=0) and (n11!=0) ):
            return (n1/n)*( -(n10/n1)*math.log(n10/n1) - (n11/n1)*math.log(n11/n1) )
        
        
        
        if ( (n00!=0) and (n01==0) and (n10==0) and (n11==0) ):
            return 0
        
        if ( (n00!=0) and (n01==0) and (n10==0) and (n11!=0) ):
            return 0
        
        if ( (n00!=0) and (n01==0) and (n10!=0) and (n11==0) ):
            return 0
        
        if ( (n00!=0) and (n01==0) and (n10!=0) and (n11!=0) ):
            return (n1/n)*( -(n10/n1)*math.log(n10/n1) - (n11/n1)*math.log(n11/n1) )
        
        
        
        if ( (n00!=0) and (n01!=0) and (n10==0) and (n11==0) ):
            return (n0/n)*( -(n00/n0)*math.log(n00/n0) - (n01/n0)*math.log(n01/n0) )
        
        if ( (n00!=0) and (n01!=0) and (n10==0) and (n11!=0) ):
            return (n0/n)*( -(n00/n0)*math.log(n00/n0) - (n01/n0)*math.log(n01/n0) )
        
        if ( (n00!=0) and (n01!=0) and (n10!=0) and (n11==0) ):
            return (n0/n)*( -(n00/n0)*math.log(n00/n0) - (n01/n0)*math.log(n01/n0) )
        
        if ( (n00!=0) and (n01!=0) and (n10!=0) and (n11!=0) ):
            entr0 = (n0/n)*( -(n00/n0)*math.log(n00/n0) - (n01/n0)*math.log(n01/n0) )
            entr1 = (n1/n)*( -(n10/n1)*math.log(n10/n1) - (n11/n1)*math.log(n11/n1) )
            #print entr0+entr1
            return entr0+entr1
        
        
    def infoGain(self, index_label, ind):
        #print self.entropy(index_label)-self.entropy_attr(index_label, ind)
        return self.entropy(index_label)-self.entropy_attr(index_label, ind)
          
    
    def prtTree(self):
        self.__prtTree__(self.bTree, -1, -1, self.bTree.n_attri) # '|' bar number & left, right indicator
        
    
    def __prtTree__(self, bTree, Bar, LoR, c_attri):
        # node.attr = l_o_(r) : node.l_o_(r).label if it is leaf
        # c_attri, current attribute of node
        
        n_bar = Bar
        stri = '| ' * n_bar
        
        if bTree == None :
            return
        
        #if bTree.n_left == None and bTree.n_right == None
        if bTree.n_left == None and bTree.n_right == None and bTree.n_inde == 0 :
            pass
            print bTree.n_label
        
        if n_bar == -1 : # should not do first step
            pass
        
        else:
            if bTree.n_isLeaf == 1 :
                stri = stri + str(self.set_attr[c_attri]) + ' = ' + str(LoR) + ' : ' + str(bTree.n_label)
                
            else:
                stri = stri + str(self.set_attr[c_attri]) + ' = ' + str(LoR) + ' : '
            
        print stri
        self.__prtTree__(bTree.n_left, n_bar+1, 0, bTree.n_attri)
        self.__prtTree__(bTree.n_right, n_bar+1, 1, bTree.n_attri)
    
    
    #####################################
    ###  Variance Impurity Heuristic  ###
    #####################################
    def VI_Best(self, index_label, index_attr):
        maxVIGain = 0
        bstattr = -1
        for ind in index_attr:
            if ( self.VI(index_label) - self.VIP(index_label, ind) ) > maxVIGain :
                maxVIGain = ( self.VI(index_label) - self.VIP(index_label, ind) )
                bstattr = ind
                
        return bstattr
    
    def VI(self, index_label):
        n = len(index_label)
        n0 = 0
        n1 = 0
        for ind in index_label :
            if self.set_label[ind] == 0 :
                n0 += 1
                
            else:
                n1 += 1
                
        if n==0 :
            return 0
        
        else:
            return (n0*n1)/(n*n)
        
    def VIP(self, index_label, attr):
        ind_p = []
        ind_n = []
        for ind in index_label :
            if self.set_data[ind][attr] == 1 :
                ind_p.append(ind)
                
            else:
                ind_n.append(ind)
                
        l = len(ind_p) + len(ind_n)
        if l == 0:
            return 0
        
        else:
            return (len(ind_p)/l)*self.VI(ind_p) + (len(ind_n)/l)*self.VI(ind_n)
        
        
    
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
                
                
    def get_level(self, Root):
        #only one node is level one
        if Root == None:
            return 0
        else:
            return 1+max(self.get_level(Root.n_left), self.get_level(Root.n_right))
    
    
        
        
if __name__=='__main__':
    #sys.setrecursionlimit(999999)
    path = '/home/mdy/Desktop/decisionTree/data_sets1/training_set.csv'
    tr = id3train(path, 0)
    #print tr.set_attr[tr.bTree.n_left.n_left.n_left.n_left.n_right.n_attri]
    #tr.bTree.prtNode()
    tr.prtTree()
    tr.bTree.n_left.n_right.prtNode()
    print tr.node_cnt
    print tr.node_indx
    #print set(tr.used_attri)
    #tr.bTree.n_right.n_right.n_right.n_right.n_right.n_right.prtNode()
    #print tr.index_attr
    #print tr.set_attr[7]
    #print tr.index_data
    #print tr.index_label
    tr.get_unandis_leaf_index(tr.bTree)
    print tr.un_leaf_index
    print tr.is_leaf_index
    tr.prunP(tr.bTree, 0)
    tr.prtTree()
    print tr.bTree.n_label
    print tr.get_level(tr.bTree)
    
    
    